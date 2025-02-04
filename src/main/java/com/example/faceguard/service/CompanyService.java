package com.example.faceguard.service;

import com.example.faceguard.dto.ApiResponse;
import com.example.faceguard.dto.CompanyDto;
import com.example.faceguard.model.Company;
import org.springframework.stereotype.Service;

@Service
public interface CompanyService {

    ApiResponse createCompany(CompanyDto company);
    ApiResponse update(Long id, CompanyDto company);

    ApiResponse deleteCompany(Long id);

//    ApiResponse getCompany(Long id);
}
