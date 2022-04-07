package com.itech.learning.controller;

import com.itech.learning.domain.dto.RatingDto;
import com.itech.learning.domain.dto.UserDto;
import com.itech.learning.service.RatingService;
import com.itech.learning.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final RatingService ratingService;

    @GetMapping("/users")
    public ResponseEntity<List<UserDto>> getAll() {
        return ResponseEntity.ok(userService.getAll());
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<UserDto> getById(@PathVariable Long userId) {
        return ResponseEntity.ok(userService.findDtoById(userId));
    }

    @GetMapping("/users/{userId}/ratings")
    public ResponseEntity<List<RatingDto>> getUserRatings(@PathVariable Long userId) {
        return ResponseEntity.ok(ratingService.findAllByUserId(userId));
    }

    @GetMapping("/users/{userId}/ratings/{ratingId}")
    public ResponseEntity<RatingDto> getUserRatingById(@PathVariable Long userId, @PathVariable Long ratingId) {
        return ResponseEntity.ok(userService.getUserRatingById(userId, ratingId));
    }

    @PutMapping("/users/{userId}")
    public ResponseEntity<UserDto> update(@PathVariable Long userId, @RequestBody UserDto userDto) {
        return ResponseEntity.ok(userService.update(userId, userDto));
    }

    @PostMapping("/users")
    ResponseEntity<UserDto> create(@RequestBody UserDto userDto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userService.create(userDto));
    }

    @DeleteMapping("/users/{userId}")
    public ResponseEntity<?> delete(@PathVariable Long userId) {
        userService.deleteById(userId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(String.format("User[%d] was deleted", userId));
    }

}
