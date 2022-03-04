package com.itech.learning.repository;

import com.itech.learning.domain.Rating;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RatingRepository extends JpaRepository<Rating, Long> {
    Rating findRatingById(Long id);
    List<Rating> findAllByUserId(Long id);
    List<Rating> findAllByLessonId(Long id);
}
