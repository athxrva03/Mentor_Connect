package com.mentorconnect.repository;

import com.mentorconnect.entity.Request;
import com.mentorconnect.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface RequestRepository extends JpaRepository<Request, Long> {
    List<Request> findByMentor(User mentor);
    List<Request> findByMentee(User mentee);
}
