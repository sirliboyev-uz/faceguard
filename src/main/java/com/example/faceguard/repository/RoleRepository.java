package com.example.faceguard.repository;

import com.example.faceguard.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRoleName(String roleName);

    boolean existsByRoleName(String name);

    boolean existsByRoleNameAndIdNot(String name, Long id);

//    Role getById(Role roleId);

    Role findById(Role roleId);
}
