package com.mentorconnect.dto;

import com.mentorconnect.entity.AssignmentStatus;
import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AssignmentDto {
    private Long id;
    private Long userId;
    private String userName;
    private String title;
    private String filePath;
    private LocalDateTime submissionDate;
    private AssignmentStatus status;
}
