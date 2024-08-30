package com.beautysalon.beautysalonsystem.model.entity;

import com.beautysalon.beautysalonsystem.model.entity.enums.BookingState;
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

    @Enumerated
    private BookingState status;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;
//
    @Column(name = "date_time")
    private LocalDateTime localDateTime;

    //todo
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name= "services_id")
    private List<Services> servicesList;
//
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(
            name = "time_id",
            foreignKey = @ForeignKey(name = "fk_booking_reserve")
    )
    private Timing timing;

//    public void addItem(Timing timing){
//        if(timingList == null){
//            timingList = new ArrayList<>();
//        }
//        timingList.add(timing);
//    }

}
