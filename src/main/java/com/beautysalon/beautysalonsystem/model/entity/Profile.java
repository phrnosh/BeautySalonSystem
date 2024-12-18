package com.beautysalon.beautysalonsystem.model.entity;

import com.beautysalon.beautysalonsystem.model.entity.enums.UserState;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
@ToString

@MappedSuperclass
public abstract class Profile extends Base {

    @Column(name="name", length = 30, nullable = false)
    @Pattern(regexp = "^[a-zA-Z\\s]{3,30}$" ,message = "Invalid Name")
    private String name;

    @Column(name="family", length = 30, nullable = false)
    @Pattern(regexp = "^[a-zA-Z\\s]{3,30}$" ,message = "Invalid Family")
    private String family;

    @Column(name="email", length = 50)
    @Pattern(regexp = "^\\w{3,35}@(gmail|yahoo|microsoft)\\.com$" ,message = "Invalid Email")
    private String email;

    @Column(name="phone_number", length = 15, unique = true)
    @Pattern(regexp = "^(09|\\+989)\\d{9}$" ,message = "Invalid PhoneNumber")
    private String phoneNumber;

//    @Enumerated(EnumType.ORDINAL)
//    private UserState status;

//    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
//    @JoinTable(name="profile_address_tbl")
//    private Address address;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name="username")
    private User user ;
}