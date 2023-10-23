package com.augusto.user.proxy;

import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import com.augusto.user.payload.UserDto;

@FeignClient(name = "user-service-consumer")
public interface UserProxy {

        @GetMapping("/api/v1/user-service/system/users/user")
        public UserDto getUser(@RequestParam(name = "id", required = false) Long id,
                        @RequestParam(name = "email", required = false) String email,
                        @RequestParam(name = "cpf", required = false) String cpf,
                        @RequestHeader(name = "Authorization") String token);


        @GetMapping("/api/v1/user-service/system/users/is-valid")
        public Boolean credentialIsValid(@RequestParam("email") String email,
                        @RequestParam("cpf") String cpf);

        @GetMapping("/api/v1/user-service/system/users/all")
        public List<UserDto> listAllUsers(@RequestHeader(name = "Authorization") String token);

        @GetMapping("/api/v1/user-service/system/users/product/{id}")
        public UserDto getUserAndProduct(@PathVariable Long id,
                        @RequestHeader(name = "Authorization") String token);
}
