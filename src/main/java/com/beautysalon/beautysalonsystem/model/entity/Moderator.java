package com.beautysalon.beautysalonsystem.model.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
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

@Entity(name = "moderatorEntity")
@Table(name = "moderator_tbl")
public class Moderator extends Profile{

    @Id
    @SequenceGenerator(name = "moderatorSeq", sequenceName = "moderator_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "moderatorSeq")
    @Column(name = "id")
    private Long id;

    @Column(name = "national_code", length = 10)
    @Pattern(regexp = "^\\d{10}$" ,message = "Invalid National Code")
    private String nationalCode;

    @Column(name = "address", length = 100)
//    @Pattern(regexp = "^[\\w\\s]{2,100}$", message = "invalid Address")
    private String address;


    @OneToMany(mappedBy = "moderator", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<Attachment> attachments;

    public void addAttachment(Attachment attachment) {
        if (attachments == null) {
            attachments = new ArrayList<>();
        }
        attachments.add(attachment);
        attachment.setModerator(this);
    }

    @OneToOne
    private Role role ;
}
