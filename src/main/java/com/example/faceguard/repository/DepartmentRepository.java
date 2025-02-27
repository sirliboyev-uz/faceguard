package com.example.faceguard.repository;

import com.example.faceguard.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DepartmentRepository extends JpaRepository<Department, Long> {

    Optional<Department> findByBranchId(Long id);

    void deleteByBranchId(Long id);
}
