package com.example.faceguard.dto;

import com.example.faceguard.model.Company;
import com.example.faceguard.model.Image;
import com.example.faceguard.model.Role;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class RegisterDto {
    private String firstName;
    private String lastName;
    private String middleName;
    private String email;
    private String phoneNumber;
    private String birthDate;
    private String gender;
    private String jobTitle;
    private String username;
    private String password;
    private String repeatPassword;
    private Long roleId;
    private Long companyId;
    private MultipartFile image;
}