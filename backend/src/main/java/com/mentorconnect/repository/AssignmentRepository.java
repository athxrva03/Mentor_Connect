package com.mentorconnect.repository;

import com.mentorconnect.entity.Assignment;
import com.mentorconnect.entity.User;
import com.mentorconnect.entity.AssignmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssignmentRepository extends JpaRepository<Assignment, Long> {
    List<Assignment> findByUser(User user);
    List<Assignment> findByUserId(Long userId);
    long countByStatus(AssignmentStatus status);
}
