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

import java.util.List;
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

    @Override
    public Optional<Branch> getBranchById(Long id) {
        return branchRepository.findById(id);
    }
    public Optional<Branch> updateBranch(Long id, BranchDto branchDTO) {
        return branchRepository.findById(id).map(branch -> {
            branch.setName(branchDTO.getName());
            branch.setDescription(branchDTO.getDescription());
            branch.setLocation(branchDTO.getLocation());
            branch.setLongitude(branchDTO.getLongitude());
            branch.setLatitude(branchDTO.getLatitude());

            companyRepository.findById(branchDTO.getCompanyId()).ifPresent(branch::setCompany);

            return branchRepository.save(branch);
        });
    }
    
    @Override
    public List<Branch> getBranchesByCompanyId(Long companyId) {
        return branchRepository.findByCompanyId(companyId);
    }
}
