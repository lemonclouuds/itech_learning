package com.itech.learning.controller;

import com.itech.learning.domain.Subject;
import com.itech.learning.repository.SubjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class SubjectController {
    private final SubjectRepository subjectRepository;

    @GetMapping("/subjects")
    public List<Subject> findAll(){
        return subjectRepository.findAll();
    }
}
