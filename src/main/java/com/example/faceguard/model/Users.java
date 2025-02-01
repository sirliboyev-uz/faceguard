package com.example.faceguard.model;


import com.example.faceguard.model.permission.Permission;
import com.example.faceguard.template.AbstractEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Users extends AbstractEntity implements UserDetails {

    private String firstName;
    private String lastName;
    private String middleName;
    private String email;
    private String phoneNumber;
    private Date birthDate;
    private String gender;
    private String jobTitle;
    private String username;
    private String password;
    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;
    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;
    @OneToOne
    @JoinColumn(name = "image_id")
    private Image image;

    public Users(String firstName, String lastName, String middleName, String email, String phoneNumber, Date birthDate, String gender, String jobTitle, String username, String password, Company company, Role role, Image image) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.birthDate = birthDate;
        this.gender = gender;
        this.jobTitle = jobTitle;
        this.username = username;
        this.password = password;
        this.company = company;
        this.role = role;
        this.image = image;
    }

    private boolean enabled=true;
    private boolean isAccountNonExpired=true;
    private boolean isAccountNonLocked=true;
    private boolean isCredentialsNonExpired=true;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<Permission> roleTypes = role.getPermissions();
        List<GrantedAuthority> grantedAuthorities=new ArrayList<>();
        for (Permission i: roleTypes){
            grantedAuthorities.add(new SimpleGrantedAuthority(i.name()));
        }
        return grantedAuthorities;
    }

    @Override
    public String getUsername() {
        return username;
    }
    @Override
    public boolean isAccountNonExpired() {
        return isAccountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isAccountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isCredentialsNonExpired;
    }
}
