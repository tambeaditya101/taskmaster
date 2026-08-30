package com.example.taskmaster.controller;

import com.example.taskmaster.entity.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @GetMapping("/me")
    public Map<String, Object> getCurrentUser(
            @AuthenticationPrincipal User user
    ) {
        return Map.of(
                "id", user.getId(),
                "name", user.getName(),
                "email", user.getEmail()
        );
    }
}