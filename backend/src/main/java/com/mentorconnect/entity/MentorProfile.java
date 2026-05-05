package com.mentorconnect.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "mentor_profiles")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MentorProfile {
    @Id
    private Long userId;

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;

    private String skills;
    private String experience;

    @Column(columnDefinition = "TEXT")
    private String bio;

    private String profilePicture;
}
