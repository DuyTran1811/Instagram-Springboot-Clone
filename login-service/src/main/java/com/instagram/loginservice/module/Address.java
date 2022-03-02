package com.instagram.loginservice.module;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Table(name = "address")
public class Address {
    @Id
    @Column(name = "address_id")
    private String id;
    // Quoc Gia
    private String country;
    // Thanh Pho
    private String city;
    private String zipCode;
    // Ten duong
    private String streetName;
    // So nha
    private int buildingNumber;

    @OneToOne(mappedBy = "address")
    private User user;
}
