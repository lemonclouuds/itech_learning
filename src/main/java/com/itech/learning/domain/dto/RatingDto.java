package com.itech.learning.domain.dto;

import lombok.Data;

import java.util.Objects;

@Data
public class RatingDto {
    private Long id;

    private Double rate;

    private Long userId;

    private Long lessonId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RatingDto ratingDto = (RatingDto) o;
        return Objects.equals(id, ratingDto.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
