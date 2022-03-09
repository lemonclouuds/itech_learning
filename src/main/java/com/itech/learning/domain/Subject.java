package com.itech.learning.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(
        name = "subjects"
)
public class Subject {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @OneToMany(mappedBy = "subject")
    @EqualsAndHashCode.Exclude
    @JsonManagedReference("subject")
    private List<Lesson> lessons;

    public Subject(String title) {
        this.title = title;
        this.lessons = new ArrayList<>();
    }
}
