package com.itech.learning.domain;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(
        name = "lessons"
)
public class Lesson {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "solution", nullable = false, columnDefinition = "TEXT")
    private String solution;

    @ManyToOne
    @JoinColumn(name = "subject_id")
    @EqualsAndHashCode.Exclude
    @JsonBackReference("subject")
    private Subject subject;

    @OneToMany(mappedBy = "lesson")
    @JsonManagedReference("lesson")
    private List<Rating> rates;

    public Lesson(String title, String solution) {
        this.title = title;
        this.solution = solution;
    }

    public Lesson(String title, String solution, Subject subject) {
        this.title = title;
        this.solution = solution;
        this.subject = subject;
    }
}
