package com.example.faceguard.dao;
import com.example.faceguard.model.Branch;
import lombok.Data;

@Data
public class BranchDao {
    private Long id;
    private String name;
    private String description;
    private String location;
    private String longitude;
    private String latitude;

    // Entity dan DTO ga o'tkazish
    public static BranchDao fromEntity(Branch branch) {
        BranchDao dto = new BranchDao();
        dto.setId(branch.getId());
        dto.setName(branch.getName());
        dto.setDescription(branch.getDescription());
        dto.setLocation(branch.getLocation());
        dto.setLongitude(branch.getLongitude());
        dto.setLatitude(branch.getLatitude());
        return dto;
    }
}
