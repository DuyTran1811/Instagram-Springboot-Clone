package com.instagram.loginservice.module;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "profile")
public class Profile {
    @Id
    @Column(name = "profile_id")
    private Integer profileId;
    // Ten Hien Thi
    private String displayName;
    // Anh ho so
    private String profilePictureUrl;
    // Ngay Thang nam Sinh
    private Date birthday;

    @OneToOne(mappedBy = "profile")
    private User user;
}
