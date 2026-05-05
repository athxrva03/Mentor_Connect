package com.mentorconnect.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "mentee_profiles")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MenteeProfile {
    @Id
    private Long userId;

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;

    private String interests;
    private String goals;
    private String education;
}
