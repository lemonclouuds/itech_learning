package com.itech.learning.service;

import com.itech.learning.domain.Lesson;
import com.itech.learning.domain.Rating;
import com.itech.learning.domain.Subject;
import com.itech.learning.domain.dto.LessonDto;
import com.itech.learning.mapper.LessonMapper;
import com.itech.learning.repository.LessonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Collection;
import java.util.List;

import static com.itech.learning.service.ExceptionMessage.LESSON_WITH_ID_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class LessonService {
    private final LessonRepository lessonRepository;
    private final LessonMapper lessonMapper;

    private final SubjectService subjectService;

    public List<Lesson> getAll() {
        return lessonRepository.findAll();
    }

    public Lesson findById(Long lessonId) {
        return lessonRepository.findById(lessonId).orElseThrow(
                () -> new EntityNotFoundException(String.format(LESSON_WITH_ID_NOT_FOUND, lessonId)));
    }

    public LessonDto create(LessonDto lessonDto) {
        Lesson lesson = lessonMapper.lessonDtoToLesson(lessonDto);
        Subject subject = subjectService.findById(lessonDto.getSubjectId());
        lesson.setSubject(subject);

        lesson = lessonRepository.save(lesson);
        return lessonMapper.lessonToLessonDto(lesson);
    }

    public Lesson updateTitle(Long lessonId, String title) {
        Lesson lesson = findById(lessonId);
        lesson.setTitle(title);
        return lessonRepository.save(lesson);
    }

    public Lesson updateSolution(Long lessonId, String solution) {
        Lesson lesson = findById(lessonId);
        lesson.setSolution(solution);
        return lessonRepository.save(lesson);
    }

    public void deleteByIdIn(Collection<Long> ids) {
        lessonRepository.deleteByIdIn(ids);
    }

    public List<Lesson> getAllBySubjectId(Long subjectId) {
        return lessonRepository.findAllBySubjectId(subjectId);
    }

    public List<Rating> getLessonRatings(Long lessonId) {
        return lessonRepository.findById(lessonId).get().getRates();
    }

    @Transactional
    public Double calculateLessonRating(Long lessonId) {
        List<Rating> rates = lessonRepository.findById(lessonId).get().getRates();
        double res = 0.0;
        int counter = 0;
        for (Rating rate : rates) {
            res += rate.getRate();
            counter++;
        }
        return res / counter;
    }
}
