package com.itech.learning.controller;

import com.itech.learning.domain.dto.RatingDto;
import com.itech.learning.domain.dto.UserDto;
import com.itech.learning.service.RatingService;
import com.itech.learning.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final RatingService ratingService;

    @GetMapping("/users")
    @PreAuthorize("hasAuthority('user:read')")
    public ResponseEntity<List<UserDto>> getAll() {
        return ResponseEntity.ok(userService.getAll());
    }

    @GetMapping("/users/{userId}")
    @PreAuthorize("hasAuthority('user:read')")
    public ResponseEntity<UserDto> getById(@PathVariable Long userId) {
        return ResponseEntity.ok(userService.findDtoById(userId));
    }

    @GetMapping("/users/{userId}/ratings")
    @PreAuthorize("hasAuthority('rating:read')")
    public ResponseEntity<List<RatingDto>> getUserRatings(@PathVariable Long userId) {
        return ResponseEntity.ok(ratingService.findAllByUserId(userId));
    }

    @GetMapping("/users/{userId}/ratings/{ratingId}")
    @PreAuthorize("hasAuthority('rating:read')")
    public ResponseEntity<RatingDto> getUserRatingById(@PathVariable Long userId, @PathVariable Long ratingId) {
        return ResponseEntity.ok(userService.getUserRatingById(userId, ratingId));
    }

    @PutMapping("/users/{userId}")
    @PreAuthorize("hasAuthority('user:write')")
    public ResponseEntity<UserDto> update(@PathVariable Long userId, @RequestBody UserDto userDto) {
        return ResponseEntity.ok(userService.update(userId, userDto));
    }

    @PostMapping("/users")
    @PreAuthorize("hasAuthority('user:write')")
    ResponseEntity<UserDto> create(@RequestBody UserDto userDto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userService.create(userDto));
    }

    @DeleteMapping("/users/{userId}")
    @PreAuthorize("hasAuthority('user:write')")
    public ResponseEntity<?> delete(@PathVariable Long userId) {
        userService.deleteById(userId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(String.format("User[%d] was deleted", userId));
    }

}
