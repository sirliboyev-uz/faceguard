package com.example.faceguard.dao;

import com.example.faceguard.model.Company;
import lombok.Data;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class CompanyDao {
    private Long id;
    private String name;
    private String address;
    private String email;
    private String phone;
    private int branchCount;  // Filiallar soni
    private int departmentCount;  // Departamentlar soni
    private List<BranchDao> branches;

    // Entity dan DTO ga o'tkazish
    public static CompanyDao fromEntity(Company company) {
        CompanyDao dto = new CompanyDao();
        dto.setId(company.getId());
        dto.setName(company.getName());
        dto.setAddress(company.getAddress());
        dto.setEmail(company.getEmail());
        dto.setPhone(company.getPhone());

        // Filiallar soni
        dto.setBranchCount(company.getBranches().size());

        // Departamentlar sonini hisoblash (har bir filialning departamentlari sonini qo‘shish)
        int totalDepartments = company.getBranches().stream()
                .mapToInt(branch -> branch.getDepartments().size()) // Branch ichidagi departamentlar sonini olamiz
                .sum();
        dto.setDepartmentCount(totalDepartments);

        // Filiallarni DTO ga aylantiramiz
        dto.setBranches(company.getBranches().stream()
                .map(BranchDao::fromEntity)
                .collect(Collectors.toList()));

        return dto;
    }
}
