package com.mentorconnect.dto;

import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuizResultDto {
    private Long id;
    private Long quizId;
    private String quizTitle;
    private Long menteeId;
    private String menteeName;
    private Integer score;
    private Integer totalMarks;
    private LocalDateTime takenAt;
}
