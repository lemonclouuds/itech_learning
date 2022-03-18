package com.itech.learning.service;

import com.itech.learning.domain.Lesson;
import com.itech.learning.domain.Subject;
import com.itech.learning.repository.LessonRepository;
import com.itech.learning.repository.SubjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

import static com.itech.learning.service.ExceptionMessage.LESSON_WITH_ID_NOT_FOUND;
import static com.itech.learning.service.ExceptionMessage.SUBJECT_WITH_ID_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class SubjectService {
    private final SubjectRepository subjectRepository;
    private final LessonRepository lessonRepository;

    public List<Subject> getAll() {
        return subjectRepository.findAll();
    }

    public Subject updateTitle(Long subjectId, String title) {
        Subject subject = findById(subjectId);
        subject.setTitle(title);
        subjectRepository.save(subject);
        return subject;
    }

    @Transactional
    public Subject addLesson(Long subjectId, Lesson lesson) {
        Subject subject = findById(subjectId);
        if (!subject.getLessons().contains(lesson)) {
            subject.getLessons().add(lesson);
        }
        subjectRepository.save(subject);
        return subject;
    }

    @Transactional
    public boolean deleteLesson(Long subjectId, Long lessonId) {
        boolean res = false;
        Subject subject = findById(subjectId);
        Lesson lesson = lessonRepository.findById(lessonId).orElseThrow(
                () -> new EntityNotFoundException(String.format(LESSON_WITH_ID_NOT_FOUND, lessonId)));

        if (subject.getLessons().contains(lesson)) {
            subject.getLessons().remove(lesson);
            lesson.setSubject(null);
            res = true;
        }
        subjectRepository.save(subject);
        lessonRepository.save(lesson);
        return res;
    }

    public Subject findById(Long subjectId) {
        return subjectRepository.findById(subjectId).orElseThrow(
                () -> new EntityNotFoundException(String.format(SUBJECT_WITH_ID_NOT_FOUND, subjectId)));
    }

    public List<Lesson> getSubjectLessons(Long subjectId) {
        return findById(subjectId).getLessons();
    }
}
