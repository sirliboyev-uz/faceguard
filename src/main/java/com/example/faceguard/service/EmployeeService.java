package com.example.faceguard.service;


import com.example.faceguard.dto.ApiResponse;
import com.example.faceguard.dto.EmployeeDto;
import com.example.faceguard.model.Employee;
import org.springframework.stereotype.Service;

@Service
public interface EmployeeService {
    ApiResponse createEmployee(EmployeeDto employeeDto);
}
