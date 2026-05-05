package com.mentorconnect.controller;

import com.mentorconnect.dto.JwtResponse;
import com.mentorconnect.dto.LoginRequest;
import com.mentorconnect.dto.SignupRequest;
import com.mentorconnect.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.HashMap;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class AuthController {
    @Autowired
    AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        try {
            String message = authService.registerUser(signUpRequest);
            Map<String, String> response = new HashMap<>();
            response.put("message", message);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            Map<String, String> response = new HashMap<>();
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            return ResponseEntity.ok(authService.authenticateUser(loginRequest));
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("error", "Invalid email or password");
            return ResponseEntity.badRequest().body(response);
        }
    }
}
