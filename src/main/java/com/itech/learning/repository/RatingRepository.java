package com.itech.learning.repository;

import com.itech.learning.domain.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface RatingRepository extends JpaRepository<Rating, Long> {
    List<Rating> findAllByUserId(Long id);
    List<Rating> findAllByLessonId(Long id);

    @Modifying
    @Transactional
    @Query(value = "UPDATE ratings " +
            "SET rate = :rate " +
            "WHERE id = :id",
            nativeQuery = true)
    void changeRate(@Param("id") Long id, @Param("rate") Double rate);
}
