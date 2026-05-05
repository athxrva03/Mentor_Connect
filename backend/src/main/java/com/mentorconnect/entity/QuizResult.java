package com.mentorconnect.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "quiz_results")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuizResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "quiz_id", nullable = false)
    private Quiz quiz;

    @ManyToOne
    @JoinColumn(name = "mentee_id", nullable = false)
    private User mentee;

    private Integer score;
    private LocalDateTime takenAt;

    @PrePersist
    protected void onTake() {
        takenAt = LocalDateTime.now();
    }
}
