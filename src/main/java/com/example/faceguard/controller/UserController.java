package com.example.faceguard.controller;

import com.example.faceguard.dto.ApiResponse;
import com.example.faceguard.dto.RegisterDto;
import com.example.faceguard.model.utils.Annotation.RoleCheckName;
import com.example.faceguard.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
@CrossOrigin
public class UserController {

    @Autowired
    private final UserService userService;

    @RoleCheckName("READ_USER")
    @PostMapping(value = "/register", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public HttpEntity<?> register(
            @RequestParam("firstName") String firstName,
            @RequestParam("lastName") String lastName,
            @RequestParam("middleName") String middleName,
            @RequestParam("email") String email,
            @RequestParam("phoneNumber") String phoneNumber,
            @RequestParam("birthDate") String birthDate,
            @RequestParam("gender") String gender,
            @RequestParam("jobTitle") String jobTitle,
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            @RequestParam("repeatPassword") String repeatPassword,
            @RequestParam("roleId") Long roleId,
            @RequestParam("companyId") Long companyId,
            @RequestParam("image") MultipartFile image) {

        RegisterDto registerDTO = new RegisterDto(firstName, lastName, middleName, email, phoneNumber, birthDate,
                gender, jobTitle, username, password, repeatPassword, roleId, companyId, image);

        ApiResponse apiResponse = userService.registerUser(registerDTO);
        return ResponseEntity.status(apiResponse.getStatusCode()).body(apiResponse.getMessage());
    }
}
