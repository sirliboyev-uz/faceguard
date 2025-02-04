package com.example.faceguard.dto;

import com.example.faceguard.model.Branch;
import lombok.Data;

@Data
public class DepartmentDto {
    private String name;
    private Long branchId;
    private Long cameraId;
}
