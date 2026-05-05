package com.mentorconnect.controller;

import com.mentorconnect.dto.MenteeProfileDto;
import com.mentorconnect.service.MenteeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/mentees")
public class MenteeController {
    @Autowired
    MenteeService menteeService;

    @GetMapping("/profile/{userId}")
    public ResponseEntity<MenteeProfileDto> getProfile(@PathVariable Long userId) {
        return ResponseEntity.ok(menteeService.getProfile(userId));
    }

    @PutMapping("/profile/{userId}")
    public ResponseEntity<MenteeProfileDto> updateProfile(@PathVariable Long userId, @RequestBody MenteeProfileDto dto) {
        return ResponseEntity.ok(menteeService.updateProfile(userId, dto));
    }
}
