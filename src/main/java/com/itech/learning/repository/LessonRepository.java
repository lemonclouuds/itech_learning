package com.itech.learning.repository;

import com.itech.learning.domain.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

public interface LessonRepository extends JpaRepository<Lesson, Long> {
    List<Lesson> findAllBySubjectId(Long subjectId);

    void deleteByIdIn(Collection<Long> ids);

    @Modifying
    @Transactional
    @Query(value = "UPDATE lessons " +
            "SET title = :title " +
            "WHERE id = :id",
            nativeQuery = true)
    void changeTitle(@Param("id") Long id, @Param("title") String title);

    @Modifying
    @Transactional
    @Query(value = "UPDATE lessons " +
            "SET solution = :solution " +
            "WHERE id = :id",
            nativeQuery = true)
    void changeSolution(@Param("id") Long id, @Param("solution") String solution);
}
