package com.beautysalon.beautysalonsystem.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
@ToString


public class CustomerVO {

    private Long id;

    private String name;

    private String family;

    private String username;

    private String password;

    private String phoneNumber;

    private String email;

    private Long timingId;


    public CustomerVO(Customer customer) {
        this.id = customer.getId();
        this.name = customer.getName();
        this.family = customer.getFamily();
        this.username = customer.getUser().getUsername();
        this.password = customer.getUser().getPassword();
        this.phoneNumber = customer.getPhoneNumber();
        this.email = customer.getEmail();
        this.timingId = customer.getTicket().getId();

    }

}