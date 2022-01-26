package com.nicolas.ordersapi.domain.entities;

import java.time.LocalDateTime;

public class StockEntity {
    public Long id;
    public String stock_symbol;
    public String stock_name;
    public Float ask_min;
    public Float ask_max;
    public Float bid_min;
    public Float bid_max;
    public LocalDateTime created_on;
    public LocalDateTime updated_on;

    public StockEntity() {}
}
