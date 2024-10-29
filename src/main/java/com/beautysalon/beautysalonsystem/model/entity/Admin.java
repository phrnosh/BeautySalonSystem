package com.beautysalon.beautysalonsystem.model.entity;

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

@Entity(name = "adminEntity")
@Table(name="admin_tbl")

public class Admin extends Profile {
    @Id
    @SequenceGenerator(name = "adminSeq", sequenceName = "admin_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "adminSeq")
    @Column(name = "id")
    private Long id;

    @Column(name = "address", length = 100)
    @Pattern(regexp = "^[\\w\\s]{2,100}$", message = "invalid Address")
    private String address;

    @Column(name = "national_code", length = 10)
//    @Pattern(regexp = "^//d{10}$")
    private String nationalCode;

    @OneToOne
    private Role role ;

}