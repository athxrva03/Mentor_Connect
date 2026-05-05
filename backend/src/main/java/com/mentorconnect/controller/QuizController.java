package com.mentorconnect.controller;

import com.mentorconnect.dto.QuizDto;
import com.mentorconnect.dto.QuizResultDto;
import com.mentorconnect.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/quizzes")
public class QuizController {
    @Autowired
    QuizService quizService;

    @PostMapping("/create")
    public ResponseEntity<QuizDto> createQuiz(@RequestBody QuizDto dto) {
        return ResponseEntity.ok(quizService.createQuiz(dto));
    }

    @GetMapping("/all")
    public ResponseEntity<List<QuizDto>> getAllQuizzes() {
        return ResponseEntity.ok(quizService.getAllQuizzes());
    }

    @PostMapping("/submit")
    public ResponseEntity<QuizResultDto> submitResult(@RequestBody QuizResultDto dto) {
        return ResponseEntity.ok(quizService.submitQuizResult(dto));
    }

    @GetMapping("/user/{userId}/results")
    public ResponseEntity<List<QuizResultDto>> getResults(@PathVariable Long userId) {
        return ResponseEntity.ok(quizService.getResultsForUser(userId));
    }
}
