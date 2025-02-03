package com.example.faceguard.service;

import com.example.faceguard.dto.*;
import com.example.faceguard.model.Users;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public interface UserService {
    ApiResponse registerUser(RegisterDto registerDTO);
    Optional<Users> getUser(String email);
    List<Users> getUsers();
}
