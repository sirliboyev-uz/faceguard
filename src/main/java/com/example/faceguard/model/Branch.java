package com.example.faceguard.model;


import com.example.faceguard.template.AbstractEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

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
    private Company company;
}
