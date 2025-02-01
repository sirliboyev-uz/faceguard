package com.example.faceguard.model;

import com.example.faceguard.template.AbstractEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Employee extends AbstractEntity {

    private String firstName;
    private String lastName;
    private String middleName;
    private String email;
    private String phone;
    private Date birthday;
    private String gender;
    private String jobTitle;

    private String schedule;
    private double salary;
    private String login;
    private String password;

    @ManyToOne
    @JoinColumn(name = "branch_id")
    private Branch branch;
}
