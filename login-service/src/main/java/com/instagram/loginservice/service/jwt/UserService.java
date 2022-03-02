package com.instagram.loginservice.service.jwt;

import com.instagram.loginservice.exception.EmailAlreadyExistsException;
import com.instagram.loginservice.exception.ResourceNotFoundException;
import com.instagram.loginservice.exception.UsernameAlreadyExistsException;
import com.instagram.loginservice.messaging.UserEventSender;
import com.instagram.loginservice.module.Role;
import com.instagram.loginservice.module.User;
import com.instagram.loginservice.repository.IUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);
    private final PasswordEncoder passwordEncoder;
    private final IUserRepository userRepository;
    private final UserEventSender userEventSender;

    public UserService(IUserRepository userRepository, PasswordEncoder passwordEncoder, UserEventSender userEventSender) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userEventSender = userEventSender;
    }

    public List<User> findAll() {
        LOGGER.info("retrieving all users");
        return userRepository.findAll();
    }

    public Optional<User> findByUsername(String username) {
        LOGGER.info("retrieving user {}", username);
        return userRepository.findByUsername(username);
    }

    public List<User> findByUsernameIn(List<String> usernames) {
        return userRepository.findByUsernameIn(usernames);
    }

    public User registerUser(User user) {
        LOGGER.info("registering user {}", user.getUsername());

        if (userRepository.existsByUsername(user.getUsername())) {
            LOGGER.warn("username {} already exists.", user.getUsername());

            throw new UsernameAlreadyExistsException(String.format("username %s already exists", user.getUsername()));
        }

        if (userRepository.existsByEmail(user.getEmail())) {
            LOGGER.warn("email {} already exists.", user.getEmail());

            throw new EmailAlreadyExistsException(String.format("email %s already exists", user.getEmail()));
        }
        user.setActive(true);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(new HashSet<>() {{
            add(Role.USER);
        }});

        User savedUser = userRepository.save(user);
        userEventSender.sendUserCreated(savedUser);

        return savedUser;
    }

    public User updateProfilePicture(String uri, String id) {
        LOGGER.info("update profile picture {} for user {}", uri, id);

        return userRepository
                .findById(id)
                .map(user -> {
                    String oldProfilePic = user.getUserProfile().getProfilePictureUrl();
                    user.getUserProfile().setProfilePictureUrl(uri);
                    User savedUser = userRepository.save(user);

                    userEventSender.sendUserUpdated(savedUser, oldProfilePic);

                    return savedUser;
                })
                .orElseThrow(() -> new ResourceNotFoundException(String.format("user id %s not found", id)));
    }
}
