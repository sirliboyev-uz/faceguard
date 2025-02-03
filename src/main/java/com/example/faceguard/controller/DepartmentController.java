package com.example.faceguard.controller;

import com.example.faceguard.dto.ApiResponse;
import com.example.faceguard.dto.CompanyDto;
import com.example.faceguard.dto.DepartmentDto;
import com.example.faceguard.model.Department;
import com.example.faceguard.model.utils.Annotation.RoleCheckName;
import com.example.faceguard.repository.DepartmentRepository;
import com.example.faceguard.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/department")
@RestController()
@CrossOrigin("*")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private DepartmentRepository departmentRepository;

    @RoleCheckName(value = "ADD_COMPANY")
    @PostMapping("/register")
    public HttpEntity<?> create(@RequestBody DepartmentDto companyDto){
        ApiResponse apiResponse = departmentService.createDepartment(companyDto);
        return ResponseEntity.status(apiResponse.getType()?200:409).body(apiResponse.getMessage());
    }
    @RoleCheckName(value = "ADD_COMPANY")
    @GetMapping("/list")
    public HttpEntity<?> departmentList(){
        return ResponseEntity.ok(departmentRepository.findAll());
    }
}
