package com.example.faceguard.dto;

import com.example.faceguard.model.Branch;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
@AllArgsConstructor
@NoArgsConstructor
@Data

public class EmployeeDto {
    private String firstName;
    private String lastName;
    private String middleName;
    private String email;
    private String phone;
    private Date birthDate;
    private String gender;
    private String jobTitle;
    private Branch branchId;
    private MultipartFile image;
}
