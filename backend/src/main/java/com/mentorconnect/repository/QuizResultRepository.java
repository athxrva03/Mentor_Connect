package com.mentorconnect.repository;

import com.mentorconnect.entity.Quiz;
import com.mentorconnect.entity.QuizResult;
import com.mentorconnect.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface QuizResultRepository extends JpaRepository<QuizResult, Long> {
    List<QuizResult> findByMentee(User mentee);
    List<QuizResult> findByQuiz(Quiz quiz);
}
