package com.example.faceguard.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class JwtResponse {
    private String token;
    private String type = "Bearer ";
    private Long id;
    private String email;
    private String roles;

    public JwtResponse(String token, Long id, String email, String roles) {
        this.token = token;
        this.id = id;
        this.email = email;
        this.roles = roles;
    }
}