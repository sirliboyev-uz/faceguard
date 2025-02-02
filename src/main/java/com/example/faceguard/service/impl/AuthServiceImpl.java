package com.example.faceguard.service.impl;


import com.example.faceguard.dto.JwtResponse;
import com.example.faceguard.dto.LoginDto;
import com.example.faceguard.dto.ReqRes;
import com.example.faceguard.model.Users;
import com.example.faceguard.repository.UserRepository;
import com.example.faceguard.service.AuthService;
import com.example.faceguard.web.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    Token token;

    @Autowired
    UserRepository userRepository;

    @Override
    public ReqRes getMyInfo(String username) {
        ReqRes reqRes = new ReqRes();
        try {
            Optional<Users> userOptional = userRepository.findByUsername(username);
            if (userOptional.isPresent()) {
                reqRes.setUsers(userOptional.get());
                reqRes.setStatusCode(200);
                reqRes.setMessage("successful");
            } else {
                reqRes.setStatusCode(404);
                reqRes.setMessage("User not found for update");
            }

        }catch (Exception e){
            reqRes.setStatusCode(500);
            reqRes.setMessage("Error occurred while getting user info: " + e.getMessage());
        }
        return reqRes;

    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(()->new UsernameNotFoundException(username+" not found username"));
    }

    @Override
    public JwtResponse loginUser(LoginDto loginDTO) {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword()));
        if (!authenticate.isAuthenticated()) return null;
        Users users= (Users) authenticate.getPrincipal();
        String token1=token.generateToken(users.getUsername(), users.getRole());
        System.out.println(token1);
        return new JwtResponse(token1, users.getId(), users.getEmail(), users.getRole().getRoleName());
    }
    @Override
    public ReqRes refreshToken(ReqRes refreshTokenRequest){
        ReqRes response = new ReqRes();
        try{
            String username = token.extractUsername(refreshTokenRequest.getToken());
            Users users = userRepository.findByUsername(username).orElseThrow();
            if (token.isTokenValid(refreshTokenRequest.getToken(), users)) {
                var jwt = token.generateToken(users.getUsername(), users.getRole());
                response.setStatusCode(200);
                response.setToken(jwt);
                response.setRefreshToken(refreshTokenRequest.getToken());
                response.setExpirationTime("24Hr");
                response.setMessage("Successfully Refreshed Token");
            }
            response.setStatusCode(200);
            return response;

        }catch (Exception e){
            response.setStatusCode(500);
            response.setMessage(e.getMessage());
            return response;
        }
    }
}
