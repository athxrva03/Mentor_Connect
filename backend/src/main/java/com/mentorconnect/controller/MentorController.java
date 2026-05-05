package com.mentorconnect.controller;

import com.mentorconnect.dto.MentorProfileDto;
import com.mentorconnect.service.MentorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/mentors")
public class MentorController {
    @Autowired
    MentorService mentorService;

    @GetMapping("/search")
    public ResponseEntity<List<MentorProfileDto>> searchMentors(@RequestParam(required = false) String query) {
        return ResponseEntity.ok(mentorService.searchMentors(query));
    }

    @GetMapping("/profile/{userId}")
    public ResponseEntity<MentorProfileDto> getProfile(@PathVariable Long userId) {
        return ResponseEntity.ok(mentorService.getProfile(userId));
    }
    @PutMapping("/profile/{userId}")
    public ResponseEntity<MentorProfileDto> updateProfile(@PathVariable Long userId, @RequestBody MentorProfileDto dto) {
        return ResponseEntity.ok(mentorService.updateProfile(userId, dto));
    }
}
