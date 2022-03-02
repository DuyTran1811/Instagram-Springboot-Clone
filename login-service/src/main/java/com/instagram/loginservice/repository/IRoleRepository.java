package com.instagram.loginservice.repository;

import com.instagram.loginservice.module.ERole;
import com.instagram.loginservice.module.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.Optional;
@EnableJpaRepositories
public interface IRoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByName(ERole name);
}
