package com.mentorconnect;

import com.mentorconnect.entity.Role;
import com.mentorconnect.entity.User;
import com.mentorconnect.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class MentorConnectApplication {

	public static void main(String[] args) {
		SpringApplication.run(MentorConnectApplication.class, args);
	}

	@Bean
	CommandLineRunner initAdmin(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		return args -> {
			if (!userRepository.existsByEmail("admin@gmail.com")) {
				User admin = User.builder()
						.name("System Admin")
						.email("admin@gmail.com")
						.password(passwordEncoder.encode("admin123"))
						.role(Role.ADMIN)
						.collegeId("ADMIN-001")
						.build();
				userRepository.save(admin);
				System.out.println("Default Admin created: admin@gmail.com / admin123");
			}
		};
	}
}
