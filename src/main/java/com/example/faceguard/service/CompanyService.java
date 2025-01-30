package com.example.faceguard.service;

import com.example.faceguard.dto.ApiResponse;
import com.example.faceguard.dto.CompanyDto;
import com.example.faceguard.model.Company;

public interface CompanyService {

    ApiResponse createCompany(CompanyDto company);

}
