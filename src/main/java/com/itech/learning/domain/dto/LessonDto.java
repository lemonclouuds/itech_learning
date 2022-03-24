package com.itech.learning.domain.dto;

import lombok.Data;

import java.util.Objects;

@Data
public class LessonDto {
    private Long id;

    private String title;

    private String solution;

    private Long subjectId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LessonDto lessonDto = (LessonDto) o;
        return Objects.equals(id, lessonDto.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
