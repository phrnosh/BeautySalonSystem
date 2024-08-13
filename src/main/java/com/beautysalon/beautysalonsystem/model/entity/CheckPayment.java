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

@Entity(name = "checkPayment")
@Table(name = "checkPayment_tbl")


public class CheckPayment extends Payment {
    @Id
    @SequenceGenerator(name = "checkPaymentSeq", sequenceName = "checkPayment_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "checkPaymentSeq")
    @Column(name = "id")
    private Long id;

    @Column(name = "checkNumber", length = 20, unique = true)
    private Long checkNumber;


}
