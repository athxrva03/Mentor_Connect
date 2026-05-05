package com.mentorconnect.controller;

import com.mentorconnect.dto.RequestDto;
import com.mentorconnect.entity.RequestStatus;
import com.mentorconnect.service.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/requests")
public class RequestController {
    @Autowired
    RequestService requestService;

    @PostMapping("/send")
    public ResponseEntity<RequestDto> sendRequest(@RequestBody RequestDto dto) {
        return ResponseEntity.ok(requestService.sendRequest(dto));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<RequestDto>> getRequests(@PathVariable Long userId, @RequestParam String role) {
        return ResponseEntity.ok(requestService.getRequestsForUser(userId, role));
    }

    @PutMapping("/{requestId}/status")
    public ResponseEntity<RequestDto> updateStatus(@PathVariable Long requestId, @RequestParam RequestStatus status) {
        return ResponseEntity.ok(requestService.updateRequestStatus(requestId, status));
    }
}
