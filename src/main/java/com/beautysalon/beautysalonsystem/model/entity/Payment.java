package com.beautysalon.beautysalonsystem.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
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

@Entity(name = "paymentEntity")
@Table(name = "payment_tbl")
public class Payment extends Base {

    @Id
    @SequenceGenerator(name = "paymentSeq", sequenceName = "payment_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "paymentSeq")
    @Column(name = "id")
    private Long id;

    @Column(name = "payment_price")
    private Double price;

    @Column(name = "payment_date")
    private LocalDateTime paymentDateTime;

    @Column(name = "description", length = 50)
    private String description;

    @OneToOne
    @JoinColumn(
            name = "bank_id",
            foreignKey = @ForeignKey(name = "fk_bank_payment")
    )
    private Bank bank;

    @OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE})
    @JoinTable(name = "payment_booking_tbl",
            joinColumns = @JoinColumn(name = "payment_id"),
            inverseJoinColumns = @JoinColumn(name = "booking_id"),
            foreignKey = @ForeignKey(name = "fk_payment_booking"),
            inverseForeignKey = @ForeignKey(name = "fk_inverse_payment_booking")
    )
    private List<Booking> bookingList;

    @OneToMany(mappedBy = "payment", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<Attachment> attachments;

    public void addAttachment(Attachment attachment) {
        if (attachments == null) {
            attachments = new ArrayList<>();
        }
        attachments.add(attachment);
        attachment.setPayment(this);
    }

}