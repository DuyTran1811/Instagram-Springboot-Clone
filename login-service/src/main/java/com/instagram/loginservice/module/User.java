package com.instagram.loginservice.module;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.time.Instant;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user", uniqueConstraints = {
        @UniqueConstraint(columnNames = "username"), @UniqueConstraint(columnNames = "email")})
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Size(min = 3, max = 20)
    @Column(name = "username", nullable = false, length = 25)
    private String username;
    @JsonIgnore
    @Column(name = "password", nullable = false)
    private String password;
    @Email @Column(name = "email", nullable = false, length = 50)
    private String email;
    @Column(name = "created_at")
    private Instant createdAt;
    @Column(name = "updated_at")
    private Instant updatedAt;
    @Column(name = "display_name", length = 30)
    private String displayName;
    @Column(name = "profile_picture_url")
    private String profilePictureUrl;
    @Column(name = "birthday")
    private Date birthday;
    @Column(name = "country", length = 30)
    private String country;
    @Column(name = "city", length = 30)
    private String city;
    @Column(name = "zip_code", length = 10)
    private String zipCode;
    @Column(name = "street_name")
    private String streetName;
    @Column(name = "building_number")
    private int buildingNumber;
    @Column(name = "active")
    private boolean active;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();
}
