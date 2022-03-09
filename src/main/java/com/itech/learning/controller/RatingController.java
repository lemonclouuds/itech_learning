package com.itech.learning.controller;

import com.itech.learning.domain.Rating;
import com.itech.learning.service.RatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class RatingController {
    private final RatingService ratingService;

    @GetMapping("/ratings")
    public List<Rating> findAll() {
        return ratingService.getAll();
    }
}
