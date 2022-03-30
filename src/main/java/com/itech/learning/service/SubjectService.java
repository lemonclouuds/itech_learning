package com.itech.learning.service;

import com.itech.learning.domain.Lesson;
import com.itech.learning.domain.Subject;
import com.itech.learning.domain.dto.LessonDto;
import com.itech.learning.domain.dto.SubjectDto;
import com.itech.learning.repository.LessonRepository;
import com.itech.learning.repository.SubjectRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Collection;
import java.util.List;

import static com.itech.learning.service.ExceptionMessage.SUBJECT_WITH_ID_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class SubjectService {
    private static ModelMapper modelMapper = new ModelMapper();

    private final SubjectRepository subjectRepository;
    private final LessonRepository lessonRepository;

    public List<Subject> getAll() {
        return subjectRepository.findAll();
    }

    @Transactional
    public Subject updateTitle(Long subjectId, String title) {
        Subject subject = findById(subjectId);
        subject.setTitle(title);
        subjectRepository.save(subject);
        return subject;
    }

    @Transactional
    public SubjectDto create(SubjectDto subjectDto) {
        if (!subjectRepository.existsById(subjectDto.getId())) {
            Subject subject = new Subject(subjectDto.getTitle());
            subjectRepository.save(subject);
        } else {
            update(subjectDto);
        }
        return subjectDto;
    }

    @Transactional
    public SubjectDto update(SubjectDto subjectDto) {
        Subject subject = modelMapper.map(subjectDto, Subject.class);
        if (!subjectRepository.existsById(subjectDto.getId())) {
            create(subjectDto);
        } else {
            List<Lesson> lessons = lessonRepository.findAllBySubjectId(subjectDto.getId());
            subject.setLessons(lessons);
            subjectRepository.save(subject);
        }
        return subjectDto;
    }

    @Transactional
    public SubjectDto addLesson(Long subjectId, LessonDto lessonDto) {
        Subject subject = findById(subjectId);
        Lesson lesson = modelMapper.map(lessonDto, Lesson.class);
        if (!subject.getLessons().contains(lesson)) {
            subject.getLessons().add(lesson);
        }
        subjectRepository.save(subject);
        return modelMapper.map(subject, SubjectDto.class);
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

    @Transactional
    public List<Lesson> getSubjectLessons(Long subjectId) {
        return findById(subjectId).getLessons();
    }
}
