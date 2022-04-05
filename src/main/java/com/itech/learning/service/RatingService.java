package com.itech.learning.service;

import com.itech.learning.domain.Rating;
import com.itech.learning.domain.Subject;
import com.itech.learning.domain.User;
import com.itech.learning.domain.dto.RatingDto;
import com.itech.learning.repository.RatingRepository;
import com.itech.learning.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Collection;
import java.util.List;

import static com.itech.learning.service.ExceptionMessage.RATING_WITH_ID_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class RatingService {
    private static ModelMapper modelMapper = new ModelMapper();

    private final RatingRepository ratingRepository;
    private final UserRepository userRepository;
    private final UserService userService;
    private final SubjectService subjectService;

    public List<Rating> getAll() {
        return ratingRepository.findAll();
    }

    public Rating findById(Long ratingId) {
        return ratingRepository.findById(ratingId).orElseThrow(
                () -> new EntityNotFoundException(String.format(RATING_WITH_ID_NOT_FOUND, ratingId)));
    }

    public List<Rating> findAllBySubjectId(Long subjectId) {
        return ratingRepository.findAllBySubjectId(subjectId);
    }

    public List<Rating> findAllByUserId(Long userId) {
        return ratingRepository.findAllByUserId(userId);
    }

    public RatingDto create(RatingDto ratingDto) {
        //
        return ratingDto;
    }

    /*public Rating addRating(Double rate, Long userId, Long lessonId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new EntityNotFoundException(String.format(USER_WITH_ID_NOT_FOUND, userId)));
        Lesson lesson = lessonRepository.findById(lessonId).orElseThrow(
                () -> new EntityNotFoundException(String.format(LESSON_WITH_ID_NOT_FOUND, lessonId)));
        Rating rating = new Rating(rate, user, lesson);

        if (!ratingRepository.findAll().contains(rating)) {
            ratingRepository.save(rating);
        }

        return rating;
    }*/

    @Transactional
    public RatingDto update(RatingDto ratingDto) throws Exception {
        if (ratingRepository.existsById(ratingDto.getId())) {
            Rating rating = modelMapper.map(ratingDto, Rating.class);

            User user = userService.findById(ratingDto.getUserId());
            Subject subject = subjectService.findById(ratingDto.getSubjectId());

            rating.setUser(user);
            rating.setSubject(subject);
            ratingRepository.save(rating); //TODO modify save() in repo or create update() w/ native query
        } else {
            throw new Exception(); //TODO custom exception?
        }

        return ratingDto;
    }

    @Transactional
    public void deleteById(Long id) {
        if (ratingRepository.existsById(id)) {
            ratingRepository.deleteById(id);
        }
    }

    @Transactional
    public void deleteByIdIn(Collection<Long> ids) {
        for (Long id : ids) {
            deleteById(id);
        }
    }
}
