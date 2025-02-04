package com.example.faceguard.controller;

import com.example.faceguard.dto.ApiResponse;
import com.example.faceguard.dto.CompanyDto;
import com.example.faceguard.dto.DepartmentDto;
import com.example.faceguard.model.Department;
import com.example.faceguard.model.utils.Annotation.RoleCheckName;
import com.example.faceguard.repository.DepartmentRepository;
import com.example.faceguard.repository.EmployeeRepository;
import com.example.faceguard.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequestMapping("/api/v1/department")
@RestController()
@CrossOrigin("*")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private EmployeeRepository employeeRepository;

    @RoleCheckName(value = "ADD_COMPANY")
    @PostMapping("/register")
    public HttpEntity<?> create(@RequestBody DepartmentDto departmentDto){
//        return ResponseEntity.ok(departmentService.createDepartment(departmentDto));
        return ResponseEntity.ok(departmentService.createDepartment(departmentDto));
    }
    @RoleCheckName(value = "ADD_COMPANY")
    @GetMapping("/list")
    public HttpEntity<?> departmentList() {
        List<Department> departments = departmentRepository.findAll();

        List<Map<String, Object>> response = departments.stream().map(department -> {
            Map<String, Object> departmentData = new HashMap<>();
            departmentData.put("id", department.getId());
            departmentData.put("name", department.getName());

            // Fetch employee count by querying the Employee table based on department id
            long employeeCount = employeeRepository.countByBranchId(department.getId());

            departmentData.put("employeeCount", employeeCount); // Count employees in the department
            departmentData.put("branchName", department.getBranch() != null ? department.getBranch().getName() : "No Branch");

            return departmentData;
        }).collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }



}
