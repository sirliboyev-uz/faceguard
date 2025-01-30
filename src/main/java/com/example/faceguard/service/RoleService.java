package com.example.faceguard.service;

import com.example.faceguard.dto.ApiResponse;
import com.example.faceguard.dto.RoleRegisterDto;
import org.springframework.stereotype.Service;

@Service
public interface RoleService {
    ApiResponse register(RoleRegisterDto roleRegisterDTO);

    ApiResponse update(Long id, RoleRegisterDto dto);

    ApiResponse delete(Long id);
    ApiResponse role(Long id);
    ApiResponse roles();
}
