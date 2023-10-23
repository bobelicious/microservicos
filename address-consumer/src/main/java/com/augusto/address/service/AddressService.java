package com.augusto.address.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import com.augusto.address.exceptions.ResourceNotFoundException;
import com.augusto.address.model.Address;
import com.augusto.address.payload.AddressDto;
import com.augusto.address.repository.AddressRepository;

@Service
public class AddressService {
    @Autowired
    private AddressRepository addressRepository;

    @KafkaListener(topics = "address", groupId = "address",
            containerFactory = "kafkaListenerContainerFactory")
    public void createAddress(AddressDto addressDto) {
        var address = toAddress(addressDto);
        addressRepository.save(address);
    }

    private Address toAddress(AddressDto addressDto) {
        var address = new Address();
        address.setBairro(addressDto.getBairro());
        address.setCep(addressDto.getCep());
        address.setCidade(addressDto.getCidade());
        address.setComplemento(addressDto.getComplemento());
        address.setEstado(addressDto.getEstado());
        address.setNumero(addressDto.getNumero());
        address.setRua(addressDto.getRua());
        address.setUserId(addressDto.getUserId());
        return address;
    }

    public AddressDto getAddressDto(Long userId) {
        var address = addressRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Address", "user", userId));
        return new AddressDto(address);
    }

}
