package com.mentorconnect.service;

import com.mentorconnect.dto.JwtResponse;
import com.mentorconnect.dto.LoginRequest;
import com.mentorconnect.dto.SignupRequest;
import com.mentorconnect.entity.MentorProfile;
import com.mentorconnect.entity.Role;
import com.mentorconnect.entity.User;
import com.mentorconnect.repository.MentorRepository;
import com.mentorconnect.repository.MenteeProfileRepository;
import com.mentorconnect.entity.MenteeProfile;
import com.mentorconnect.repository.UserRepository;
import com.mentorconnect.security.JwtUtils;
import com.mentorconnect.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    MentorRepository mentorRepository;

    @Autowired
    MenteeProfileRepository menteeRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @Transactional
    public String registerUser(SignupRequest signUpRequest) {
        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new RuntimeException("Error: Email is already in use!");
        }

        User user = User.builder()
                .name(signUpRequest.getName())
                .email(signUpRequest.getEmail())
                .password(encoder.encode(signUpRequest.getPassword()))
                .role(signUpRequest.getRole())
                .collegeId(signUpRequest.getCollegeId())
                .build();

        userRepository.save(user);

        if (user.getRole() == Role.MENTOR) {
            MentorProfile profile = MentorProfile.builder()
                    .user(user)
                    .build();
            mentorRepository.save(profile);
        } else if (user.getRole() == Role.MENTEE) {
            MenteeProfile profile = MenteeProfile.builder()
                    .user(user)
                    .build();
            menteeRepository.save(profile);
        }

        return "User registered successfully!";
    }

    public JwtResponse authenticateUser(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        Role role = Role.valueOf(userDetails.getAuthorities().iterator().next().getAuthority());

        return new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getName(),
                userDetails.getEmail(),
                role);
    }
}
