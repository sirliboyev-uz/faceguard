package com.example.faceguard.service;

import com.example.faceguard.dto.ApiResponse;
import com.example.faceguard.dto.BranchDto;
import com.example.faceguard.dto.CompanyDto;
import com.example.faceguard.model.Branch;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface BranchService {
//    ApiResponse createBranch(BranchDto branchDto);

    ApiResponse createBranch(BranchDto branchDto);

    Optional<Branch> getBranchById(Long id);

    Optional<Branch> updateBranch(Long id, BranchDto branchDTO);
}
