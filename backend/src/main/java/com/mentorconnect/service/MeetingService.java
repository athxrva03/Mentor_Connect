package com.mentorconnect.service;

import com.mentorconnect.entity.Meeting;
import com.mentorconnect.entity.MeetingStatus;
import com.mentorconnect.entity.User;
import com.mentorconnect.repository.MeetingRepository;
import com.mentorconnect.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MeetingService {

    @Autowired
    private MeetingRepository meetingRepository;

    @Autowired
    private UserRepository userRepository;

    public Meeting requestMeeting(Long menteeId, Long mentorId, String topic, String date) {
        User mentee = userRepository.findById(menteeId).orElseThrow(() -> new RuntimeException("Mentee not found"));
        User mentor = userRepository.findById(mentorId).orElseThrow(() -> new RuntimeException("Mentor not found"));

        Meeting meeting = Meeting.builder()
                .mentee(mentee)
                .mentor(mentor)
                .topic(topic)
                .meetingDate(java.time.LocalDateTime.parse(date))
                .status(MeetingStatus.PENDING)
                .build();

        return meetingRepository.save(meeting);
    }

    public List<Meeting> getMyMeetings(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        return meetingRepository.findByMentee(user);
    }

    public List<Meeting> getAllMeetingsForMentor(Long mentorId) {
        User mentor = userRepository.findById(mentorId).orElseThrow(() -> new RuntimeException("Mentor not found"));
        return meetingRepository.findByMentor(mentor);
    }

    public Meeting approveMeeting(Long meetingId) {
        Meeting meeting = meetingRepository.findById(meetingId).orElseThrow(() -> new RuntimeException("Meeting not found"));
        meeting.setStatus(MeetingStatus.APPROVED);
        return meetingRepository.save(meeting);
    }

    public Meeting rejectMeeting(Long meetingId) {
        Meeting meeting = meetingRepository.findById(meetingId).orElseThrow(() -> new RuntimeException("Meeting not found"));
        meeting.setStatus(MeetingStatus.REJECTED);
        return meetingRepository.save(meeting);
    }

    public Meeting updateSummary(Long meetingId, String summary, String tasksAssigned) {
        Meeting meeting = meetingRepository.findById(meetingId).orElseThrow(() -> new RuntimeException("Meeting not found"));
        meeting.setSummary(summary);
        meeting.setTasksAssigned(tasksAssigned);
        return meetingRepository.save(meeting);
    }

    public long getPendingCountForMentor(Long mentorId) {
        User mentor = userRepository.findById(mentorId).orElseThrow(() -> new RuntimeException("Mentor not found"));
        return meetingRepository.countByMentorAndStatus(mentor, MeetingStatus.PENDING);
    }
}
