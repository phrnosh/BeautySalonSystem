package com.beautysalon.beautysalonsystem.model.entity;

import com.beautysalon.beautysalonsystem.model.entity.enums.FileType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
@SuperBuilder

@Entity(name = "attachmentEntity")
@Table(name="attachment_tbl")

public class Attachment {
    @Id
    @SequenceGenerator(name = "attachmentSeq", sequenceName = "attachment_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "attachmentSeq")
    private Long id;

    @Column(name="file_name", length = 50)
    private String filename;

    @Enumerated(EnumType.ORDINAL)
    @Column(name ="file_type")
    private FileType fileType;

    @Column(name="file_size")
    private Long fileSize;

    @Column(name="attach_date_time")
    private LocalDateTime localDateTime;

    @Column(name="description", length = 100)
    private String description;

    @ManyToOne
    @JoinColumn(name = "manager_id", nullable = true, foreignKey = @ForeignKey(name = "fk_attachment_manager"))
    private Manager manager;

    @ManyToOne
    @JoinColumn(name = "admin_id", nullable = true, foreignKey = @ForeignKey(name = "fk_attachment_admin"))
    private Admin admin;

//    @ManyToOne
//    @JoinColumn(name = "message_id", nullable = true, foreignKey = @ForeignKey(name = "fk_attachment_message"))
//    private Message message;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = true, foreignKey = @ForeignKey(name = "fk_attachment_customer"))
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "salon_id", nullable = true, foreignKey = @ForeignKey(name = "fk_attachment_salon"))
    private Salon salon;

    @ManyToOne
    @JoinColumn(name = "services_id", nullable = true, foreignKey = @ForeignKey(name = "fk_attachment_services"))
    private Services services;

    @ManyToOne
    @JoinColumn(name = "booking_id", nullable = true, foreignKey = @ForeignKey(name = "fk_attachment_booking"))
    private Booking booking;

    @ManyToOne
    @JoinColumn(name = "payment_id", nullable = true, foreignKey = @ForeignKey(name = "fk_attachment_payment"))
    private Payment payment;
}
