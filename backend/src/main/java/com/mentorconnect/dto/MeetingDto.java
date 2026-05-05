package com.mentorconnect.dto;

import com.mentorconnect.entity.MeetingStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MeetingDto {
    private Long id;
    private Long mentorId;
    private String mentorName;
    private Long menteeId;
    private String menteeName;
    private LocalDate meetingDate;
    private LocalTime meetingTime;
    private MeetingStatus status;
    private String link;
}
