package com.itech.learning.service;

import com.itech.learning.domain.Rating;
import com.itech.learning.domain.Subject;
import com.itech.learning.domain.User;
import com.itech.learning.domain.dto.RatingDto;
import com.itech.learning.exception.EntityAlreadyExistsException;
import com.itech.learning.helper.MapperHelper;
import com.itech.learning.repository.RatingRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Collection;
import java.util.List;

import static com.itech.learning.constants.ExceptionMessage.RATING_WITH_ID_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class RatingService {
    private static ModelMapper modelMapper = new ModelMapper();

    private final RatingRepository ratingRepository;
    private final UserService userService;
    private final SubjectService subjectService;

    @Transactional
    public List<RatingDto> getAll() {
        return MapperHelper.mapList(ratingRepository.findAll(), RatingDto.class);
    }

    @Transactional
    public Rating findById(Long ratingId) {
        return ratingRepository.findById(ratingId).orElseThrow(
                () -> new EntityNotFoundException(String.format(RATING_WITH_ID_NOT_FOUND, ratingId)));
    }

    @Transactional
    public RatingDto findDtoById(Long ratingId) {
        return modelMapper.map(findById(ratingId), RatingDto.class);
    }

    public List<RatingDto> findAllBySubjectId(Long subjectId) {
        return MapperHelper.mapList(ratingRepository.findAllBySubjectId(subjectId), RatingDto.class);
    }

    public List<RatingDto> findAllByUserId(Long userId) {
        return MapperHelper.mapList(ratingRepository.findAllByUserId(userId), RatingDto.class);
    }

    @Transactional
    public RatingDto create(RatingDto ratingDto) {
        Rating rating = modelMapper.map(ratingDto, Rating.class);
        if (!ratingRepository.existsById(ratingDto.getId())) {
            User user = userService.findById(ratingDto.getUserId());
            Subject subject = subjectService.findById(ratingDto.getSubjectId());

            rating.setUser(user);
            rating.setSubject(subject);
            ratingRepository.save(rating);
        } else {
            throw new EntityAlreadyExistsException(String.format("Rating with id[%d] already exists", ratingDto.getId()));
        }
        return ratingDto;
    }

    @Transactional
    public RatingDto update(Long ratingId, RatingDto ratingDto) {
        Rating rating = modelMapper.map(ratingDto, Rating.class);
        rating.setId(ratingId);
        if (ratingRepository.existsById(ratingId)) {
            User user = userService.findById(ratingDto.getUserId());
            Subject subject = subjectService.findById(ratingDto.getSubjectId());

            rating.setUser(user);
            rating.setSubject(subject);
            ratingRepository.save(rating);
        } else {
            throw new EntityNotFoundException(String.format(RATING_WITH_ID_NOT_FOUND, ratingId));
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
