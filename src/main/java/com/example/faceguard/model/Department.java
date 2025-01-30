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
public class Department extends AbstractEntity {

    private String name;

    @ManyToOne
    @JoinColumn(name = "branch_id")
    private Branch branch;
}
