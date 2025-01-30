package com.example.faceguard.repository;

import com.example.faceguard.model.Branch;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BranchRepository extends JpaRepository<Branch, Long> {
    boolean existsByName(String name);
}
