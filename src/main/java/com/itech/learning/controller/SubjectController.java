package com.itech.learning.controller;

import com.itech.learning.domain.dto.RatingDto;
import com.itech.learning.domain.dto.SubjectDto;
import com.itech.learning.service.RatingService;
import com.itech.learning.service.SubjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class SubjectController {
    private final SubjectService subjectService;
    private final RatingService ratingService;

    @GetMapping("/subjects")
    @PreAuthorize("hasAuthority('subject:read')")
    public ResponseEntity<List<SubjectDto>> findAll() {
        return ResponseEntity.ok(subjectService.getAll());
    }

    @GetMapping("/subjects/{subjectId}")
    @PreAuthorize("hasAuthority('subject:read')")
    public ResponseEntity<SubjectDto> findById(@PathVariable Long subjectId) {
        return ResponseEntity.ok(subjectService.findDtoById(subjectId));
    }

    @GetMapping("/subjects/{subjectId}/ratings")
    @PreAuthorize("hasAuthority('rating:read')")
    public ResponseEntity<List<RatingDto>> getSubjectRatings(@PathVariable Long subjectId) {
        return ResponseEntity.ok(subjectService.getSubjectRatings(subjectId));
    }

    @GetMapping("/subjects/{subjectId}/ratings/{ratingId}")
    @PreAuthorize("hasAuthority('rating:read')")
    public ResponseEntity<RatingDto> getSubjectRatings(@PathVariable Long subjectId, @PathVariable Long ratingId) {
        return ResponseEntity.ok(subjectService.getSubjectRatingById(subjectId, ratingId));
    }

    @PutMapping("/subjects/{subjectId}")
    @PreAuthorize("hasAuthority('subject:write')")
    public ResponseEntity<SubjectDto> update(@PathVariable Long subjectId, @RequestBody SubjectDto subjectDto) {
        return ResponseEntity.ok(subjectService.update(subjectId, subjectDto));
    }

    @PutMapping("/subjects/{subjectId}/ratings/{ratingId}")
    @PreAuthorize("hasAuthority('rating:write')")
    public ResponseEntity<RatingDto> updateRating(@PathVariable Long ratingId, @RequestBody RatingDto ratingDto) {
        return ResponseEntity.ok(ratingService.update(ratingId, ratingDto));
    }

    @PostMapping("/subjects")
    @PreAuthorize("hasAuthority('subject:write')")
    ResponseEntity<SubjectDto> create(@RequestBody SubjectDto subjectDto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(subjectService.create(subjectDto));
    }

    @PostMapping("/subjects/{subjectId}/ratings")
    @PreAuthorize("hasAuthority('rating:write')")
    public ResponseEntity<RatingDto> createRating(@PathVariable Long subjectId, @RequestBody RatingDto ratingDto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(subjectService.addRating(subjectId, ratingDto));
    }

    @DeleteMapping("/subjects/{subjectId}")
    @PreAuthorize("hasAuthority('subject:write')")
    public ResponseEntity<?> delete(@PathVariable Long subjectId) {
        subjectService.deleteById(subjectId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(String.format("Subject[%d] was deleted", subjectId));
    }

    @DeleteMapping("/subjects/{subjectId}/ratings/{ratingId}")
    @PreAuthorize("hasAuthority('rating:write')")
    public ResponseEntity<?> deleteRating(@PathVariable Long ratingId) {
        ratingService.deleteById(ratingId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(String.format("Rating[%d] was deleted", ratingId));
    }

}
