package com.beautysalon.beautysalonsystem.model.entity;

import jakarta.json.bind.annotation.JsonbTransient;
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

@Entity(name = "salonEntity")
@Table(name="salon_tbl")

public class Salon extends Base{
    @Id
    @SequenceGenerator(name = "salonSeq", sequenceName = "salon_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "salonSeq")
    @Column(name = "id")
    private Long id;

    @Column(name = "name", length =30 , nullable = false)
    @Pattern(regexp = "^[a-zA-z\\d\\s]{2,30}$", message = "invalid Name")
    private String name;

    @Column(name = "address", length = 100)
    @Pattern(regexp = "^[\\w\\s]{2,100}$", message = "invalid Address")
    private String address;

    @Column(name = "status")
    private boolean status;

    @Column(name = "description", length = 50)
    private String description;

    @JsonbTransient
    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "salon_services_tbl",
            joinColumns = @JoinColumn(name = "salon_id"),
            inverseJoinColumns = @JoinColumn(name = "services_id"),
            foreignKey = @ForeignKey(name = "fk_salon_services"),
            inverseForeignKey = @ForeignKey(name = "fk_inverse_salon_services")
    )
    private List<Services> servicesList;

    @JsonbTransient
    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "salon_stylist_tbl",
            joinColumns = @JoinColumn(name = "salon_id"),
            inverseJoinColumns = @JoinColumn(name = "stylist_id"),
            foreignKey = @ForeignKey(name = "fk_salon_stylist"),
            inverseForeignKey = @ForeignKey(name = "fk_inverse_salon_stylist")
    )
    private List<Stylist> stylists;

    @JsonbTransient
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "salon_timing_tbl",
            joinColumns = @JoinColumn(name = "salon_id"),
            inverseJoinColumns = @JoinColumn(name = "timing_id"),
            foreignKey = @ForeignKey(name = "fk_salon_timing"),
            inverseForeignKey = @ForeignKey(name = "fk_inverse_salon_timing")
    )
    private List<Timing> timingList;

    @JsonbTransient
    @OneToMany(mappedBy = "salon", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<Attachment> attachments;

    public void addAttachment(Attachment attachment) {
        if (attachments == null) {
            attachments = new ArrayList<>();
        }
        attachments.add(attachment);
        attachment.setSalon(this);
    }

    public void addServices(Services services) {
        if (servicesList == null) {
            servicesList = new ArrayList<>();
        }
        servicesList.add(services);
    }

    public void addTiming(Timing timing){
        if (timingList == null){
            timingList = new ArrayList<>();
        }
        timingList.add(timing);
    }

    public void addStylist(Stylist stylist){
        if (stylists == null){
            stylists = new ArrayList<>();
        }
        stylists.add(stylist);
    }
}
