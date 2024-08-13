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

@Entity(name = "cancelBooking")
@Table(name="cancelBooking_tbl")

public class CancelBooking extends Base {
    @Id
    @SequenceGenerator(name = "cancelBookingSeq", sequenceName = "cancelBooking_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cancelBookingSeq")
    @Column(name = "id")
    private Long id;

    @OneToOne
    private Booking booking ;

    @OneToOne
    private CheckPayment CheckPayment;



}
