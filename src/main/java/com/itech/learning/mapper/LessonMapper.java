package com.itech.learning.mapper;

import com.itech.learning.domain.Lesson;
import com.itech.learning.domain.dto.LessonDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface LessonMapper {
    @Mapping(target = "subjectId", expression = "java(lesson.getSubject().getId())")
    LessonDto lessonToLessonDto(Lesson lesson);

    Lesson lessonDtoToLesson(LessonDto lessonDto);

    List<LessonDto> lessonListToLessonDtoList(List<Lesson> lessonList);
}
