package com.augusto.user.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import com.augusto.user.exceptions.ResourceNotFoundException;
import com.augusto.user.model.Role;
import com.augusto.user.model.User;
import com.augusto.user.payload.UserDto;
import com.augusto.user.payload.UserInputDto;
import com.augusto.user.repository.RoleRepository;
import com.augusto.user.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ModelMapper mapper;

    @KafkaListener(topics = "user", groupId = "user",
            containerFactory = "kafkaListenerContainerFactory")
    public void createUser(UserInputDto userInputDto) {
        var user = toUser(userInputDto);
        user.setRoles(setRole(userInputDto.getRoles()));
        userRepository.save(user);
    }

    private Set<Role> setRole(String roles) {
        var role = roleRepository.findByRole(roles);
        Set<Role> newRoles = new HashSet<>();
        newRoles.add(role.get());
        return newRoles;
    }

    private User toUser(UserInputDto userInputDto) {
        var user = mapper.map(userInputDto, User.class);
        return user;
    }

    private UserDto toUserDto(User user) {
        var userDto = mapper.map(user, UserDto.class);
        return userDto;
    }

    public UserDto getUserAndProduct(Long id) {
        var user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("user", "ID", id));
        var userDto = toUserDto(user);
        return userDto;
    }

    public UserDto getUser(Long id, String email, String cpf) {
        var user = userRepository.findByIdOrEmailOrCpf(id, email, cpf).orElseThrow(
                () -> new ResourceNotFoundException("user", "identifier", id + cpf + email));
        var userDto = toUserDto(user);
        return userDto;
    }

    public List<UserDto> listAllUsers() {
        var users = userRepository.findAll();
        var usersDto = users.stream().map(u -> toUserDto(u)).toList();
        return usersDto;
    }


    public boolean credentialsIsValid(String email, String cpf) {
        var checkUser = userRepository.findByEmailOrCpf(email, cpf);
        if (checkUser.isPresent()) {
            return false;
        }
        return true;
    }
}
