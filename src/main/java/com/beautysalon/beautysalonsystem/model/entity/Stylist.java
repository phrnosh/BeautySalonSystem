package com.beautysalon.beautysalonsystem.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@Getter
@Setter
@SuperBuilder

@Entity(name = "stylistEntity")
@Table(name="stylist_tbl")

public class Stylist extends Profile {

    @Id
    @SequenceGenerator(name = "stylistSeq", sequenceName = "stylist_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "stylistSeq")
    @Column(name = "id")
    private Long id;

    @Column(name = "career", length = 30)
    private String career;

}