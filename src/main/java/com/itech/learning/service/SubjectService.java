package com.itech.learning.service;

import com.itech.learning.domain.Rating;
import com.itech.learning.domain.Subject;
import com.itech.learning.domain.User;
import com.itech.learning.domain.dto.RatingDto;
import com.itech.learning.domain.dto.SubjectDto;
import com.itech.learning.exception.EntityAlreadyExistsException;
import com.itech.learning.helper.MapperHelper;
import com.itech.learning.repository.SubjectRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static com.itech.learning.constants.ExceptionMessage.RATING_WITH_ID_NOT_FOUND;
import static com.itech.learning.constants.ExceptionMessage.SUBJECT_WITH_ID_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class SubjectService {
    private static ModelMapper modelMapper = new ModelMapper();

    private final SubjectRepository subjectRepository;
    private final UserService userService;

    public List<SubjectDto> getAll() {
        return MapperHelper.mapList(subjectRepository.findAll(), SubjectDto.class);
    }

    @Transactional
    public SubjectDto create(SubjectDto subjectDto) {
        if (!subjectRepository.existsById(subjectDto.getId())) {
            Subject subject = new Subject(subjectDto.getTitle(), subjectDto.getSolution());
            subjectRepository.save(subject);
        } else {
            throw new EntityAlreadyExistsException(String.format("Subject with id[%d] already exists",
                    subjectDto.getId()));
        }
        return subjectDto;
    }

    @Transactional
    public SubjectDto update(Long subjectId, SubjectDto subjectDto) {
        Subject subject = modelMapper.map(subjectDto, Subject.class);
        subject.setId(subjectId);
        if (!subjectRepository.existsById(subjectId)) {
            throw new EntityNotFoundException(String.format(SUBJECT_WITH_ID_NOT_FOUND, subjectId));
        } else {
            subjectRepository.save(subject);
        }
        return subjectDto;
    }

    @Transactional
    public RatingDto addRating(Long subjectId, RatingDto ratingDto) {
        Subject subject = findById(subjectId);
        User user = userService.findById(ratingDto.getUserId());
        Rating rating = modelMapper.map(ratingDto, Rating.class);
        Optional<Rating> found = subject.getRatings()
                .stream()
                .filter(rating1 -> ratingDto.getUserId().equals(rating1.getUser().getId()))
                .findFirst();
        if (found.isPresent()) {
            throw new EntityAlreadyExistsException("User had already rated this subject");
        } else {
                subject.getRatings().add(rating);
                rating.setSubject(subject);
                rating.setUser(user);
                subjectRepository.save(subject);
        }
        return ratingDto;
    }

    @Transactional
    public void deleteById(Long id) {
        if (subjectRepository.existsById(id)) {
            subjectRepository.deleteById(id);
        }
    }

    @Transactional
    public void deleteByIdIn(Collection<Long> ids) {
        for (Long id : ids) {
            deleteById(id);
        }
    }

    @Transactional
    public Subject findById(Long subjectId) {
        return subjectRepository.findById(subjectId).orElseThrow(
                () -> new EntityNotFoundException(String.format(SUBJECT_WITH_ID_NOT_FOUND, subjectId)));
    }

    @Transactional
    public SubjectDto findDtoById(Long subjectId) {
        return modelMapper.map(findById(subjectId), SubjectDto.class);
    }

    @Transactional
    public List<RatingDto> getSubjectRatings(Long subjectId) {
        Subject subject = findById(subjectId);
        return MapperHelper.mapList(subject.getRatings(), RatingDto.class);
    }

    @Transactional
    public RatingDto getSubjectRatingById(Long subjectId, Long ratingId) {
        Rating found = findById(subjectId).getRatings()
                .stream()
                .filter(rating -> ratingId.equals(rating.getId()))
                .findFirst()
                .orElseThrow(
                        () -> new EntityNotFoundException(String.format(RATING_WITH_ID_NOT_FOUND, ratingId)));
        return modelMapper.map(found, RatingDto.class);
    }

}
