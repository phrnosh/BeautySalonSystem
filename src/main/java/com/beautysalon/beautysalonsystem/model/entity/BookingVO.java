package com.beautysalon.beautysalonsystem.model.entity;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.time.LocalTime;

@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
@ToString

public class BookingVO {

    private Long id;

    private String customerName;

    private String customerFamily;

    private String customerPhoneNumber;

    private String issueTime;

    private LocalDate servicesDate;

    private LocalTime startHour;

    private LocalTime endHour;
//    private double price;
    private String salonName;

    private String address;

    private String servicesName;

    private String description;

    private boolean verified = false;

    private String imageUrl;

    private String qrCode;


    public BookingVO(Booking booking){
        this.id = booking.getId();
        this.customerName = booking.getCustomer().getName();
        this.customerFamily = booking.getCustomer().getFamily();
        this.customerPhoneNumber = booking.getCustomer().getPhoneNumber();
        this.issueTime = booking.getIssueTime().toLocalDate().toString() + "      " + booking.getIssueTime().getHour() + ":" + booking.getIssueTime().getMinute();
        this.servicesDate = booking.getTiming().getStartTime().toLocalDate();
        this.startHour = booking.getTiming().getStartTime().toLocalTime();
        this.endHour = booking.getTiming().getEndTime().toLocalTime();
//        this.price = booking.getPrice();
        this.salonName = booking.getTiming().getSalon().getName();
        this.address = booking.getTiming().getSalon().getAddress();
        this.servicesName = booking.getTiming().getServices().getName();

        String showDescription = booking.getTiming().getServices().getDescription();
        if (showDescription.length() > 50){
            showDescription = showDescription.substring(0, 50) + "...";
        }
        this.description = booking.getTiming().getDescription() + showDescription;

        if (booking.getAttachments().isEmpty()) {
            this.qrCode = "";
        } else {
            this.qrCode = booking.getAttachments().get(0).getFileName();
        }

        if (booking.getTiming().getServices().getAttachments().isEmpty()){
            this.imageUrl = "";
        } else {
            this.imageUrl = booking.getTiming().getServices().getAttachments().get(0).getFileName();
        }

//        if (booking.getPayment() != null){
//            this.verified = true;
//        }

    }

}