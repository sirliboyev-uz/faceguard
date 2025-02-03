package com.example.faceguard.controller;

import com.example.faceguard.dto.ApiResponse;
import com.example.faceguard.dto.CompanyDto;
import com.example.faceguard.model.utils.Annotation.RoleCheckName;
import com.example.faceguard.repository.CompanyRepository;
import com.example.faceguard.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/company")
@RestController()
@CrossOrigin("*")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @Autowired
    private CompanyRepository companyRepository;

    @RoleCheckName(value = "ADD_COMPANY")
    @PostMapping("/register")
    public HttpEntity<?> create(@RequestBody CompanyDto companyDto){
        ApiResponse apiResponse = companyService.createCompany(companyDto);
        return ResponseEntity.status(apiResponse.getType()?200:409).body(apiResponse.getMessage());
    }
    @RoleCheckName(value = "ADD_COMPANY")
    @GetMapping("/list")
    public HttpEntity<?> companyList(){
        return ResponseEntity.ok(companyRepository.findAll());
    }
}
