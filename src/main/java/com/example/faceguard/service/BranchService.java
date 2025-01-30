package com.example.faceguard.service;

import com.example.faceguard.dto.ApiResponse;
import com.example.faceguard.dto.BranchDto;
import com.example.faceguard.dto.CompanyDto;

public interface BranchService {
    ApiResponse createBranch(BranchDto branchDto);

}
