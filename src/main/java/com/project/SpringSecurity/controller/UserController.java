package com.project.SpringSecurity.controller;

import com.project.SpringSecurity.model.AuthenticationRequest;
import com.project.SpringSecurity.model.AuthenticationResponse;
import com.project.SpringSecurity.model.RegisterRequest;
import com.project.SpringSecurity.model.User;
import com.project.SpringSecurity.service.AuthenticationService;
import com.project.SpringSecurity.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/students/account")
public class UserController {
    private final AuthenticationService authService;
    private final UserService userService;

    @Autowired
    public UserController(AuthenticationService authService, UserService userService) {
        this.authService = authService;
        this.userService = userService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(authService.authenticate(request));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<User>> getUsers() {
        return ResponseEntity.ok().body(userService.getUsers());
    }

/*    private final UserService userService;
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }*/

/*    @GetMapping
    public ResponseEntity<List<User>> getUsers() {
        return ResponseEntity.ok().body(userService.getUsers());
    }

    @GetMapping("/user/id")
    public ResponseEntity<User> getUserById(@RequestParam("id") String id) {
        return ResponseEntity.ok().body(userService.getUserById(id));
    }

    @GetMapping("/user/username")
    public ResponseEntity<User> getUserByUsername(@RequestParam("username") String username) {
        return ResponseEntity.ok().body(userService.getUserByUsername(username));
    }

    @PostMapping
    public ResponseEntity<UUID> createUser(@Valid @RequestBody User user) {
        return ResponseEntity.ok().body(userService.createUser(user));
    }*/
}
