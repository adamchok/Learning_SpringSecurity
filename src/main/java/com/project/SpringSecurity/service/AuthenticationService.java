package com.project.SpringSecurity.service;

import com.project.SpringSecurity.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserService userService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        User user = User.builder()
                .username(request.getUsername())
                .password(request.getPassword())
                .roles(request.getRoles())
                .build();
        UserPrincipal userPrincipal = new UserPrincipal(user);
        System.out.println(userService.createUser(user));
        var jwtToken = jwtService.generateToken(userPrincipal);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        User user = userService.getUserByUsername(request.getUsername());
        UserPrincipal userPrincipal = new UserPrincipal(user);
        var jwtToken = jwtService.generateToken(userPrincipal);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public List<User> getUsers(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        return userService.getUsers();
    }
}
