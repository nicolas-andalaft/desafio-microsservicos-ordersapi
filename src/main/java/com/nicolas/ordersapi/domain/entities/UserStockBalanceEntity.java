package com.nicolas.ordersapi.domain.entities;

import java.sql.Timestamp;

public class UserStockBalanceEntity {
    public Long id_user;
    public Long id_stock;
    public String stock_symbol;
    public String stock_name;
    public Long volume;
    public Timestamp created_on;
    public Timestamp updated_on;

    public UserStockBalanceEntity() {}
}
