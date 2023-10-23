package com.augusto.authservice.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.augusto.authservice.payload.LoginDto;
import com.augusto.authservice.repository.UserRepository;
import com.augusto.authservice.security.JwtTokenProvider;

@Service
public class AuthService {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    public String login(LoginDto loginDto) {
        Authentication authentication =
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                        loginDto.getUsername(), loginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        var user = userRepository.findByEmailOrCpf(loginDto.getUsername(), loginDto.getUsername());
        List<String> roles =  user.get().getRoles().stream().map(role-> role.getRole()).toList();
        String token = jwtTokenProvider.generateToken(authentication, user.get().getId(), roles);
        return token;

    }
}
