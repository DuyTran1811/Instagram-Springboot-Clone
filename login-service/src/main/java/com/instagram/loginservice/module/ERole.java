package com.instagram.loginservice.module;

public enum ERole {
    USER("USER"),
    SERVICE("SERVICE"),
    ADMIN("ADMIN");
    private String name;

    ERole(String name) {
        this.name = name;
    }
}