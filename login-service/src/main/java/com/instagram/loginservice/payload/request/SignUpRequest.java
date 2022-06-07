package com.instagram.loginservice.payload.request;

import lombok.Data;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.Instant;
import java.util.Date;
import java.util.Set;

@Data
public class SignUpRequest {
    @NotBlank
    @Size(min = 3, max = 20)
    @Column(name = "username", nullable = false, length = 25)
    private String username;
    @NotBlank @Size(max = 50) @Email
    @Column(name = "email", nullable = false, length = 50)
    private String email;
    @Column(name = "role")
    private Set<String> role;
    @Size(min = 6, max = 40)
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "created_at")
    private Instant createdAt;
    @Column(name = "display_name", length = 30)
    private String displayName;
    @Column(name = "profile_picture_url")
    private String profilePictureUrl;
    @Column(name = "birthday")
    private Date birthday;
    @Column(name = "country")
    private String country;
    @Column(name = "city")
    private String city;
    @Column(name = "zip_code")
    private String zipCode;
    @Column(name = "street_name")
    private String streetName;
    @Column(name = "building_number")
    private int buildingNumber;
    @Column(name = "active")
    private boolean active;

}
