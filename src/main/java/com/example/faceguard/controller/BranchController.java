package com.example.faceguard.controller;


import com.example.faceguard.dto.BranchDto;
import com.example.faceguard.model.Branch;
import com.example.faceguard.model.Company;
import com.example.faceguard.model.Department;
import com.example.faceguard.model.utils.Annotation.RoleCheckName;
import com.example.faceguard.repository.BranchRepository;
import com.example.faceguard.repository.CompanyRepository;
import com.example.faceguard.repository.DepartmentRepository;
import com.example.faceguard.service.BranchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RequestMapping("/api/v1/branch")
@CrossOrigin("*")
@RestController()
public class BranchController {

    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    private BranchService branchService;
    @Autowired
    private BranchRepository branchRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @RoleCheckName(value = "CREATE_BRANCH")
    @PostMapping("/register")
    public ResponseEntity<?> createBranch(@RequestBody BranchDto branchDto) {
        Optional<Company> company = companyRepository.findById(branchDto.getCompanyId());
        if (!company.isPresent()) return new ResponseEntity<>("Company not found", HttpStatus.NOT_FOUND);
        Branch branch = new Branch();
        branch.setName(branchDto.getName());
        branch.setDescription(branchDto.getDescription());
        branch.setLocation(branchDto.getLocation());
        branch.setLongitude(branchDto.getLongitude());
        branch.setLatitude(branchDto.getLatitude());
        branch.setCompany(company.get());  // Set the company object
        branchRepository.save(branch);
        return ResponseEntity.ok(branch);
    }

    @RoleCheckName(value = "READ_BRANCH")
    @GetMapping("/list")
    public HttpEntity<?> branchList() {
        List<Branch> branches = branchRepository.findAll();

        List<Map<String, Object>> response = branches.stream().map(branch -> {
            Map<String, Object> branchData = new HashMap<>();
            branchData.put("id", branch.getId());
            branchData.put("name", branch.getName());
            branchData.put("description", branch.getDescription());
            branchData.put("location", branch.getLocation());
            branchData.put("longitude", branch.getLongitude());
            branchData.put("latitude", branch.getLatitude());
            branchData.put("departmentCount", branch.getDepartmentCount());
            branchData.put("company", branch.getCompany() != null ? branch.getCompany().getName() : "No Company");
            return branchData;
        }).collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }


    @RoleCheckName(value = "DELETE_BRANCH")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBranch(@PathVariable Long id){
        return branchRepository.findById(id).map(branch -> {
            branchRepository.delete(branch);
            return ResponseEntity.ok("Company and related branches & departments deleted successfully!");
        }).orElse(ResponseEntity.notFound().build());
    }

    @RoleCheckName(value = "READ_BRANCH")
    @GetMapping("/{id}")
    public ResponseEntity<Branch> getBranch(@PathVariable Long id) {
        Optional<Branch> branch = branchService.getBranchById(id);
        return branch.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @RoleCheckName(value = "UPDATE_BRANCH")
    @PutMapping("/update/{id}")
    public ResponseEntity<Branch> updateBranch(@PathVariable Long id, @RequestBody BranchDto branchDTO) {
        Optional<Branch> updatedBranch = branchService.updateBranch(id, branchDTO);
        return updatedBranch.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @RoleCheckName(value = "READ_BRANCH")
    @GetMapping("/list/{companyId}")
    public List<Branch> getBranchesByCompany(@PathVariable Long companyId) {
        return branchService.getBranchesByCompanyId(companyId);
    }


    @RoleCheckName(value = "READ_BRANCH")
    @GetMapping("/list-by-department/{departmentId}")
    public ResponseEntity<List<Branch>> getBranchesByDepartment(@PathVariable Long departmentId) {
        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new RuntimeException("Department not found"));

        Long companyId = department.getBranch().getCompany().getId();
        List<Branch> branches = branchRepository.findByCompanyId(companyId);

        return ResponseEntity.ok(branches);
    }

}