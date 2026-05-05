package com.mentorconnect.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "assignments")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Assignment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnoreProperties({"password", "hibernateLazyInitializer", "handler"})
    private User user; // The mentee who submitted

    private String title;
    private String filePath;
    private LocalDateTime submissionDate;

    @Enumerated(EnumType.STRING)
    private AssignmentStatus status;

    @PrePersist
    protected void onCreate() {
        submissionDate = LocalDateTime.now();
        if (status == null) status = AssignmentStatus.SUBMITTED;
    }
}
