package com.example.faceguard.controller;

import com.example.faceguard.dto.ApiResponse;
import com.example.faceguard.dto.CompanyDto;
import com.example.faceguard.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RequestMapping("/api/v1/company")
@Controller()
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @PostMapping("/register")
    public HttpEntity<?> create(@RequestBody CompanyDto companyDto){
        ApiResponse apiResponse = companyService.createCompany(companyDto);
        return ResponseEntity.status(apiResponse.getType()?200:409).body(apiResponse.getMessage());
    }
}
