package com.example.faceguard.service.impl;

import com.example.faceguard.dto.ApiResponse;
import com.example.faceguard.dto.DepartmentDto;
import com.example.faceguard.model.Branch;
import com.example.faceguard.model.Department;
import com.example.faceguard.repository.BranchRepository;
import com.example.faceguard.repository.DepartmentRepository;
import com.example.faceguard.service.DepartmentService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DepartmentServiceImpl implements DepartmentService {
    @Autowired
    private BranchRepository branchRepository;
    @Autowired
    private DepartmentRepository departmentRepository;
    @Override
    public ApiResponse createDepartment(DepartmentDto departmentDto) {
        Optional<Branch> branch = branchRepository.findById(departmentDto.getBranchId());
        Department department = new Department();
        department.setName(departmentDto.getName());
        if (branch.isPresent()) {
            department.setBranch(branch.get());
        }
        else new ApiResponse("wrong branch", false);
        departmentRepository.save(department);
        return new ApiResponse("cool", true);
    }
}
