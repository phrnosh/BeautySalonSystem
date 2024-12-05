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

public class SalonVO {

    private Long id;

    private String name;

    private String address;

    private boolean status;

    private String description;

    private String imageUrl;

    public SalonVO(Salon salon) {
        this.id = salon.getId();
        this.name = salon.getName();
        this.address = salon.getAddress();
        this.status = salon.isStatus();
        this.description = salon.getDescription();

        if (salon.getAttachments().isEmpty()) {
            this.imageUrl = "";
        } else {
            this.imageUrl = salon.getAttachments().get(0).getFileName();
        }
    }

}