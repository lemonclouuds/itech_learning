package com.itech.learning.controller;

import com.itech.learning.domain.Lesson;
import com.itech.learning.domain.Rating;
import com.itech.learning.service.LessonService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class LessonController {
    private final LessonService lessonService;

    @GetMapping("/lessons")
    public List<Lesson> findAll() {
        return lessonService.getAll();
    }

    @GetMapping("/lessons/{id}")
    public Lesson findById(@PathVariable Long id) {
        return lessonService.findById(id);
    }

    @GetMapping("/lessons/{id}/ratings")
    public List<Rating> findLessonRatings(@PathVariable Long id) {
        return lessonService.getLessonRatings(id);
    }

    //TODO lessons?subjectId=4
    @GetMapping("/lessons/bySubject/{id}")
    public List<Lesson> findAllBySubjectId(@PathVariable Long id) {
        return lessonService.getAllBySubjectId(id);
    }

    //fix
    @PostMapping("/lessons/{id}")
    public Lesson changeTitle(@PathVariable Long id, @RequestParam String title) {
       lessonService.updateTitle(id, title);
        return lessonService.findById(id);
    }
}
