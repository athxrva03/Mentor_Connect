package com.mentorconnect.service;

import com.mentorconnect.dto.MenteeProfileDto;
import com.mentorconnect.entity.MenteeProfile;
import com.mentorconnect.entity.User;
import com.mentorconnect.repository.MenteeProfileRepository;
import com.mentorconnect.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MenteeService {
    @Autowired
    MenteeProfileRepository menteeProfileRepository;

    @Autowired
    UserRepository userRepository;

    public MenteeProfileDto getProfile(Long userId) {
        MenteeProfile profile = menteeProfileRepository.findById(userId)
                .orElseGet(() -> createDefaultProfile(userId));
        return mapToDto(profile);
    }

    public MenteeProfileDto updateProfile(Long userId, MenteeProfileDto dto) {
        MenteeProfile profile = menteeProfileRepository.findById(userId)
                .orElseGet(() -> createDefaultProfile(userId));
        
        profile.setInterests(dto.getInterests());
        profile.setGoals(dto.getGoals());
        profile.setEducation(dto.getEducation());
        
        return mapToDto(menteeProfileRepository.save(profile));
    }

    private MenteeProfile createDefaultProfile(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        MenteeProfile profile = MenteeProfile.builder()
                .user(user)
                .build();
        return menteeProfileRepository.save(profile);
    }

    private MenteeProfileDto mapToDto(MenteeProfile profile) {
        return MenteeProfileDto.builder()
                .userId(profile.getUserId())
                .name(profile.getUser().getName())
                .email(profile.getUser().getEmail())
                .interests(profile.getInterests())
                .goals(profile.getGoals())
                .education(profile.getEducation())
                .build();
    }
}
