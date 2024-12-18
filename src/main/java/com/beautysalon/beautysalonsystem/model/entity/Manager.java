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

@Entity(name = "managerEntity")
@Table(name = "manager_tbl")

public class Manager extends Profile {

    @Id
    @SequenceGenerator(name = "managerSeq", sequenceName = "manager_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "managerSeq")
    @Column(name = "id")
    private Long id;

    @Column(name = "address", length = 100)
    @Pattern(regexp = "^[\\w\\s]{2,100}$", message = "invalid Address")
    private String address;

    @Column(name = "national_code", length = 10)
    @Pattern(regexp = "^//d{10}$")
    private String nationalCode;

    @OneToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(
            name = "salon_id",
            foreignKey = @ForeignKey(name = "fk_manager_salon")
    )
    private Salon salon;

//    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
//    @JoinColumn(
//            name = "booking_id",
//            foreignKey = @ForeignKey(name = "fk_manager_booking")
//    )
//    private List<Booking> bookingList;

    @OneToMany(mappedBy = "manager", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<Attachment> attachments;

    public void addAttachment(Attachment attachment) {
        if (attachments == null) {
            attachments = new ArrayList<>();
        }
        attachments.add(attachment);
        attachment.setManager(this);
    }

}

