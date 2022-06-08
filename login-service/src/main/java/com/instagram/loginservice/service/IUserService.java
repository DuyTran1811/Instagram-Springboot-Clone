package com.instagram.loginservice.service;

import com.instagram.loginservice.module.User;
import com.instagram.loginservice.payload.request.LoginRequest;
import com.instagram.loginservice.payload.request.SignUpRequest;
import com.instagram.loginservice.payload.response.JwtResponse;

import java.util.List;

public interface IUserService {
    List<User> getAllUser();

    User finByUserName(String username);

    List<User> getListByUserName(String username);

    void upDateUserName(Long id, SignUpRequest user);

    void registerUser(SignUpRequest signUp);

    JwtResponse loginUser(LoginRequest login);
}
