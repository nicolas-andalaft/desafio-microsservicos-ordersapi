package com.nicolas.ordersapi.domain.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class UserEntity {
    public Long id;
    public String username;
    public BigDecimal dollarBalance;
    public LocalDateTime createdOn;
    public LocalDateTime updatedOn;

    public UserEntity() {}

    public UserEntity(
        Long id,
        String username,
        BigDecimal dollarBalance,
        LocalDateTime createdOn,
        LocalDateTime updatedOn
    ) {
        this.id = id;
        this.username = username;
        this.dollarBalance = dollarBalance;
        this.createdOn = createdOn;
        this.updatedOn = updatedOn;
    }
}
