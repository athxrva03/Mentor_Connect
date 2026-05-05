package com.mentorconnect.dto;

import com.mentorconnect.entity.RequestStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RequestDto {
    private Long id;
    private Long mentorId;
    private String mentorName;
    private Long menteeId;
    private String menteeName;
    private RequestStatus status;
    private String message;
    private LocalDateTime createdAt;
}
