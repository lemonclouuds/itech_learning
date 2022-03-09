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

@Service
@RequiredArgsConstructor
public class SubjectService {
    private final String SUBJECT_WITH_ID_NOT_FOUND = "Subject[%d] not found";

    private final SubjectRepository subjectRepository;
    private final LessonRepository lessonRepository;

    public List<Subject> getAll() {
        return subjectRepository.findAll();
    }

    public void updateTitle(Long subjectId, String title) {
        Subject subject = subjectRepository.findById(subjectId).orElseThrow(
                () -> new EntityNotFoundException(String.format(SUBJECT_WITH_ID_NOT_FOUND, subjectId)));
        subject.setTitle(title);
        subjectRepository.save(subject);
    }

    @Transactional
    public void addLesson(Long subjectId, Lesson lesson) {
        Subject subject = subjectRepository.findById(subjectId).orElseThrow(
                () -> new EntityNotFoundException(String.format(SUBJECT_WITH_ID_NOT_FOUND, subjectId)));
        if (!subject.getLessons().contains(lesson)) {
            subject.getLessons().add(lesson);
        }
        subjectRepository.save(subject);
    }

    @Transactional
    public void deleteLesson(Long subjectId, Long lessonId) {
        Subject subject = subjectRepository.findById(subjectId).orElseThrow(
                () -> new EntityNotFoundException(String.format(SUBJECT_WITH_ID_NOT_FOUND, subjectId)));
        Lesson lesson = lessonRepository.findById(lessonId).orElseThrow(
                () -> new EntityNotFoundException(String.format("Lessson[%d] not found", lessonId)));

        if (subject.getLessons().contains(lesson)) {
            subject.getLessons().remove(lesson);
        }
        subjectRepository.save(subject);
    }

}
