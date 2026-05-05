package com.mentorconnect.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "mentor_mentee_mapping")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MentorMenteeMapping {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "mentor_id", nullable = false)
    private User mentor;

    @OneToOne
    @JoinColumn(name = "mentee_id", nullable = false)
    private User mentee;

    private LocalDateTime assignedAt;

    @PrePersist
    protected void onAssign() {
        assignedAt = LocalDateTime.now();
    }
}
