package com.instagram.loginservice.repository;

import com.instagram.loginservice.module.ERole;
import com.instagram.loginservice.module.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IRoleRepository extends JpaRepository<Role, Integer> {
    @Query("SELECT r FROM Role r WHERE r.name =:roleName")
    Optional<Role> findRoleByName(ERole roleName);
}
