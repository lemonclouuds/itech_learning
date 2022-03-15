package com.itech.learning.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Table(
        name = "ratings"
)
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "rate", nullable = false)
    private Double rate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    @JsonBackReference("user")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "lesson_id")
    @JsonBackReference("lesson")
    private Lesson lesson;

    public Rating(Double rate, User user, Lesson lesson) {
        this.rate = rate;
        this.user = user;
        this.lesson = lesson;
    }
}
