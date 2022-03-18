package com.itech.learning.repository;

import com.itech.learning.domain.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface LessonRepository extends JpaRepository<Lesson, Long> {
    List<Lesson> findAllBySubjectId(Long subjectId);
    void deleteByIdIn(Collection<Long> ids);
}
