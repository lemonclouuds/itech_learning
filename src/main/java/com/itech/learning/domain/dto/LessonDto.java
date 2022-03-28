package com.itech.learning.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LessonDto {
    //can be null
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
