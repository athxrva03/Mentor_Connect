package com.mentorconnect.controller;

import com.mentorconnect.entity.Assignment;
import com.mentorconnect.service.AssignmentService;
import com.mentorconnect.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;

@RestController
@RequestMapping("/api/assignments")
public class AssignmentController {

    @Autowired
    private AssignmentService assignmentService;

    @PostMapping("/upload")
    public ResponseEntity<?> uploadAssignment(@RequestParam("title") String title, @RequestParam("file") MultipartFile file) {
        try {
            UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            return ResponseEntity.ok(assignmentService.uploadAssignment(userDetails.getId(), title, file));
        } catch (Exception e) {
            java.util.Map<String, String> response = new java.util.HashMap<>();
            response.put("message", "Upload failed: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllAssignments() {
        return ResponseEntity.ok(assignmentService.getAllAssignments());
    }

    @GetMapping("/my")
    public ResponseEntity<?> getMyAssignments() {
        try {
            UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            return ResponseEntity.ok(assignmentService.getAssignmentsByUserId(userDetails.getId()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(java.util.Map.of("message", e.getMessage()));
        }
    }

    @PutMapping("/{id}/verify")
    public ResponseEntity<?> verifyAssignment(@PathVariable Long id) {
        return ResponseEntity.ok(assignmentService.verifyAssignment(id));
    }

    @GetMapping("/export/excel")
    public ResponseEntity<InputStreamResource> exportExcel() throws Exception {
        ByteArrayInputStream in = assignmentService.exportToExcel();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=assignments.xlsx");

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .body(new InputStreamResource(in));
    }

    @GetMapping("/export/pdf")
    public ResponseEntity<InputStreamResource> exportPdf() throws Exception {
        ByteArrayInputStream in = assignmentService.exportToPdf();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=assignments.pdf");

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(in));
    }
}
