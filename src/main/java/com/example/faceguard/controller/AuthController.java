package com.example.faceguard.controller;

import com.example.faceguard.dto.*;
import com.example.faceguard.model.utils.Annotation.RoleCheckName;
import com.example.faceguard.service.AuthService;
import com.example.faceguard.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@CrossOrigin("*")
public class AuthController {
    private final UserService userService;
    private final AuthService authService;


    @PostMapping("/login")
    public HttpEntity<?> login(@RequestBody LoginDto loginDTO){
        JwtResponse jwtResponse = authService.loginUser(loginDTO);
        return ResponseEntity.ok(jwtResponse);
    }

    @RoleCheckName("READ_USER")
    @GetMapping("/get-profile")
    public ResponseEntity<ReqRes> getMyProfile(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        ReqRes response = authService.getMyInfo(username);
        return  ResponseEntity.status(response.getStatusCode()).body(response);
    }
    @PostMapping("/refresh")
    public ResponseEntity<ReqRes> refreshToken(@RequestBody ReqRes req){
        return ResponseEntity.ok(authService.refreshToken(req));
    }





//    @GetMapping(value = "/all")
//    public ResponseEntity<?> home(){
//        return ResponseEntity.ok().body("Hi, this is public content");
//    }
//
//
//    @GetMapping("/user")
//    @RoleCheckName("READ_USER")
//    public String userAccess() {
//        return "User Content.";
//    }
//
//
//    @GetMapping("/mod")
//    @RoleCheckName(value = "ADD_ROLE")
//    public String moderatorAccess() {
//        return "Moderator Board.";
//    }
//
//    @GetMapping("/admin")
//    @RoleCheckName(value = "ADD_ROLE")
//    public String adminAccess() {
//        return "Admin Board.";
//    }
}
