package com.example.faceguard.repository;

import com.example.faceguard.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    boolean existsByPhoneNumber(String phone);
    long countByCompanyId(Long companyId);
}
