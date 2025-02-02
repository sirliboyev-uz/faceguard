package com.example.faceguard.service;

import com.example.faceguard.dto.JwtResponse;
import com.example.faceguard.dto.LoginDto;
import com.example.faceguard.dto.ReqRes;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public interface AuthService {
//    JwtResponse login(String username, String password);
    ReqRes getMyInfo(String username);
    ReqRes refreshToken(ReqRes refreshToken);
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
    JwtResponse loginUser(LoginDto loginDTO);
}
