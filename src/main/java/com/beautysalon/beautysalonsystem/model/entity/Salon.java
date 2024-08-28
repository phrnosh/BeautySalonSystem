package com.beautysalon.beautysalonsystem.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@SuperBuilder

@Entity(name = "salonEntity")
@Table(name="salon_tbl")

public class Salon extends Base{
    @Id
    @SequenceGenerator(name = "salonSeq", sequenceName = "salon_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "salonSeq")
    @Column(name = "id")
    private Long id;

    @Column(name = "name", length =30 , nullable = false )
    private String name;

    @Column(name = "address", length = 100)
    private String address;

    @Column(name = "description", length = 50)
    private String description;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    private List<Attachment> attachments;

    public void addAttachment(Attachment attachment) {
        if (attachments == null) {
            attachments = new ArrayList<>();
        }
        attachments.add(attachment);
    }
}
