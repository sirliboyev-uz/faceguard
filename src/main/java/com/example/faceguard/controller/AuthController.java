package com.example.faceguard.controller;

import com.example.faceguard.dto.ApiResponse;
import com.example.faceguard.dto.JwtResponse;
import com.example.faceguard.dto.LoginDto;
import com.example.faceguard.dto.RegisterDto;
import com.example.faceguard.model.utils.Annotation.RoleCheckName;
import com.example.faceguard.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@CrossOrigin
public class AuthController {
    private final UserService userService;

    @PostMapping("/register")
    public HttpEntity<?> register(@RequestBody RegisterDto registerDTO){
        ApiResponse apiResponse = userService.registerUser(registerDTO);
        return ResponseEntity.status(apiResponse.getType()?200:409).body(apiResponse.getMessage());
    }
    @PostMapping("/login")
    public HttpEntity<?> login(@RequestBody LoginDto loginDTO){
        JwtResponse jwtResponse = userService.loginUser(loginDTO);
        return ResponseEntity.ok(jwtResponse);
    }
    @GetMapping(value = "/all")
    public ResponseEntity<?> home(){
        return ResponseEntity.ok().body("Hi, this is public content");
    }


    @GetMapping("/user")
    @RoleCheckName("READ_USER")
    public String userAccess() {
        return "User Content.";
    }

    @GetMapping("/mod")
    @RoleCheckName(value = "ADD_ROLE")
    public String moderatorAccess() {
        return "Moderator Board.";
    }

    @GetMapping("/admin")
    @RoleCheckName(value = "ADD_ROLE")
    public String adminAccess() {
        return "Admin Board.";
    }
}
