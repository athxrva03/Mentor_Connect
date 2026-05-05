package com.mentorconnect.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MentorProfileDto {
    private Long userId;
    private String name;
    private String email;
    private String skills;
    private String experience;
    private String bio;
    private String profilePicture;
}
