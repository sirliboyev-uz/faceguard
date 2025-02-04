package com.example.faceguard.model;


import com.example.faceguard.template.AbstractEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Branch extends AbstractEntity {

    private String name;
    private String description;
    private String location;
    private String longitude;
    private String latitude;

    @ManyToOne
    @JoinColumn(name = "company_id")
    @JsonIgnore
    private Company company;

    @OneToMany(mappedBy = "branch", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Department> departments;

    public int getDepartmentCount() {
        return departments != null ? departments.size() : 0;
    }
}
