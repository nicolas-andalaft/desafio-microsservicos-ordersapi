package com.nicolas.ordersapi.domain.entities;

import java.time.LocalDateTime;

public class UserStockBalanceEntity {
    public Long id_user;
    public Long id_stock;
    public String stock_symbol;
    public String stock_name;
    public Long volume;
    public LocalDateTime created_on;
    public LocalDateTime updated_on;

    public UserStockBalanceEntity() {}
}
