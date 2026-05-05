package com.mentorconnect.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MenteeProfileDto {
    private Long userId;
    private String name;
    private String email;
    private String interests;
    private String goals;
    private String education;
}
