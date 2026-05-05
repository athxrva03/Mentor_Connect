package com.mentorconnect.repository;

import com.mentorconnect.entity.Meeting;
import com.mentorconnect.entity.User;
import com.mentorconnect.entity.MeetingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MeetingRepository extends JpaRepository<Meeting, Long> {
    List<Meeting> findByMentee(User mentee);
    List<Meeting> findByMentor(User mentor);
    long countByMentorAndStatus(User mentor, MeetingStatus status);
}
