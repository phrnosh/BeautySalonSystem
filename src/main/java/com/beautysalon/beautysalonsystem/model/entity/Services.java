package com.beautysalon.beautysalonsystem.model.entity;

import com.beautysalon.beautysalonsystem.model.entity.enums.ServicesType;
import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
@ToString

@Entity(name = "servicesEntity")
@Table(name="services_tbl")

public class Services extends Base{

    @Id
    @SequenceGenerator(name = "servicesSeq", sequenceName = "services_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "servicesSeq")
    @Column(name = "id")
    private Long id;

    @Column(name = "name", length = 60)
//    @Pattern(regexp = "^[a-zA-Z\\s\\d]{3,60}$", message = "Invalid Name")
    private String name;

    @Column(name = "description",length = 100)
    private String description;

    @Column(name = "stylist_name", length = 30)
    //@Pattern(regexp = "^[a-zA-Z\\d\\S\\._]{3,30}$" ,message = "Invalid username")
    private String stylistName;

    @Column(name = "services_type")
    @Enumerated(EnumType.ORDINAL)
    private ServicesType servicesType;

    @Column(name = "is_active")
    private boolean status;

    @Column(name = "available")
    private boolean available = false;

    @Column(name = "date_of_modified")
    private LocalDateTime dateOfModified;

    @JsonbTransient
    @OneToMany(mappedBy = "services", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<Attachment> attachments;

    public void addAttachment(Attachment attachment) {
        if (attachments == null) {
            attachments = new ArrayList<>();
        }
        attachments.add(attachment);
        attachment.setServices(this);
    }
//    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
//    @JoinTable(
//            name = "service_stylist",
//            joinColumns = @JoinColumn(name = "stylist_name"),
//            inverseJoinColumns = @JoinColumn(name = "family"))
//    private Stylist stylist;

    @PrePersist
    public void beforeDateModified(){
        dateOfModified = LocalDateTime.now();
    }


}
