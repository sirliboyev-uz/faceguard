package com.example.faceguard.repository;

import com.example.faceguard.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    boolean existsByPhone(String phone);
    long countByBranchId(Long branchId);
}
