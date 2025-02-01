package com.example.faceguard.dto;

import com.example.faceguard.model.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ApiResponse {
    private String message;
    private Boolean type;
    private Object object;
    private int statusCode;

    public ApiResponse(String message, Boolean type) {
        this.message = message;
        this.type = type;
    }
    public ApiResponse(String message, Boolean type, int statusCode) {
        this.message = message;
        this.type = type;
        this.statusCode = statusCode;
    }
}
