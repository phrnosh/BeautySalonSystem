package com.beautysalon.beautysalonsystem.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
@SuperBuilder

@Entity(name = "servicesEntity")
@Table(name="services_tbl")

public class Services extends Base{

    @Id
    @SequenceGenerator(name = "servicesSeq", sequenceName = "services_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "servicesSeq")
    @Column(name = "id")
    private Long id;

    @Column(name = "name", length = 60)
//    @Pattern(regexp = "^[a-zA-Z\\s\\d]{3,60}$", message = "Invalid Name")
    private String name;

    @Column(name = "description",length = 100)
    private String description;

    @Column(name = "stylist_name", length = 30)
    //@Pattern(regexp = "^[a-zA-Z\\d\\S\\._]{3,30}$" ,message = "Invalid username")
    private String stylistName;

    @Column(name = "is_active")
    private boolean status;

// todo موجودی رزور ها / اسم ارایشگر /

    @Column(name = "date_of_modified")
    private LocalDateTime dateOfModified;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinTable(
            name = "service_stylist",
            joinColumns = @JoinColumn(name = "stylist_name"),
            inverseJoinColumns = @JoinColumn(name = "family"))
    private Stylist stylist;

    @PrePersist
    public void beforeDateModified(){
        dateOfModified = LocalDateTime.now();
    }


}
