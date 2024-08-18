package com.farmCity.farm_city_be.models;

import com.farmCity.farm_city_be.enums.Gender;
import com.farmCity.farm_city_be.enums.Roles;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "users_table")
public class UserEntity extends BaseEntity {

    @Column(unique = true, nullable = false, updatable = false, length = 50)
    private String username;

    @Column(length = 35)
    private String firstName;

    @Column(length = 35)
    private String lastName;

    @Column(nullable = false, length = 100)
    private String email;

    @Column(nullable = false, length = 60)
    private String password;

    @Transient
    private String confirmPassword;

    @Column(length = 15)
    private String phoneNumber;

    private String address;

    @Column(length = 10)
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(length = 11)
    private String dob;

    @Column(length = 100)
    private String pictureUrl;

    @Column(length = 50)
    @Enumerated(EnumType.STRING)
    private Roles roles;

    private boolean isVerified;
}
