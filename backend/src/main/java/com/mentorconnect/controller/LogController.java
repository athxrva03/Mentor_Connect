package com.mentorconnect.controller;

import com.mentorconnect.dto.LogDto;
import com.mentorconnect.entity.Log;
import com.mentorconnect.entity.User;
import com.mentorconnect.repository.LogRepository;
import com.mentorconnect.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/logs")
public class LogController {
    @Autowired
    LogRepository logRepository;

    @Autowired
    UserRepository userRepository;

    @PostMapping("/add")
    public ResponseEntity<LogDto> addLog(@RequestBody LogDto dto) {
        User mentee = userRepository.findById(dto.getMenteeId()).orElseThrow();
        User mentor = userRepository.findById(dto.getMentorId()).orElseThrow();
        
        Log log = Log.builder()
                .mentee(mentee)
                .mentor(mentor)
                .topic(dto.getTopic())
                .date(dto.getDate())
                .verified(false)
                .build();
        
        return ResponseEntity.ok(mapToDto(logRepository.save(log)));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<LogDto>> getLogs(@PathVariable Long userId, @RequestParam String role) {
        User user = userRepository.findById(userId).orElseThrow();
        List<Log> logs = role.equals("MENTOR") ? logRepository.findByMentor(user) : logRepository.findByMentee(user);
        return ResponseEntity.ok(logs.stream().map(this::mapToDto).collect(Collectors.toList()));
    }

    @PutMapping("/{id}/verify")
    public ResponseEntity<LogDto> verifyLog(@PathVariable Long id) {
        Log log = logRepository.findById(id).orElseThrow();
        log.setVerified(true);
        return ResponseEntity.ok(mapToDto(logRepository.save(log)));
    }

    private LogDto mapToDto(Log log) {
        return LogDto.builder()
                .id(log.getId())
                .menteeId(log.getMentee().getId())
                .menteeName(log.getMentee().getName())
                .mentorId(log.getMentor().getId())
                .mentorName(log.getMentor().getName())
                .topic(log.getTopic())
                .date(log.getDate())
                .verified(log.isVerified())
                .build();
    }
}
