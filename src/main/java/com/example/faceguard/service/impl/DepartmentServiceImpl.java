package com.example.faceguard.service.impl;

import com.example.faceguard.dto.ApiResponse;
import com.example.faceguard.dto.DepartmentDto;
import com.example.faceguard.exceptions.ResourceNotFoundException;
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
        Optional<Branch> branchOptional = branchRepository.findById(departmentDto.getBranchId());
        if (branchOptional.isPresent()) {
            Department department = new Department();
            department.setBranch(branchOptional.get());
            BeanUtils.copyProperties(departmentDto, department);
            departmentRepository.save(department);
            return new ApiResponse("Registered", true);
        } else {
            throw new RuntimeException("Branch not found!");
        }
    }

    @Override
    public Department updateDepartment(Long id, DepartmentDto departmentDto) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Department not found"));
        Branch branch = branchRepository.findById(departmentDto.getBranchId())
                .orElseThrow(() -> new ResourceNotFoundException("Branch not found"));
        department.setName(departmentDto.getName());
        department.setBranch(branch);
        return departmentRepository.save(department);
    }

    @Override
    public Department getDepartmentById(Long id) {
        return departmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Department not found with id: " + id));
    }

    @Override
    public void deleteDepartment(Long id) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Department not found with id: " + id));

        departmentRepository.delete(department);
    }
}
