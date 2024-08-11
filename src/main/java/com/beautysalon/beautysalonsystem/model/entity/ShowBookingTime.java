package com.beautysalon.beautysalonsystem.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@SuperBuilder

@Entity(name = "showBookingTimeEntity")
@Table(name="show_booking_time_tbl")

public class ShowBookingTime extends Base{

    @Id
    @SequenceGenerator(name = "showBookingTimeSeq", sequenceName = "show_booking_time_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "showBookingTimeSeq")
    @Column(name = "id")
    private Long id;

    @Column(name = "stylist_name", length = 30)
    //@Pattern(regexp = "^[a-zA-Z\\d\\S\\._]{3,30}$" ,message = "Invalid username")
    private String name;

    @ManyToOne
    @JoinColumn(name= "services_time_id")
    private Services services;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinTable(
            name = "time_stylist",
            joinColumns = @JoinColumn(name = "stylist_name"),
            inverseJoinColumns = @JoinColumn(name = "family"))
    private Stylist stylist;

    @Column(name = "reservation_date")
    private LocalDateTime reservationDate;

}
