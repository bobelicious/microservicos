package com.augusto.user.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.augusto.user.payload.UserDto;
import com.augusto.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "User process endpoint")
@RestController
@RequestMapping("/api/v1/user-service/system/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Operation(summary = " Find a specific user and related products")
    @GetMapping("/product/{id}")
    public ResponseEntity<UserDto> getUserAndProduct(@PathVariable Long id) {
        return new ResponseEntity<UserDto>(userService.getUserAndProduct(id), HttpStatus.OK);
    }

    @Operation(summary = " Find a specific user by id")
    @GetMapping("/user")
    public ResponseEntity<UserDto> getUser(@RequestParam(name = "id", required = false) Long id,
            @RequestParam(name = "email", required = false) String email,
            @RequestParam(name = "cpf", required = false) String cpf) {
        return new ResponseEntity<UserDto>(userService.getUser(id, email, cpf), HttpStatus.OK);
    }

    @Operation(summary = "List all users")
    @GetMapping("/all")
    public ResponseEntity<List<UserDto>> listAllUsers() {
        return new ResponseEntity<List<UserDto>>(userService.listAllUsers(), HttpStatus.OK);
    }

    @Operation(description = "verify credentials")
    @GetMapping("/is-valid")
    public ResponseEntity<Boolean> credentialIsValid(@RequestParam("email") String email,
            @RequestParam("cpf") String cpf) {
        return new ResponseEntity<Boolean>(userService.credentialsIsValid(email, cpf),
                HttpStatus.OK);
    }
}
