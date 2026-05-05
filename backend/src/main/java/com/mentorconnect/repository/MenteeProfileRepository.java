package com.mentorconnect.repository;

import com.mentorconnect.entity.MenteeProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenteeProfileRepository extends JpaRepository<MenteeProfile, Long> {
}
