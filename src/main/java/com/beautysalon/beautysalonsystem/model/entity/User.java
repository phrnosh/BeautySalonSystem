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

@Entity(name = "userEntity")
@Table(name = "user_tbl")

public class User extends Base {
    @Id
    @Column(name = "username", length = 30)
    @Pattern(regexp = "^[a-zA-Z\\d\\S\\._]{3,30}$" ,message = "Invalid username")
    private String username;

    @Column(name = "password", length = 15, nullable = false)
    @Pattern(regexp = "^[\\w\\S]{3,30}$" ,message = "Invalid Password")
    private String password;

    @OneToOne(cascade = {CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "username"),
            inverseJoinColumns = @JoinColumn(name = "role_name"),
            foreignKey = @ForeignKey(name = "fk_user_role"),
            inverseForeignKey = @ForeignKey(name = "fk_inverse_user_role"))
    private Role role;

    @Column(name = "locked")
    private boolean locked;}