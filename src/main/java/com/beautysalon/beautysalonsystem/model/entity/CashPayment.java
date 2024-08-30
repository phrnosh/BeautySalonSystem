
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

@Entity(name = "cashPayment")
@Table(name="cashPayment_tbl")

public class CashPayment extends Payment {
    @Id
    @SequenceGenerator(name = "cashPaymentSeq", sequenceName = "cashPayment_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cashPaymentSeq")
    @Column(name = "id")
    private Long id;

}
