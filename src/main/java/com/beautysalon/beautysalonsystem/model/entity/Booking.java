package com.beautysalon.beautysalonsystem.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@SuperBuilder

@Entity(name = "bookingEntity")
@Table(name="booking_tbl")

public class Booking extends Base{

    @Id
    @SequenceGenerator(name = "bookingSeq", sequenceName = "booking_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bookingSeq")
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    // todo تاریخ رزور
    @Column(name = "date_time")
    private LocalDateTime localDateTime;

    @OneToMany(mappedBy = "services", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name= "services_id")
    private List<Services> servicesList;

    public void addItem(Services services){
        if(servicesList == null){
            servicesList = new ArrayList<>();
        }
        servicesList.add(services);
    }

}
