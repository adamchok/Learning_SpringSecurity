package com.project.SpringSecurity.service;

import com.project.SpringSecurity.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ActiveProfiles("test")
class UserServiceTest {
    private final UserService userService;

    @Autowired
    public UserServiceTest(UserService userService) {
        this.userService = userService;
    }

    @Test
    void testGetUserByID() {
        String userID = "63498649-6b05-4ae7-b55e-606bd6b33208";
        User result = userService.getUserById(userID);
        assertNotNull(result);
    }

    @Test
    void testGetUserByUsername() {
        String username = "jc_80";
        User result = userService.getUserByUsername(username);
        assertNotNull(result);
    }

    @Test
    void testGetUses() {
        List<User> result = userService.getUsers();
        assertNotNull(result);
    }
}