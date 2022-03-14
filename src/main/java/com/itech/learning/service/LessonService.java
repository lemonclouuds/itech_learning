package com.itech.learning.service;

import com.itech.learning.domain.Lesson;
import com.itech.learning.domain.Rating;
import com.itech.learning.repository.LessonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Collection;
import java.util.List;

import static com.itech.learning.service.ExceptionMessage.LESSON_WITH_ID_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class LessonService {
    private final LessonRepository lessonRepository;

    public List<Lesson> getAll() {
        return lessonRepository.findAll();
    }

    public Lesson getById(Long lessonId) {
        return lessonRepository.findById(lessonId).orElseThrow(
                () -> new EntityNotFoundException(String.format(LESSON_WITH_ID_NOT_FOUND, lessonId)));
    }

    public void updateTitle(Long lessonId, String title) {
        lessonRepository.changeTitle(lessonId, title);
    }

    public void updateSolution(Long lessonId, String solution) {
        lessonRepository.changeSolution(lessonId, solution);
    }

    public void deleteByIdIn(Collection<Long> ids) {
        lessonRepository.deleteByIdIn(ids);
    }

    public List<Lesson> getAllBySubjectId(Long subjectId) {
        return lessonRepository.findAllBySubjectId(subjectId);
    }

    public List<Rating> getLessonRatings(Long lessonId) {
        return lessonRepository.getById(lessonId).getRates();
    }
}
