package com.itech.learning.domain.dto;

import lombok.Data;

@Data
public class LessonDto {
    private Long id;

    private String title;

    private String solution;

    private Long subjectId;
}
