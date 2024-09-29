package com.beautysalon.beautysalonsystem.model.entity;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
@ToString

public class PaymentVO {

    private Long id;

    private String customerPhoneNumber;

    private double price;

    private String paymentDateTime;

    private String description;

    private String bankName;

    private String accountNumber;

    private List<Long> bookingIds = new ArrayList<>();

    private int bookingCounts;

    private String imageUrl;

    public PaymentVO(Payment payment){
        this.id = payment.getId();
        this.price = payment.getPrice();
        this.paymentDateTime = payment.getPaymentDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        this.description = payment.getDescription();
        this.bankName = payment.getBank().getName();
        this.accountNumber = payment.getBank().getAccountNumber();
        this.customerPhoneNumber = payment.getBookingList().get(0).getCustomer().getPhoneNumber();

        for (Booking booking : payment.getBookingList()){
            this.bookingIds.add(booking.getId());
        }

        this.bookingCounts = bookingIds.size();

        if (payment.getAttachments().isEmpty()) {
            this.imageUrl = "";
        } else {
            this.imageUrl = payment.getAttachments().get(0).getFilename();
        }

    }

}