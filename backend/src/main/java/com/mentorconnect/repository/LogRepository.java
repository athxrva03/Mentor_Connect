package com.mentorconnect.repository;

import com.mentorconnect.entity.Log;
import com.mentorconnect.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface LogRepository extends JpaRepository<Log, Long> {
    List<Log> findByMentee(User mentee);
    List<Log> findByMentor(User mentor);
}
