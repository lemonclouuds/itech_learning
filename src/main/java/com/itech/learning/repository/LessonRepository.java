package com.itech.learning.repository;

import com.itech.learning.domain.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LessonRepository extends JpaRepository<Lesson, Long> {
    Lesson findLessonById(Long id);
    List<Lesson> findLessonBySubjectId(Long id);
}
