package com.beautysalon.beautysalonsystem.model.entity;

import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
@SuperBuilder

@Entity(name = "reserveEntity")
@Table(name="reserve_tbl")

public class Timing extends Base{

    @Id
    @SequenceGenerator(name = "reserveSeq", sequenceName = "reserve_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "reserveSeq")
    @Column(name = "id")
    private Long id;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "reserve_stylist")
    private Stylist stylist;

    @ManyToOne
    @JoinColumn(
            name = "services_id",
            foreignKey = @ForeignKey(name = "fk_reserve_service")
    )
    private Services services;

    @Column(name = "start_time")
    private LocalDateTime startTime;

    @Column(name = "end_time")
    private LocalDateTime endTime;

    @Column(name = "description", length = 50)
    private String description;

    @JsonbTransient
    @ToString.Exclude
    @ManyToOne
    @JoinColumn(
            name = "salon_id",
            foreignKey = @ForeignKey(name = "fk_time_salon")
    )
    private Salon salon;

}
