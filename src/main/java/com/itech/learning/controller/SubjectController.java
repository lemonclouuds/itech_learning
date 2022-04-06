package com.itech.learning.controller;

import com.itech.learning.domain.dto.RatingDto;
import com.itech.learning.domain.dto.SubjectDto;
import com.itech.learning.service.RatingService;
import com.itech.learning.service.SubjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class SubjectController {
    private final SubjectService subjectService;
    private final RatingService ratingService;

    @GetMapping("/subjects")
    public List<SubjectDto> findAll() {
        return subjectService.getAll();
    }

    @GetMapping("/subjects/{subjectId}")
    public SubjectDto findById(@PathVariable Long subjectId) {
        return subjectService.findDtoById(subjectId);
    }

    @GetMapping("/subjects/{subjectId}/ratings")
    public List<RatingDto> getSubjectRatings(@PathVariable Long subjectId) {
        return subjectService.getSubjectRatings(subjectId);
    }

    @GetMapping("/subjects/{subjectId}/ratings/{ratingId}")
    public RatingDto getSubjectRatings(@PathVariable Long subjectId, @PathVariable Long ratingId) {
        return subjectService.getSubjectRatingById(subjectId, ratingId);
    }

    @PutMapping("/subjects/{subjectId}")
    public SubjectDto update(@PathVariable Long subjectId, @RequestBody SubjectDto subjectDto) {
        return subjectService.update(subjectId, subjectDto);
    }

    @PutMapping("/subjects/{subjectId}/ratings/{ratingId}")
    public RatingDto updateRating(@PathVariable Long ratingId, @RequestBody RatingDto ratingDto) {
        return ratingService.update(ratingId, ratingDto);
    }

    @PostMapping("/subjects")
    @ResponseStatus(code = HttpStatus.CREATED)
    SubjectDto create(@RequestBody SubjectDto subjectDto) {
        return subjectService.create(subjectDto);
    }

    @PostMapping("/subjects/{subjectId}/ratings")
    @ResponseStatus(code = HttpStatus.CREATED)
    public RatingDto createRating(@PathVariable Long subjectId, @RequestBody RatingDto ratingDto) {
        return subjectService.addRating(subjectId, ratingDto);
    }

    @DeleteMapping("/subjects/{subjectId}")
    @ResponseStatus(code = HttpStatus.OK)
    void delete(@PathVariable Long subjectId) {
        subjectService.deleteById(subjectId);
    }

    @DeleteMapping("/subjects/{subjectId}/ratings/{ratingId}")
    @ResponseStatus(code = HttpStatus.OK)
    void deleteRating(@PathVariable Long ratingId) {
        ratingService.deleteById(ratingId);
    }

}
