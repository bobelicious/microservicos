package com.augusto.user.service;

import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.augusto.user.exceptions.UserException;
import com.augusto.user.payload.UserDto;
import com.augusto.user.payload.UserInputDto;
import com.augusto.user.proxy.ProductProxy;
import com.augusto.user.proxy.UserProxy;

@Service
public class UserService {
    @Autowired
    private ProductProxy productProxy;
    @Autowired
    private UserProxy userProxy;
    @Autowired
    private Environment environment;
    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private ModelMapper mapper;

    public UserDto createUser(UserInputDto userInputDto) {
        credentialIsValid(userInputDto.getEmail(), userInputDto.getCpf());
        userInputDto.setCustomId(generateCustomId());
        userInputDto.setPassword(encoder.encode(userInputDto.getPassword()));
        var userDto =  toUserDto(userInputDto);
        userDto.setEnvironment(environment.getProperty("local.server.port"));

        Message<UserInputDto> message = MessageBuilder.withPayload(userInputDto)
                .setHeader(KafkaHeaders.TOPIC, "user").build();
        kafkaTemplate.send(message);
        return userDto;
    }

    public UserDto getUserAndProduct(Long id, String token) {
        var userDto = userProxy.getUserAndProduct(id, token);
        userDto.setProduct(productProxy.getProductByUserId(id, token));
        userDto.setEnvironment(environment.getProperty("local.server.port"));
        return userDto;
    }

    public UserDto getUser(Long id, String email, String cpf, String token) {
        var userDto = userProxy.getUser(id, email, cpf, token);
        userDto.setEnvironment(environment.getProperty("local.server.port"));
        return userDto;
    }

    public List<UserDto> listAllUsers(String token) {
        var usersDto = userProxy.listAllUsers(token);
        return usersDto;
    }

    private Long generateCustomId() {
        var env = environment.getProperty("local.server.port");
        var date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyMMdd"));
        var id = date + env;
        for (int i = 0; i <= 4; i++) {
            SecureRandom rand = new SecureRandom();
            var num = rand.nextInt(10);
            id += "" + num;
        }
        var customId = Long.parseLong(id);
        return customId;
    }

    private void credentialIsValid(String email, String cpf) {
        var checkUser = userProxy.credentialIsValid(email, cpf);
        if (checkUser.equals(false)) {
            throw new UserException(HttpStatus.BAD_REQUEST,
                    "User with this CPF or Email already exists");
        }
    }

    private UserDto toUserDto(UserInputDto userInputDto) {
        var userDto = mapper.map(userInputDto, UserDto.class);
        return userDto;
    }
}
