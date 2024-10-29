package com.beautysalon.beautysalonsystem.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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

@Entity(name = "roleEntity")
@Table(name="role_tbl")

public class Role extends Base {
    @Id
    @Column(name = "role_name", length = 20)
    private String role;
}
