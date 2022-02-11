package com.nicolas.ordersapi.domain.entities;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class OrderEntity {
    public Long id;
    public Long id_user;
    public Long id_stock;
    public String stock_symbol;
    public String stock_name;
    public Long volume;
    public BigDecimal price;
    public Integer type;
    public Integer status;
    public Timestamp created_on;
    public Timestamp updated_on;

    public OrderEntity() {}
}
