package com.example.faceguard.service.impl;

import com.example.faceguard.dto.ApiResponse;
import com.example.faceguard.dto.BranchDto;
import com.example.faceguard.model.Branch;
import com.example.faceguard.model.Company;
import com.example.faceguard.repository.BranchRepository;
import com.example.faceguard.repository.CompanyRepository;
import com.example.faceguard.service.BranchService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BranchServiceImpl implements BranchService {

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private BranchRepository branchRepository;

    @Override
    public ApiResponse createBranch(BranchDto branchDto) {
        Optional<Company> companyOptional = companyRepository.findById(branchDto.getCompanyId());
        if (companyOptional.isPresent()) {
            branchDto.setCompanyId(companyOptional.get().getId());
            Branch branch = new Branch();
            BeanUtils.copyProperties(branchDto, branch);
            branchRepository.save(branch);
            return new ApiResponse("registered", true);
        } else {
            throw new RuntimeException("Company not found!");
        }
    }
}
