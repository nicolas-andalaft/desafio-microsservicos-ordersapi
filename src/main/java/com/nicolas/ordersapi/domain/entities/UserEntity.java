package com.nicolas.ordersapi.domain.entities;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class UserEntity {
    public Long id;
    public String username;
    public BigDecimal dollar_balance;
    public Timestamp created_on;
    public Timestamp updated_on;

    public UserEntity() {}
}
