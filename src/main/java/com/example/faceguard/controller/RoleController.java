package com.example.faceguard.controller;

import com.example.faceguard.dto.ApiResponse;
import com.example.faceguard.dto.RoleRegisterDto;
import com.example.faceguard.model.utils.Annotation.RoleCheckName;
import com.example.faceguard.repository.RoleRepository;
import com.example.faceguard.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/role")
@CrossOrigin("*")
public class RoleController {
    @Autowired
    RoleService roleService;
    @Autowired
    private RoleRepository roleRepository;

    @RoleCheckName(value = "CREATE_ROLE")
    @PostMapping("/register")
    public HttpEntity<?> create(@RequestBody RoleRegisterDto roleRegisterDTO){
        ApiResponse apiResponse = roleService.register(roleRegisterDTO);
        return ResponseEntity.status(apiResponse.getType()?200:409).body(apiResponse.getMessage());
    }
    @RoleCheckName(value = "UPDATE_ROLE")
    @PutMapping("/update/{id}")
    public HttpEntity<?> update(@PathVariable Long id, @RequestBody RoleRegisterDto roleRegisterDTO){
        ApiResponse apiResponse=roleService.update(id, roleRegisterDTO);
        return ResponseEntity.status(apiResponse.getType()?200:409).body(apiResponse.getMessage());
    }
    @RoleCheckName(value = "DELETE_ROLE")
    @DeleteMapping("/delete/{id}")
    public HttpEntity<?> delete(@PathVariable Long id){
        ApiResponse apiResponse=roleService.delete(id);
        return ResponseEntity.status(apiResponse.getType()?200:409).body(apiResponse.getMessage());
    }
//
//    @RoleCheckName(value = "READ_ROLE")
//    @GetMapping("/select/{id}")
//    public HttpEntity<?> select(@PathVariable Long id){
//        ApiResponse apiResponse=roleService.role(id);
//        return ResponseEntity.status(apiResponse.getType()?200:409).body(apiResponse.getType()?apiResponse.getMessage()+"\n"+apiResponse.getObject():apiResponse.getMessage());
//    }

    @RoleCheckName(value = "READ_ROLE")
    @GetMapping("/list")
    public HttpEntity<?> select(){
        return ResponseEntity.ok(roleRepository.findAll());
    }
}
