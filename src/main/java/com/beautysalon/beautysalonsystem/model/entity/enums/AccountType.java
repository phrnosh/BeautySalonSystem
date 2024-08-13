package com.beautysalon.beautysalonsystem.model.entity.enums;

import lombok.Getter;

@Getter
public enum AccountType {
    savings("Savings account"),
    current("current account");

    private final String title;

    AccountType(String title) {
        this.title = title;
    }
}
