package com.augusto.user.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.augusto.user.payload.UserDto;
import com.augusto.user.payload.UserInputDto;
import com.augusto.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "User endpoint")
@RestController
@RequestMapping("/api/v1/user-service/process/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Operation(summary = " Find a specific user and related products")
    @GetMapping("/user-product/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id, @RequestHeader(name = "Authorization") String token) {
        return new ResponseEntity<UserDto>(userService.getUserAndProduct(id, token), HttpStatus.OK);
    }

    @Operation(summary = " Find a specific user by id")
    @GetMapping("/user")
    public ResponseEntity<UserDto> getUser(@RequestParam(name = "id", required = false) Long id,
            @RequestParam(name = "email", required = false) String email,
            @RequestParam(name = "cpf", required = false) String cpf,@RequestHeader(name = "Authorization") String token) {
        return new ResponseEntity<UserDto>(userService.getUser(id, email, cpf, token), HttpStatus.OK);
    }

    @Operation(summary = "List all users")
    @GetMapping("/all")
    public ResponseEntity<List<UserDto>> listAllUsers(@RequestHeader(name = "Authorization") String token) {
        return new ResponseEntity<List<UserDto>>(userService.listAllUsers(token), HttpStatus.OK);
    }

    @Operation(description = "Create a new user")
    @PostMapping("/new")
    public ResponseEntity<UserDto> createNewUser(@RequestBody @Valid UserInputDto userInputDto) {
        return new ResponseEntity<UserDto>(userService.createUser(userInputDto),
                HttpStatus.CREATED);
    }
}
