package com.example.faceguard.dto;

import com.example.faceguard.model.Company;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
public class BranchDto {

    private String name;
    private String description;
    private String location;
    private String longitude;
    private String latitude;
    private Long companyId;
}
