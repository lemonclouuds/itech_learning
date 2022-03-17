package com.itech.learning.controller;

import com.itech.learning.domain.Lesson;
import com.itech.learning.domain.Subject;
import com.itech.learning.service.SubjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class SubjectController {
    private final SubjectService subjectService;

    @GetMapping("/subjects")
    public List<Subject> findAll() {
        return subjectService.getAll();
    }

    @GetMapping("/subjects/{id}")
    public Subject findById(@PathVariable Long id) {
        return subjectService.findById(id);
    }

    @GetMapping("/subjects/{id}/lessons")
    public List<Lesson> findSubjectLessons(@PathVariable Long id) {
        return subjectService.getSubjectLessons(id);
    }
}
