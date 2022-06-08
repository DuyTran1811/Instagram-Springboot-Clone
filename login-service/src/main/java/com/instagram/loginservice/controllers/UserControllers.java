package com.instagram.loginservice.controllers;

import com.instagram.loginservice.payload.request.LoginRequest;
import com.instagram.loginservice.payload.request.SignUpRequest;
import com.instagram.loginservice.payload.response.JwtResponse;
import com.instagram.loginservice.payload.response.MessageResponse;
import com.instagram.loginservice.service.IUserService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserControllers {
    private final IUserService userService;

    public UserControllers(IUserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signing")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        JwtResponse jwtResponse = userService.loginUser(loginRequest);
        return ResponseEntity.ok(jwtResponse);
    }

    @PostMapping(value = "/signup", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createUser(@Valid @RequestBody SignUpRequest signUpRequest) {
        userService.registerUser(signUpRequest);
        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    @PutMapping(value = "/updateUser/{id}")
    public ResponseEntity<?> updateUser(@PathVariable(value = "id") long id, @RequestBody SignUpRequest update) {
        userService.upDateUserName(id, update);
        return ResponseEntity.ok(new MessageResponse("User Update successfully!"));
    }
}