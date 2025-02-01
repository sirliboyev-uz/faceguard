package com.example.faceguard.service;

import com.example.faceguard.dto.*;
import com.example.faceguard.model.Users;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.Optional;

public interface UserService {
    ApiResponse registerUser(RegisterDto registerDTO);
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
    JwtResponse loginUser(LoginDto loginDTO);
    Optional<Users> getUser(String email);
    List<Users> getUsers();

    ReqRes getMyInfo(String username);

    ReqRes refreshToken(ReqRes req);
}
