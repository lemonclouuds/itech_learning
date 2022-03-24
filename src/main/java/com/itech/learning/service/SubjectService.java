package com.itech.learning.service;

import com.itech.learning.domain.Lesson;
import com.itech.learning.domain.Subject;
import com.itech.learning.repository.LessonRepository;
import com.itech.learning.repository.SubjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Collection;
import java.util.List;

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

    public Subject findById(Long subjectId) {
        return subjectRepository.findById(subjectId).orElseThrow(
                () -> new EntityNotFoundException(String.format(SUBJECT_WITH_ID_NOT_FOUND, subjectId)));
    }

    public List<Lesson> getSubjectLessons(Long subjectId) {
        return findById(subjectId).getLessons();
    }
}
