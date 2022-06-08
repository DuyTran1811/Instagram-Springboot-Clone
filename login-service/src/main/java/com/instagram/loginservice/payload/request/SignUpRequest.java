package com.instagram.loginservice.payload.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.time.Instant;
import java.util.Date;
import java.util.Set;

@Data
public class SignUpRequest {
    @NotBlank
    private String username;
    @NotBlank
    private String email;
    @NotBlank
    private Set<String> role;
    @NotBlank
    private String password;
    private Instant createdAt;
    private String displayName;
    private String profilePictureUrl;
    private Date birthday;
    private String country;
    private String city;
    private String zipCode;
    private String streetName;
    private int buildingNumber;
    private boolean active;
}
