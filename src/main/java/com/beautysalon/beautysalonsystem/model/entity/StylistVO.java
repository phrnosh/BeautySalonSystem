package com.beautysalon.beautysalonsystem.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
@ToString


public class StylistVO {

    private Long id;

    private String name;

    private String family;

    private String phoneNumber;

    private String email;

    private String nationalCode;

    private String address;

    private String career;

    private User user;

    private String imageUrl;


    public StylistVO(Stylist stylist){
        this.id = stylist.getId();
        this.name = stylist.getName();
        this.family = stylist.getFamily();
        this.phoneNumber = stylist.getPhoneNumber();
        this.email = stylist.getEmail();
        this.nationalCode = stylist.getNationalCode();
        this.address = stylist.getAddress();
        this.user = stylist.getUser();
        this.career = stylist.getCareer();

        if (stylist.getAttachments().isEmpty()) {
            this.imageUrl = "";
        } else {
            this.imageUrl = stylist.getAttachments().get(0).getFileName();
        }

    }

}