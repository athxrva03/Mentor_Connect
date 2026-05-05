package com.mentorconnect.controller;

import com.mentorconnect.entity.MentorMenteeMapping;
import com.mentorconnect.entity.Role;
import com.mentorconnect.entity.User;
import com.mentorconnect.repository.MappingRepository;
import com.mentorconnect.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/admin")
public class AdminController {
    @Autowired
    UserRepository userRepository;

    @Autowired
    MappingRepository mappingRepository;

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userRepository.findAll());
    }

    @PostMapping("/assign")
    public ResponseEntity<?> assignMentor(@RequestParam Long mentorId, @RequestParam Long menteeId) {
        User mentor = userRepository.findById(mentorId).orElseThrow();
        User mentee = userRepository.findById(menteeId).orElseThrow();
        
        if (mentor.getRole() != Role.MENTOR || mentee.getRole() != Role.MENTEE) {
            return ResponseEntity.badRequest().body("Invalid roles for mapping");
        }

        MentorMenteeMapping mapping = mappingRepository.findByMentee(mentee)
                .orElse(new MentorMenteeMapping());
        
        mapping.setMentor(mentor);
        mapping.setMentee(mentee);
        
        return ResponseEntity.ok(mappingRepository.save(mapping));
    }

    @GetMapping("/mappings")
    public ResponseEntity<List<MentorMenteeMapping>> getAllMappings() {
        return ResponseEntity.ok(mappingRepository.findAll());
    }
    
    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        userRepository.deleteById(id);
        return ResponseEntity.ok("User deleted");
    }
}
