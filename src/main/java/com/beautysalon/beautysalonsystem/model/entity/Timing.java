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
@ToString

@Entity(name = "timingEntity")
@Table(name="timing_tbl")
//@NamedQueries(@NamedQuery(
//        name="validTiming", query = ""
//))
public class Timing extends Base{

    @Id
    @SequenceGenerator(name = "timingSeq", sequenceName = "timing_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "timingSeq")
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(
            name = "services_id",
            foreignKey = @ForeignKey(name = "fk_timing_services")
    )
    private Services services;

    @Column(name = "remaining_capacity")
    private int remainingCapacity;

    @Column(name = "start_time")
    private LocalDateTime startTime;

    @Column(name = "end_time")
    private LocalDateTime endTime;

    @Column(name = "status")
    private boolean status;

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
