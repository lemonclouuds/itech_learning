package com.itech.learning.service;

import com.itech.learning.domain.Lesson;
import com.itech.learning.domain.Rating;
import com.itech.learning.domain.User;
import com.itech.learning.repository.LessonRepository;
import com.itech.learning.repository.RatingRepository;
import com.itech.learning.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

import static com.itech.learning.service.ExceptionMessage.*;

@Service
@RequiredArgsConstructor
public class RatingService {
    private final RatingRepository ratingRepository;
    private final UserRepository userRepository;
    private final LessonRepository lessonRepository;

    public List<Rating> getAll() {
        return ratingRepository.findAll();
    }

    public Rating getById(Long ratingId) {
        return ratingRepository.findById(ratingId).orElseThrow(
                () -> new EntityNotFoundException(String.format(RATING_WITH_ID_NOT_FOUND, ratingId)));
    }

    public Rating addRating(Double rate, Long userId, Long lessonId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new EntityNotFoundException(String.format(USER_WITH_ID_NOT_FOUND, userId)));
        Lesson lesson = lessonRepository.findById(lessonId).orElseThrow(
                () -> new EntityNotFoundException(String.format(LESSON_WITH_ID_NOT_FOUND, lessonId)));
        Rating rating = new Rating(rate, user, lesson);

        if (!ratingRepository.findAll().contains(rating)) {
            ratingRepository.save(rating);
        }

        return rating;
    }

    public Rating updateRating(Long ratingId, Double rate) {
        Rating rating = getById(ratingId);
        rating.setRate(rate);
        ratingRepository.save(rating);
        return rating;
    }

    public List<Rating> getAllByLessonId(Long lessonId) {
        return ratingRepository.findAllByLessonId(lessonId);
    }

    public List<Rating> getAllByUserId(Long userId) {
        return ratingRepository.findAllByUserId(userId);
    }
}
