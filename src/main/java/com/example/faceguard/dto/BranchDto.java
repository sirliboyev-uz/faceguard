package com.example.faceguard.dto;

import lombok.Data;

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
