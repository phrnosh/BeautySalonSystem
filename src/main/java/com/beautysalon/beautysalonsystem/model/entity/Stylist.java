package com.beautysalon.beautysalonsystem.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
@ToString

@Entity(name = "stylistEntity")
@Table(name = "stylist_tbl")

public class Stylist extends Profile {

    @Id
    @SequenceGenerator(name = "stylistSeq", sequenceName = "stylist_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "stylistSeq")
    @Column(name = "id")
    private Long id;

    @Column(name = "career", length = 30)
    private String career;

    @Column(name = "address", length = 100)
    @Pattern(regexp = "^[\\w\\s]{2,100}$", message = "invalid Address")
    private String address;

    @Column(name = "national_code", length = 10)
//    @Pattern(regexp = "^//d{10}$")
    private String nationalCode;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "Services_Stylist_tbl")
    private List<Services> services;

    public void addServices(Services service) {
        if (services == null) {
            services = new ArrayList<>();
        }
        services.add(service);
    }
}
