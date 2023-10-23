package com.augusto.address.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import com.augusto.address.payload.AddressDto;

@FeignClient(name = "address-service-consumer")
public interface AddressProxy {
    @GetMapping("/api/v1/address-service/system/addresses/{id}")
    public AddressDto getAddress(@PathVariable Long id,
            @RequestHeader(name = "Authorization") String token);
}
