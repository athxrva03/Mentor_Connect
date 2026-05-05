package com.mentorconnect.repository;

import com.mentorconnect.entity.MentorProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface MentorRepository extends JpaRepository<MentorProfile, Long> {
    @Query("SELECT m FROM MentorProfile m WHERE m.skills LIKE %:query% OR m.user.name LIKE %:query%")
    List<MentorProfile> searchMentors(@Param("query") String query);
}
