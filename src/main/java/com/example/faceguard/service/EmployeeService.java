package com.example.faceguard.service;


import com.example.faceguard.dto.ApiResponse;
import com.example.faceguard.dto.EmployeeDto;
import com.example.faceguard.model.Employee;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface EmployeeService {
    ApiResponse createEmployee(EmployeeDto employeeDto);
    ApiResponse updateEmployee(Long id, EmployeeDto employeeDto);
    ApiResponse deleteEmployee(Long id);
    Employee getEmployeeById(Long id);
    List<Employee> getAllEmployees();
}
