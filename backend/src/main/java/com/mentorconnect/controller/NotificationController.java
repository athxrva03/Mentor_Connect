package com.mentorconnect.controller;

import com.mentorconnect.service.AssignmentService;
import com.mentorconnect.service.MeetingService;
import com.mentorconnect.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    @Autowired
    private MeetingService meetingService;

    @Autowired
    private AssignmentService assignmentService;

    @GetMapping("/stats")
    public ResponseEntity<?> getStats() {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Map<String, Long> stats = new HashMap<>();
        
        // For Mentors, show pending meetings and submitted assignments
        stats.put("pendingMeetings", meetingService.getPendingCountForMentor(userDetails.getId()));
        stats.put("newAssignments", assignmentService.getSubmittedCount());
        
        return ResponseEntity.ok(stats);
    }
}
