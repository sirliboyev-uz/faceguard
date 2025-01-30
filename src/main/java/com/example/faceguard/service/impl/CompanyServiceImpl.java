package com.example.faceguard.service.impl;

import com.example.faceguard.dto.ApiResponse;
import com.example.faceguard.dto.CompanyDto;
import com.example.faceguard.model.Company;
import com.example.faceguard.repository.CompanyRepository;
import com.example.faceguard.service.CompanyService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    CompanyRepository companyRepository;


    @Override
    public ApiResponse createCompany(CompanyDto company) {
        if (companyRepository.existsByName(company.getName())) {
            return new ApiResponse("wrong", false);
        }
        Company companyEntity = new Company();
        BeanUtils.copyProperties(company, companyEntity);
        companyEntity = companyRepository.save(companyEntity);
        System.out.println(companyEntity);
        return new ApiResponse("success", true);
    }
}
