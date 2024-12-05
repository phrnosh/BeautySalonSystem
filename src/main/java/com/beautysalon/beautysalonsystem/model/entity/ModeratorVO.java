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


public class ModeratorVO {

    private Long id;

    private String name;

    private String family;

    private String phoneNumber;

    private String email;

    private String nationalCode;

    private String address;

    private String username;

    private String password;

    private String imageUrl;


    public ModeratorVO(Moderator moderator){
        this.id = moderator.getId();
        this.name = moderator.getName();
        this.family = moderator.getFamily();
        this.phoneNumber = moderator.getPhoneNumber();
        this.email = moderator.getEmail();
        this.nationalCode = moderator.getNationalCode();
        this.address = moderator.getAddress();
        this.username = moderator.getUser().getUsername();
        this.password = moderator.getUser().getPassword();

        if (moderator.getAttachments().isEmpty()) {
            this.imageUrl = "";
        } else {
            this.imageUrl = moderator.getAttachments().get(0).getFileName();
        }

    }
}