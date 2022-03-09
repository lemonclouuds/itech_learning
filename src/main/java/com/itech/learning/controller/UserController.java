package com.itech.learning.controller;

import com.itech.learning.domain.User;
import com.itech.learning.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserRepository userRepository;

    @GetMapping("/users")
    public List<User> findAll() {
        return userRepository.findAll();
    }
}
