package com.itech.learning.controller;

import com.itech.learning.domain.Rating;
import com.itech.learning.domain.User;
import com.itech.learning.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/users")
    public List<User> findAll() {
        return userService.getAll();
    }

    @GetMapping("/users/{id}")
    public User findById(@PathVariable Long id) {
        return userService.getById(id);
    }

    @GetMapping("/users/{id}/ratings")
    public List<Rating> findUserRatings(@PathVariable Long id) {
        return userService.getUserRates(id);
    }

}
