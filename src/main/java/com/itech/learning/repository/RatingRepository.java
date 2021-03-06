package com.itech.learning.repository;

import com.itech.learning.domain.Rating;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface RatingRepository extends JpaRepository<Rating, Long> {
    List<Rating> findAllByUserId(Long id);
    List<Rating> findAllBySubjectId(Long id);
    void deleteByIdIn(Collection<Long> ids);
}
