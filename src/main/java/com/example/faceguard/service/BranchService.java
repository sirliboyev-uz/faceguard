package com.example.faceguard.service;

import com.example.faceguard.dto.ApiResponse;
import com.example.faceguard.dto.BranchDto;
import com.example.faceguard.dto.CompanyDto;
import org.springframework.stereotype.Service;

@Service
public interface BranchService {
    ApiResponse createBranch(BranchDto branchDto);

}
