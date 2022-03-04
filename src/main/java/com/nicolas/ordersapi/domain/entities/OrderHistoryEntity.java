package com.nicolas.ordersapi.domain.entities;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Map;

import com.nicolas.ordersapi.data.utils.MapGetter;

public class OrderHistoryEntity {
        
    public Long id;
    public Long id_order;
    public Long id_match_order;
    public Long match_volume;
    public BigDecimal match_price;
    public Integer order_type;
    public Timestamp created_on;

    public OrderHistoryEntity() {}  
    
    public static OrderHistoryEntity fromMap(Map<String, Object> map) {
        var ordersHistory = new OrderHistoryEntity();

        ordersHistory.id = MapGetter.getLong(map, "id");
        ordersHistory.id_order = MapGetter.getLong(map, "id_order");
        ordersHistory.id_match_order = MapGetter.getLong(map, "id_match_order");
        ordersHistory.match_volume = MapGetter.getLong(map, "match_volume");
        ordersHistory.match_price = MapGetter.getBigDecimal(map, "match_price");
        ordersHistory.order_type = MapGetter.getInteger(map, "order_type");
        ordersHistory.created_on = MapGetter.getTimestamp(map, "created_on");

        return ordersHistory;
    }
}
