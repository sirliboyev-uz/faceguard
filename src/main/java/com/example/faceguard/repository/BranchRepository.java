package com.example.faceguard.repository;

import com.example.faceguard.model.Branch;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BranchRepository extends JpaRepository<Branch, Long> {
    boolean existsByName(String name);

    void deleteByCompanyId(Long id);

    Optional<Branch> findByCompanyId(Long id);
}
