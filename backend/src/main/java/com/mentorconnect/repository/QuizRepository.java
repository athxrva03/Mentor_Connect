package com.mentorconnect.repository;

import com.mentorconnect.entity.Quiz;
import com.mentorconnect.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface QuizRepository extends JpaRepository<Quiz, Long> {
    List<Quiz> findByMentor(User mentor);
}
