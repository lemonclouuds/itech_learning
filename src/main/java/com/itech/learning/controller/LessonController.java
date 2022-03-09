package com.itech.learning.controller;

import com.itech.learning.domain.Lesson;
import com.itech.learning.service.LessonService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class LessonController {
    private final LessonService lessonService;

    @GetMapping("/lessons")
    public List<Lesson> findAll() {
        return lessonService.getAll();
    }
}
