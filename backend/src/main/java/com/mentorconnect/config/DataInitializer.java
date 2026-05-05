package com.mentorconnect.config;

import com.mentorconnect.entity.Role;
import com.mentorconnect.entity.User;
import com.mentorconnect.entity.MentorProfile;
import com.mentorconnect.entity.MenteeProfile;
import com.mentorconnect.repository.UserRepository;
import com.mentorconnect.repository.MentorRepository;
import com.mentorconnect.repository.MenteeProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    UserRepository userRepository;

    @Autowired
    MentorRepository mentorRepository;

    @Autowired
    MenteeProfileRepository menteeRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        if (!userRepository.existsByEmail("admin@mentorbridge.com")) {
            User admin = User.builder()
                    .name("System Admin")
                    .email("admin@mentorbridge.com")
                    .password(passwordEncoder.encode("admin123"))
                    .role(Role.ADMIN)
                    .collegeId("ADMIN-001")
                    .build();
            userRepository.save(admin);
        }

        if (!userRepository.existsByEmail("mentor@mentorbridge.com")) {
            User mentor = User.builder()
                    .name("John Mentor")
                    .email("mentor@mentorbridge.com")
                    .password(passwordEncoder.encode("mentor123"))
                    .role(Role.MENTOR)
                    .collegeId("MENTOR-001")
                    .build();
            userRepository.save(mentor);
            mentorRepository.save(MentorProfile.builder().user(mentor).build());
        }

        if (!userRepository.existsByEmail("mentee@mentorbridge.com")) {
            User mentee = User.builder()
                    .name("Jane Mentee")
                    .email("mentee@mentorbridge.com")
                    .password(passwordEncoder.encode("mentee123"))
                    .role(Role.MENTEE)
                    .collegeId("MENTEE-001")
                    .build();
            userRepository.save(mentee);
            menteeRepository.save(MenteeProfile.builder().user(mentee).build());
        }
    }
}
