package com.example.faceguard.dto;

import com.example.faceguard.model.Company;
import com.example.faceguard.model.Image;
import com.example.faceguard.model.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RegisterDto {
    private String firstName;
    private String lastName;
    private String middleName;
    private String email;
    private String phoneNumber;
    private Date birthDate;
    private String gender;
    private String jobTitle;
    private String username;
    private String password;
    private String repeatPassword;
    private Role roleId;
    private MultipartFile image;
    private Company companyId;
}