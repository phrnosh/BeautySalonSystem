package com.beautysalon.beautysalonsystem.model.entity;

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

public class TimingVO {

    private Long id;

    private String servicesName;

    private String stylistName;

    private int remainingCapacity;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private boolean status;

    private String description;

    private String servicesDescription;

    private String servicesImage;

    public TimingVO(Timing timing){

        this.id = timing.getId();
        this.servicesName = timing.getServices().getName();
        this.stylistName = timing.getServices().getStylistName();
        this.remainingCapacity = timing.getRemainingCapacity();
        this.startTime = timing.getStartTime();
        this.endTime = timing.getEndTime();
        this.status = timing.isStatus();
        this.description = timing.getDescription();
        this.servicesDescription = timing.getServices().getDescription();

        if (!timing.getServices().getAttachments().isEmpty()){
            this.servicesImage = timing.getServices().getAttachments().get(0).getFilename();
        } else {
            this.servicesImage = "";
        }
    }

}