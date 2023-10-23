package com.augusto.address.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.augusto.address.payload.AddressDto;
import com.augusto.address.service.AddressService;

@RestController
@RequestMapping("/api/v1/address-service/system/addresses")
public class AddressController {
    @Autowired
    private AddressService addressService;

    @GetMapping("/{id}")
    public ResponseEntity<AddressDto> getAddress(@PathVariable Long id){
        return new ResponseEntity<AddressDto>(addressService.getAddressDto(id),HttpStatus.OK);
    }
}
