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

@Service
@RequiredArgsConstructor
public class RatingService {
    private final String LESSON_WITH_ID_NOT_FOUND = "Lesson[%d] not found";
    private final String USER_WITH_ID_NOT_FOUND = "User[%d] not found";
    private final String RATING_WITH_ID_NOT_FOUND = "Rating[%d] not found";

    private final RatingRepository ratingRepository;
    private final UserRepository userRepository;
    private final LessonRepository lessonRepository;

    public List<Rating> getAll() {
        return ratingRepository.findAll();
    }

    public Rating addRating(String rate, Long userId, Long lessonId) {
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

    public Rating updateRating(Long ratingId, String rate) {
        Rating rating = ratingRepository.findById(ratingId).orElseThrow(
                () -> new EntityNotFoundException(String.format(RATING_WITH_ID_NOT_FOUND, ratingId)));
        rating.setRate(rate);
        ratingRepository.save(rating);
        return rating;
    }
}
