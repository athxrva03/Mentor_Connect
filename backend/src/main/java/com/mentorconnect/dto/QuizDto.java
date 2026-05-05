package com.mentorconnect.dto;

import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuizDto {
    private Long id;
    private Long mentorId;
    private String mentorName;
    private String title;
    private String description;
    private Integer totalMarks;
    private LocalDateTime createdAt;
}
