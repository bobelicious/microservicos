package com.augusto.address.service;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.augusto.address.exceptions.ResourceNotFoundException;
import com.augusto.address.payload.AddressDto;
import com.augusto.address.payload.AddressInputDto;
import com.augusto.address.payload.AddressViaCepDto;
import com.augusto.address.proxy.AddressProxy;
import com.augusto.address.security.JwtTokenProvider;

@Service
public class AddressService {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;
    @Autowired
    private AddressProxy addressProxy;


    public AddressDto createAddress(AddressInputDto addressInputDto, String token) {
        var addressViaCep = getAddressFromViaCep(addressInputDto.getCep());
        var addressDto = toAddressDto(addressViaCep);

        addressDto.setComplemento(addressInputDto.getComplemento());
        addressDto.setUserId(jwtTokenProvider.getUserIdFromJwt(token));
        addressDto.setNumero(addressInputDto.getNumero());

        Message<AddressDto> message = MessageBuilder.withPayload(addressDto)
                .setHeader(KafkaHeaders.TOPIC, "address").build();
        kafkaTemplate.send(message);

        return addressDto;
    }

    public AddressDto getAddressDto(Long userId, String token) {
        return addressProxy.getAddress(userId, token);
    }

    private AddressViaCepDto getAddressFromViaCep(String cep) {
        var uriViaCep = "https://viacep.com.br/ws/"+cep+"/json/";
        System.out.println("//////////////////////////////////////////////");
        System.out.println(uriViaCep);
        System.out.println("//////////////////////////////////////////////");
        var request = new RestTemplate();
        Optional<AddressViaCepDto> addressVc =
                Optional.ofNullable(request.getForObject(uriViaCep, AddressViaCepDto.class));
        if (addressVc.isEmpty()) {
            throw new ResourceNotFoundException("Address", "cep", cep);
        }
        addressVc.get().setCep(cep);
        return addressVc.get();
    }

    private AddressDto toAddressDto(AddressViaCepDto addressViaCepDto) {
        var addressDto = new AddressDto();
        addressDto.setBairro(addressViaCepDto.getBairro());
        addressDto.setCep(addressViaCepDto.getCep());
        addressDto.setCidade(addressViaCepDto.getLocalidade());
        addressDto.setEstado(addressViaCepDto.getUf());
        addressDto.setRua(addressViaCepDto.getLogradouro());
        return addressDto;
    }
}
