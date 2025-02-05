package com.example.faceguard.service.impl;

import com.example.faceguard.dto.ApiResponse;
import com.example.faceguard.dto.CompanyDto;
import com.example.faceguard.model.Branch;
import com.example.faceguard.model.Company;
import com.example.faceguard.model.Department;
import com.example.faceguard.repository.BranchRepository;
import com.example.faceguard.repository.CompanyRepository;
import com.example.faceguard.repository.DepartmentRepository;
import com.example.faceguard.service.CompanyService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    CompanyRepository companyRepository;
    @Autowired
    private BranchRepository branchRepository;
    @Autowired
    private DepartmentRepository departmentRepository;


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

    @Override
    public ApiResponse update(Long id, CompanyDto company) {
        if (!companyRepository.findById(id).isPresent()) {
            return new ApiResponse("wrong", false);
        }
        Company companyEntity = companyRepository.findById(id).get();
        BeanUtils.copyProperties(company, companyEntity);
        companyEntity = companyRepository.save(companyEntity);
        System.out.println(companyEntity);
        return new ApiResponse("success", true);
    }

    @Transactional
    @Override
    public ApiResponse deleteCompany(Long id) {
        Optional<Company> companyOptional = companyRepository.findById(id);

        if (companyOptional.isEmpty()) {
            return new ApiResponse("error: company not found", false);
        }

        // Kompaniyaga tegishli branchlarni topamiz
        List<Branch> branches = branchRepository.findByCompanyId(id);

        // Har bir branch uchun departmentlarni o‘chiramiz
        for (Branch branch : branches) {
            departmentRepository.deleteByBranchId(branch.getId());
        }

        // Endi barcha branchlarni o‘chiramiz
        branchRepository.deleteByCompanyId(id);

        // Kompaniyani o‘chiramiz
        companyRepository.deleteById(id);

        return new ApiResponse("Successfully deleted company and its branches & departments", true);
    }


//    @Override
//    public ApiResponse getCompany(Long id) {
//        Optional<Company> company=companyRepository.findById(id);
//        if (company.isPresent()) return new ApiResponse("Maxsulot", true, company);
//        return new ApiResponse("Maxsulot topilmadi", false);
//    }
}
