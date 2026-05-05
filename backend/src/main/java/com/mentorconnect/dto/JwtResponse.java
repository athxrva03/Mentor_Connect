package com.mentorconnect.dto;

import com.mentorconnect.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JwtResponse {
    private String token;
    private Long id;
    private String name;
    private String email;
    private Role role;
}
