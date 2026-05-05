package com.mentorconnect.controller;

import com.mentorconnect.entity.Meeting;
import com.mentorconnect.service.MeetingService;
import com.mentorconnect.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/meetings")
public class MeetingController {

    @Autowired
    private MeetingService meetingService;

    @PostMapping("/request")
    public ResponseEntity<?> requestMeeting(@RequestBody Map<String, String> request) {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long mentorId = Long.parseLong(request.get("mentorId"));
        String topic = request.get("topic");
        String date = request.get("date");
        
        return ResponseEntity.ok(meetingService.requestMeeting(userDetails.getId(), mentorId, topic, date));
    }

    @GetMapping("/my")
    public ResponseEntity<?> getMyMeetings() {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(meetingService.getMyMeetings(userDetails.getId()));
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllMeetings() {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(meetingService.getAllMeetingsForMentor(userDetails.getId()));
    }

    @PutMapping("/{id}/approve")
    public ResponseEntity<?> approveMeeting(@PathVariable Long id) {
        return ResponseEntity.ok(meetingService.approveMeeting(id));
    }

    @PutMapping("/{id}/reject")
    public ResponseEntity<?> rejectMeeting(@PathVariable Long id) {
        return ResponseEntity.ok(meetingService.rejectMeeting(id));
    }

    @PutMapping("/{id}/summary")
    public ResponseEntity<?> updateSummary(@PathVariable Long id, @RequestBody Map<String, String> request) {
        String summary = request.get("summary");
        String tasks = request.get("tasks");
        return ResponseEntity.ok(meetingService.updateSummary(id, summary, tasks));
    }
}
