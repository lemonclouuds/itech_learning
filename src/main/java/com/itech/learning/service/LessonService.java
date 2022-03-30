package com.itech.learning.service;

import com.itech.learning.domain.Lesson;
import com.itech.learning.domain.Rating;
import com.itech.learning.domain.Subject;
import com.itech.learning.domain.dto.LessonDto;
import com.itech.learning.repository.LessonRepository;
import com.itech.learning.repository.SubjectRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Collection;
import java.util.List;

import static com.itech.learning.service.ExceptionMessage.LESSON_WITH_ID_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class LessonService {
    private static ModelMapper modelMapper = new ModelMapper();

    private final LessonRepository lessonRepository;
    private final SubjectService subjectService;
    private final RatingService ratingService;

    private final SubjectRepository subjectRepository;

    public List<Lesson> getAll() {
        return lessonRepository.findAll();
    }

    public Lesson findById(Long lessonId) {
        return lessonRepository.findById(lessonId).orElseThrow(
                () -> new EntityNotFoundException(String.format(LESSON_WITH_ID_NOT_FOUND, lessonId)));
    }

    @Transactional
    public LessonDto create(LessonDto lessonDto) {
        // no lessonId
        Lesson lesson = modelMapper.map(lessonDto, Lesson.class);
        if (lessonRepository.existsById(lessonDto.getId())) {
            update(lessonDto);
            //exception - trying to create already existing entity
        } else {
            Subject subject = subjectService.findById(lessonDto.getSubjectId());
            lesson.setSubject(subject);
            subject.getLessons().add(lesson); //это разве не бд должна делать? + + оно не работает
            lessonRepository.save(lesson);
        }
        return lessonDto;
    }

    @Transactional
    public LessonDto update(LessonDto lessonDto) {
        Lesson lesson = modelMapper.map(lessonDto, Lesson.class);
        if (!lessonRepository.existsById(lessonDto.getId())) {
            //create(lessonDto);
            //exception
        } else {
            Subject subject = subjectService.findById(lessonDto.getSubjectId());
            List<Rating> rates = ratingService.getAllByLessonId(lessonDto.getId());

            lesson.setSubject(subject);
            lesson.setRates(rates);

            subject.getLessons().add(lesson);
            //log.info()
            System.out.println("\n\n\n---------------------------------\n" + subject.getLessons());
            for (Rating rate : rates) {
                rate.setLesson(lesson);
            }
            lessonRepository.save(lesson);
        }
        return lessonDto;
    }

    @Transactional
    public void deleteById(Long id) {
        if (lessonRepository.existsById(id)) {
            lessonRepository.deleteById(id);
        }
    }

    @Transactional
    public void deleteByIdIn(Collection<Long> ids) {
        for (Long id : ids) {
            deleteById(id);
        }
    }

    public List<Lesson> getAllBySubjectId(Long subjectId) {
        return lessonRepository.findAllBySubjectId(subjectId);
    }

    public List<Rating> getLessonRatings(Long lessonId) {
        return findById(lessonId).getRates();
    }

    @Transactional
    public Double calculateLessonRating(Long lessonId) {
        List<Rating> rates = findById(lessonId).getRates();
        double res = 0.0;
        int counter = 0;
        for (Rating rate : rates) {
            res += rate.getRate();
            counter++;
        }
        return res / counter;
    }
}
