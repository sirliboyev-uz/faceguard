package com.example.faceguard.service;


import com.example.faceguard.dto.ApiResponse;
import com.example.faceguard.dto.DepartmentDto;
import com.example.faceguard.model.Department;
import org.springframework.stereotype.Service;

@Service
public interface DepartmentService {
    ApiResponse createDepartment(DepartmentDto departmentDto);
}
