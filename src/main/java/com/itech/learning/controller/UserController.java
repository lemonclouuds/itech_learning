package com.itech.learning.controller;

import com.itech.learning.domain.dto.RatingDto;
import com.itech.learning.domain.dto.UserDto;
import com.itech.learning.service.RatingService;
import com.itech.learning.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final RatingService ratingService;

    @GetMapping("/users")
    public List<UserDto> getAll() {
        return userService.getAll();
    }

    @GetMapping("/users/{userId}")
    public UserDto getById(@PathVariable Long userId) {
        return userService.findDtoById(userId);
    }

    @GetMapping("/users/{userId}/ratings")
    public List<RatingDto> getUserRatings(@PathVariable Long userId) {
        return ratingService.findAllByUserId(userId);
    }

    @GetMapping("/users/{userId}/ratings/{ratingId}")
    public RatingDto getUserRatingById(@PathVariable Long userId, @PathVariable Long ratingId) {
        return userService.getUserRatingById(userId, ratingId);
    }

    @PutMapping("/users/{userId}")
    public UserDto update(@PathVariable Long userId, @RequestBody UserDto userDto) {
        return userService.update(userId, userDto);
    }

    @PostMapping("/users")
    @ResponseStatus(code = HttpStatus.CREATED)
    UserDto create(@RequestBody UserDto userDto) {
        return userService.create(userDto);
    }

    @DeleteMapping("/users/{userId}")
    @ResponseStatus(code = HttpStatus.OK)
    void delete(@PathVariable Long userId) {
        userService.deleteById(userId);
    }

}
