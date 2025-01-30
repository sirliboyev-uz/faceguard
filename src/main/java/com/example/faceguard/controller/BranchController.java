package com.example.faceguard.controller;


import com.example.faceguard.dto.ApiResponse;
import com.example.faceguard.dto.BranchDto;
import com.example.faceguard.model.utils.Annotation.RoleCheckName;
import com.example.faceguard.service.BranchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1/branch")
@RestController()
public class BranchController {

    @Autowired
    private BranchService branchService;

    @RoleCheckName(value = "ADD_COMPANY")
    @PostMapping("/register")
    public HttpEntity<?> create(@RequestBody BranchDto branchDto){
        ApiResponse apiResponse = branchService.createBranch(branchDto);
        return ResponseEntity.status(apiResponse.getType()?200:409).body(apiResponse.getMessage());
    }
}