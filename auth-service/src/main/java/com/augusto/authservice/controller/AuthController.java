package com.augusto.authservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.augusto.authservice.payload.AuthResponse;
import com.augusto.authservice.payload.LoginDto;
import com.augusto.authservice.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;

@RequestMapping("/auth-service")
@RestController
public class AuthController {
    @Autowired
    private AuthService authService;

    @Operation(summary = " Authenticate user")
    @PostMapping({"/login", "/signing"})
    public ResponseEntity<AuthResponse> login(@RequestBody LoginDto loginDto) {
        String token = authService.login(loginDto);
        var jwtAuthResponse = new AuthResponse(token);
        return ResponseEntity.ok(jwtAuthResponse);
    }
}
