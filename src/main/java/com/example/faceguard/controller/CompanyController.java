package com.example.faceguard.controller;

import com.example.faceguard.dao.CompanyDao;
import com.example.faceguard.dto.ApiResponse;
import com.example.faceguard.dto.CompanyDto;
import com.example.faceguard.model.utils.Annotation.RoleCheckName;
import com.example.faceguard.repository.CompanyRepository;
import com.example.faceguard.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/api/v1/company")
@RestController()
@CrossOrigin("*")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @Autowired
    private CompanyRepository companyRepository;

    @RoleCheckName(value = "CREATE_COMPANY")
    @PostMapping("/register")
    public HttpEntity<?> create(@RequestBody CompanyDto companyDto){
        ApiResponse apiResponse = companyService.createCompany(companyDto);
        return ResponseEntity.status(apiResponse.getType()?200:409).body(apiResponse.getMessage());
    }

    @RoleCheckName(value = "UPDATE_COMPANY")
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateEmployee(@PathVariable Long id, @RequestBody CompanyDto dto){
        ApiResponse apiResponse=companyService.update(id,dto);
        return ResponseEntity.status(apiResponse.getType()?200:409).body(apiResponse.getMessage());
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getCompany(@PathVariable Long id){
//        ApiResponse food = companyService.getCompany(id);
        return ResponseEntity.ok(companyRepository.findById(id).orElse(null));
    }

    @RoleCheckName(value = "READ_COMPANY")
    @GetMapping("/list")
    public ResponseEntity<List<CompanyDao>> companyList() {
        List<CompanyDao> companyDTOs = companyRepository.findAll().stream()
                .map(CompanyDao::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(companyDTOs);
    }


    @RoleCheckName(value = "DELETE_COMPANY")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCompany(@PathVariable Long id){
        return companyRepository.findById(id).map(company -> {
            companyRepository.delete(company);
            return ResponseEntity.ok("Company and related branches & departments deleted successfully!");
        }).orElse(ResponseEntity.notFound().build());
    }
}
