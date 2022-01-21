package com.nicolas.ordersapi.domain.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class UserEntity {
    public Long id;
    public String username;
    public BigDecimal dollar_balance;
    public LocalDateTime created_on;
    public LocalDateTime updated_on;

    public UserEntity() {}
}
