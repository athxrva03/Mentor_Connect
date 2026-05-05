package com.mentorconnect.service;

import com.mentorconnect.dto.RequestDto;
import com.mentorconnect.entity.Request;
import com.mentorconnect.entity.RequestStatus;
import com.mentorconnect.entity.User;
import com.mentorconnect.repository.RequestRepository;
import com.mentorconnect.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RequestService {
    @Autowired
    RequestRepository requestRepository;

    @Autowired
    UserRepository userRepository;

    public RequestDto sendRequest(RequestDto dto) {
        User mentor = userRepository.findById(dto.getMentorId())
                .orElseThrow(() -> new RuntimeException("Mentor not found"));
        User mentee = userRepository.findById(dto.getMenteeId())
                .orElseThrow(() -> new RuntimeException("Mentee not found"));

        Request request = Request.builder()
                .mentor(mentor)
                .mentee(mentee)
                .message(dto.getMessage())
                .status(RequestStatus.PENDING)
                .build();

        return mapToDto(requestRepository.save(request));
    }

    public List<RequestDto> getRequestsForUser(Long userId, String role) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        List<Request> requests;
        if ("MENTOR".equals(role)) {
            requests = requestRepository.findByMentor(user);
        } else {
            requests = requestRepository.findByMentee(user);
        }
        
        return requests.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    public RequestDto updateRequestStatus(Long requestId, RequestStatus status) {
        Request request = requestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Request not found"));
        
        request.setStatus(status);
        return mapToDto(requestRepository.save(request));
    }

    private RequestDto mapToDto(Request request) {
        return RequestDto.builder()
                .id(request.getId())
                .mentorId(request.getMentor().getId())
                .mentorName(request.getMentor().getName())
                .menteeId(request.getMentee().getId())
                .menteeName(request.getMentee().getName())
                .status(request.getStatus())
                .message(request.getMessage())
                .createdAt(request.getCreatedAt())
                .build();
    }
}
