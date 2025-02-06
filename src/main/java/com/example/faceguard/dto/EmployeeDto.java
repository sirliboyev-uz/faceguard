package com.example.faceguard.dto;

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
    private String phoneNumber;
    private String birthDate;
    private String gender;
    private String jobTitle;
    private String schedule;
    private double salary;

    // Associate employee with a company via companyId
    private Long companyId;

    // For file upload (image)
    private MultipartFile image;
}
