package com.beautysalon.beautysalonsystem.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
@NoArgsConstructor
@Setter
@Getter
@SuperBuilder

@MappedSuperclass
public abstract class Payment extends Base {
    @Column(name = "payment_price")
    private float price;

    @Column(name="paymentDateTime")
    private LocalDateTime paymentDateTime;

    @Column(name="description")
    @Pattern(regexp = "^[a-zA-Z\\s]{3,30}$")
    private String description;
}
