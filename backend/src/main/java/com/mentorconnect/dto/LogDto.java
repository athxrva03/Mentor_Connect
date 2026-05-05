package com.mentorconnect.dto;

import lombok.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LogDto {
    private Long id;
    private Long mentorId;
    private String mentorName;
    private Long menteeId;
    private String menteeName;
    private String topic;
    private LocalDate date;
    private boolean verified;
}
