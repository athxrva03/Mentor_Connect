package com.mentorconnect.repository;

import com.mentorconnect.entity.MentorMenteeMapping;
import com.mentorconnect.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.List;

public interface MappingRepository extends JpaRepository<MentorMenteeMapping, Long> {
    Optional<MentorMenteeMapping> findByMentee(User mentee);
    List<MentorMenteeMapping> findByMentor(User mentor);
}
