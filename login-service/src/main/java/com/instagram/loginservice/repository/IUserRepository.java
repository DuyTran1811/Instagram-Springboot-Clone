package com.instagram.loginservice.repository;

import com.instagram.loginservice.module.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IUserRepository extends MongoRepository<User, String> {
    Optional<User> findByUsername(String username);

    List<User> findByUsernameIn(List<String> usernames);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);
}
