package com.instagram.loginservice.service;

import com.instagram.loginservice.config.UserDetailsImpl;
import com.instagram.loginservice.config.jwt.JwtUtils;
import com.instagram.loginservice.exception.EmailAlreadyExistsException;
import com.instagram.loginservice.exception.UsernameAlreadyExistsException;
import com.instagram.loginservice.module.ERole;
import com.instagram.loginservice.module.Role;
import com.instagram.loginservice.module.User;
import com.instagram.loginservice.payload.request.LoginRequest;
import com.instagram.loginservice.payload.request.SignUpRequest;
import com.instagram.loginservice.payload.response.JwtResponse;
import com.instagram.loginservice.repository.IRoleRepository;
import com.instagram.loginservice.repository.IUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService implements IUserService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);
    private final AuthenticationManager authenticationManager;
    private final IUserRepository userRepository;
    private final IRoleRepository roleRepository;
    private final PasswordEncoder encoder;
    private final JwtUtils jwtUtils;

    public UserService(AuthenticationManager authenticationManager, IUserRepository userRepository,
                       IRoleRepository roleRepository, PasswordEncoder encoder, JwtUtils jwtUtils) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.encoder = encoder;
        this.jwtUtils = jwtUtils;
    }

    @Override
    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    @Override
    public User finByUserName(String username) {
        return userRepository.findByUsername(username).orElseThrow(null);
    }

    @Override
    public List<User> getListByUserName(String username) {
        return userRepository.getListByUserName(username);
    }

    @Override
    public void upDateUserName(Long id, SignUpRequest updateRequest) {
        User oldUser = userRepository.getById(id);
        oldUser.setEmail(updateRequest.getEmail());
        oldUser.setCreatedAt(updateRequest.getCreatedAt());
        oldUser.setUpdatedAt(Instant.now());
        oldUser.setDisplayName(updateRequest.getDisplayName());
        oldUser.setProfilePictureUrl(updateRequest.getProfilePictureUrl());
        oldUser.setBirthday(updateRequest.getBirthday());
        oldUser.setCountry(updateRequest.getCountry());
        oldUser.setCity(updateRequest.getCity());
        oldUser.setZipCode(updateRequest.getZipCode());
        oldUser.setStreetName(updateRequest.getStreetName());
        oldUser.setBuildingNumber(updateRequest.getBuildingNumber());
        oldUser.setActive(updateRequest.isActive());
        oldUser.setRoles(checkRole(updateRequest));
        userRepository.save(oldUser);
        LOGGER.info("Update successfully user {}", oldUser);
    }

    public void registerUser(SignUpRequest signUpRequest) {
        LOGGER.info("Creating user {}", signUpRequest.getUsername());
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            LOGGER.warn("UserName {} already exists.", signUpRequest.getUsername());
            throw new UsernameAlreadyExistsException(String.format("Username %s already exists", signUpRequest.getUsername()));
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            LOGGER.warn("Email {} already exists.", signUpRequest.getEmail());
            throw new EmailAlreadyExistsException(String.format("email %s already exists", signUpRequest.getEmail()));
        }
        Set<Role> roles = checkRole(signUpRequest);
        // Create new user's account
        User user = User.builder()
                .username(signUpRequest.getUsername())
                .email(signUpRequest.getEmail())
                .roles(roles)
                .password(encoder.encode(signUpRequest.getPassword()))
                .createdAt(Instant.now())
                .displayName(signUpRequest.getDisplayName())
                .profilePictureUrl(signUpRequest.getProfilePictureUrl())
                .birthday(new Date())
                .country(signUpRequest.getCountry())
                .city(signUpRequest.getCity())
                .zipCode(signUpRequest.getZipCode())
                .streetName(signUpRequest.getStreetName())
                .buildingNumber(signUpRequest.getBuildingNumber())
                .active(true)
                .build();
        userRepository.save(user);
        LOGGER.info("User registered successfully! {}", user);
    }

    @Override
    public JwtResponse loginUser(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        LOGGER.info("LoginUser successfully! {}", loginRequest.getUsername());
        List<String> roles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
        return new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(), userDetails.getEmail(), roles);
    }

    private Set<Role> checkRole(SignUpRequest request) {
        Set<String> strRoles = request.getRole();
        Set<Role> roles = new HashSet<>();
        if (strRoles == null) {
            Role roleUser = roleRepository.findRoleByName(ERole.USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(roleUser);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "ADMIN":
                        Role adminRole = roleRepository.findRoleByName(ERole.ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);
                        break;
                    case "SERVICE":
                        Role serviceRole = roleRepository.findRoleByName(ERole.SERVICE)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(serviceRole);
                        break;
                    case "USER":
                        Role userRole = roleRepository.findRoleByName(ERole.USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                        break;
                }
            });
        }
        return roles;
    }
}
