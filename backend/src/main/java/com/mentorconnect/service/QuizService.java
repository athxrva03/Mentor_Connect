package com.mentorconnect.service;

import com.mentorconnect.dto.QuizDto;
import com.mentorconnect.dto.QuizResultDto;
import com.mentorconnect.entity.Quiz;
import com.mentorconnect.entity.QuizResult;
import com.mentorconnect.entity.User;
import com.mentorconnect.repository.QuizRepository;
import com.mentorconnect.repository.QuizResultRepository;
import com.mentorconnect.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuizService {
    @Autowired
    QuizRepository quizRepository;

    @Autowired
    QuizResultRepository quizResultRepository;

    @Autowired
    UserRepository userRepository;

    public QuizDto createQuiz(QuizDto dto) {
        User mentor = userRepository.findById(dto.getMentorId())
                .orElseThrow(() -> new RuntimeException("Mentor not found"));

        Quiz quiz = Quiz.builder()
                .mentor(mentor)
                .title(dto.getTitle())
                .description(dto.getDescription())
                .totalMarks(dto.getTotalMarks())
                .build();

        return mapToDto(quizRepository.save(quiz));
    }

    public List<QuizDto> getAllQuizzes() {
        return quizRepository.findAll().stream().map(this::mapToDto).collect(Collectors.toList());
    }

    public QuizResultDto submitQuizResult(QuizResultDto dto) {
        Quiz quiz = quizRepository.findById(dto.getQuizId())
                .orElseThrow(() -> new RuntimeException("Quiz not found"));
        User mentee = userRepository.findById(dto.getMenteeId())
                .orElseThrow(() -> new RuntimeException("Mentee not found"));

        QuizResult result = QuizResult.builder()
                .quiz(quiz)
                .mentee(mentee)
                .score(dto.getScore())
                .build();

        return mapToResultDto(quizResultRepository.save(result));
    }

    public List<QuizResultDto> getResultsForUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        return quizResultRepository.findByMentee(user).stream()
                .map(this::mapToResultDto).collect(Collectors.toList());
    }

    private QuizDto mapToDto(Quiz quiz) {
        return QuizDto.builder()
                .id(quiz.getId())
                .mentorId(quiz.getMentor().getId())
                .mentorName(quiz.getMentor().getName())
                .title(quiz.getTitle())
                .description(quiz.getDescription())
                .totalMarks(quiz.getTotalMarks())
                .createdAt(quiz.getCreatedAt())
                .build();
    }

    private QuizResultDto mapToResultDto(QuizResult result) {
        return QuizResultDto.builder()
                .id(result.getId())
                .quizId(result.getQuiz().getId())
                .quizTitle(result.getQuiz().getTitle())
                .menteeId(result.getMentee().getId())
                .menteeName(result.getMentee().getName())
                .score(result.getScore())
                .totalMarks(result.getQuiz().getTotalMarks())
                .takenAt(result.getTakenAt())
                .build();
    }
}
