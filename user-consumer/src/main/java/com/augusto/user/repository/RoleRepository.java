package com.augusto.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.augusto.user.model.Role;
import com.google.common.base.Optional;


public interface RoleRepository extends JpaRepository<Role,Long> {
    Optional<Role> findByRole(String role);
}
