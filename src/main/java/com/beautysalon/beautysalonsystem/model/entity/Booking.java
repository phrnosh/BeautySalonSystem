package com.beautysalon.beautysalonsystem.model.entity;

import com.beautysalon.beautysalonsystem.model.entity.enums.BookingState;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
@ToString

@Entity(name = "bookingEntity")
@Table(name="booking_tbl")

public class Booking extends Base{

    @Id
    @SequenceGenerator(name = "bookingSeq", sequenceName = "booking_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bookingSeq")
    @Column(name = "id")
    private Long id;

//    @Enumerated
//    private BookingState status;

    @ManyToOne
    @JoinColumn(
            name = "customer_id",
            foreignKey = @ForeignKey(name = "fk_booking_customer")
    )
    private Customer customer;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(
            name = "timing_id",
            foreignKey = @ForeignKey(name = "fk_booking_timing")
    )
    private Timing timing;

    @Column(name = "issue_time")
    private LocalDateTime issueTime;

    @Column(name = "reserved")
    private boolean reserved;

//    @ManyToOne
//    @JoinColumn(name = "payment_id",
//        foreignKey = @ForeignKey(name = "fk_booking_payment")
//    )
//    private Payment payment;

    @OneToMany(mappedBy = "booking", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<Attachment> attachments;

    public void addAttachment(Attachment attachment) {
        if (attachments == null) {
            attachments = new ArrayList<>();
        }
        attachments.add(attachment);
        attachment.setBooking(this);
    }


//    //todo
//    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
//    @JoinColumn(name= "services_id")
//    private List<Services> servicesList;


}
