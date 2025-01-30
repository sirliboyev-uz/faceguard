package com.example.faceguard.service.impl;

import com.example.faceguard.dto.ApiResponse;
import com.example.faceguard.dto.BranchDto;
import com.example.faceguard.model.Branch;
import com.example.faceguard.model.Company;
import com.example.faceguard.repository.BranchRepository;
import com.example.faceguard.repository.CompanyRepository;
import com.example.faceguard.service.BranchService;
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
        Optional<Company> company = companyRepository.findById(branchDto.getCompanyId());
        if (company.isPresent()) {
            Company companyObj = company.get();
            if (!branchRepository.existsByName(branchDto.getName())) {
                Branch branch = new Branch();
                branch.setName(branchDto.getName());
                branch.setDescription(branchDto.getDescription());
                branch.setLatitude(branchDto.getLatitude());
                branch.setLongitude(branchDto.getLongitude());
                branch.setLocation(branchDto.getLocation());
                branch.setCompany(companyObj);
                branchRepository.save(branch);
                System.out.println(branch);
                return new ApiResponse("success", true);
            }
            return new ApiResponse("warning", false);
        }
        return new ApiResponse("error", true);
    }
}
