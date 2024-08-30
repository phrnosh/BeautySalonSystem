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

@Entity(name = "cardPayment")
@Table(name="cardPayment_tbl")

public class CardPayment extends Payment {
    @Id
    @SequenceGenerator(name = "cardPaymentSeq", sequenceName = "cardPayment_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cardPaymentSeq")
    @Column(name = "id")
    private Long id;

    @Column(name="bank_name")
    private  String bankName;

    @Column(name = "cardNumber" ,length = 20, unique = true)
    private int cardNumber;



}


