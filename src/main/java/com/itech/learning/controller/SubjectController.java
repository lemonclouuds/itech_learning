package com.itech.learning.controller;

import com.itech.learning.domain.dto.RatingDto;
import com.itech.learning.domain.dto.SubjectDto;
import com.itech.learning.service.RatingService;
import com.itech.learning.service.SubjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class SubjectController {
    private final SubjectService subjectService;
    private final RatingService ratingService;

    @GetMapping("/subjects")
    public ResponseEntity<List<SubjectDto>> findAll() {
        return ResponseEntity.ok(subjectService.getAll());
    }

    @GetMapping("/subjects/{subjectId}")
    public ResponseEntity<SubjectDto> findById(@PathVariable Long subjectId) {
        return ResponseEntity.ok(subjectService.findDtoById(subjectId));
    }

    @GetMapping("/subjects/{subjectId}/ratings")
    public ResponseEntity<List<RatingDto>> getSubjectRatings(@PathVariable Long subjectId) {
        return ResponseEntity.ok(subjectService.getSubjectRatings(subjectId));
    }

    @GetMapping("/subjects/{subjectId}/ratings/{ratingId}")
    public ResponseEntity<RatingDto> getSubjectRatings(@PathVariable Long subjectId, @PathVariable Long ratingId) {
        return ResponseEntity.ok(subjectService.getSubjectRatingById(subjectId, ratingId));
    }

    @PutMapping("/subjects/{subjectId}")
    public ResponseEntity<SubjectDto> update(@PathVariable Long subjectId, @RequestBody SubjectDto subjectDto) {
        return ResponseEntity.ok(subjectService.update(subjectId, subjectDto));
    }

    @PutMapping("/subjects/{subjectId}/ratings/{ratingId}")
    public ResponseEntity<RatingDto> updateRating(@PathVariable Long ratingId, @RequestBody RatingDto ratingDto) {
        return ResponseEntity.ok(ratingService.update(ratingId, ratingDto));
    }

    @PostMapping("/subjects")
    @ResponseStatus(code = HttpStatus.CREATED)
    ResponseEntity<SubjectDto> create(@RequestBody SubjectDto subjectDto) {
        return ResponseEntity.ok(subjectService.create(subjectDto));
    }

    @PostMapping("/subjects/{subjectId}/ratings")
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseEntity<RatingDto> createRating(@PathVariable Long subjectId, @RequestBody RatingDto ratingDto) {
        return ResponseEntity.ok(subjectService.addRating(subjectId, ratingDto));
    }

    @DeleteMapping("/subjects/{subjectId}")
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity<?> delete(@PathVariable Long subjectId) {
        subjectService.deleteById(subjectId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(String.format("Subject[%d] was deleted", subjectId));
    }

    @DeleteMapping("/subjects/{subjectId}/ratings/{ratingId}")
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity<?> deleteRating(@PathVariable Long ratingId) {
        ratingService.deleteById(ratingId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(String.format("Rating[%d] was deleted", ratingId));
    }

}
