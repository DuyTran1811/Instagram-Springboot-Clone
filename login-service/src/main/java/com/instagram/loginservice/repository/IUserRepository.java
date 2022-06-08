package com.instagram.loginservice.repository;

import com.instagram.loginservice.module.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    @Query("SELECT u FROM User u WHERE u.username =:username")
    List<User> getListByUserName(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);
}
