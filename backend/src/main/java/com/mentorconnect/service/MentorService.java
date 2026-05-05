package com.mentorconnect.service;

import com.mentorconnect.dto.MentorProfileDto;
import com.mentorconnect.entity.MentorProfile;
import com.mentorconnect.repository.MentorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MentorService {
    @Autowired
    MentorRepository mentorRepository;

    public List<MentorProfileDto> searchMentors(String query) {
        List<MentorProfile> profiles = query == null || query.isEmpty() 
            ? mentorRepository.findAll() 
            : mentorRepository.searchMentors(query);
            
        return profiles.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    public MentorProfileDto getProfile(Long userId) {
        MentorProfile profile = mentorRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Profile not found"));
        return mapToDto(profile);
    }

    public MentorProfileDto updateProfile(Long userId, MentorProfileDto dto) {
        MentorProfile profile = mentorRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Profile not found"));
        
        profile.setSkills(dto.getSkills());
        profile.setExperience(dto.getExperience());
        profile.setBio(dto.getBio());
        profile.setProfilePicture(dto.getProfilePicture());
        
        return mapToDto(mentorRepository.save(profile));
    }

    private MentorProfileDto mapToDto(MentorProfile profile) {
        return MentorProfileDto.builder()
                .userId(profile.getUserId())
                .name(profile.getUser().getName())
                .email(profile.getUser().getEmail())
                .skills(profile.getSkills())
                .experience(profile.getExperience())
                .bio(profile.getBio())
                .profilePicture(profile.getProfilePicture())
                .build();
    }
}
