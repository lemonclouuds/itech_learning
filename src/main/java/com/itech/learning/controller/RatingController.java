package com.itech.learning.controller;

import com.itech.learning.domain.Rating;
import com.itech.learning.service.RatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetMapping("/ratings/{id}")
    public List<Rating> findById(@PathVariable Long id) {
        return ratingService.getAll();
    }

    //TODO ratings?lessonId=4
    @GetMapping("/ratings/byLesson/{id}")
    public List<Rating> findAllByLessonId(@PathVariable Long id) {
        return ratingService.getAllByLessonId(id);
    }

    //TODO ratings?userId=1
    @GetMapping("/ratings/byUser/{id}")
    public List<Rating> findAllByUserId(@PathVariable Long id) {
        return ratingService.getAllByUserId(id);
    }
}
