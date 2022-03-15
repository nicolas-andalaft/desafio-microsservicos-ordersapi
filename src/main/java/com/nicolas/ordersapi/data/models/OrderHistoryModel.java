package com.nicolas.ordersapi.data.models;

import java.util.Map;

import com.nicolas.ordersapi.data.utils.MapGetter;
import com.nicolas.ordersapi.domain.entities.OrderHistoryEntity;

public class OrderHistoryModel extends OrderHistoryEntity {
    public OrderHistoryModel() {}

    public OrderHistoryModel(OrderHistoryEntity orderHistory) {
        this.id = orderHistory.id;
        this.order_user = orderHistory.order_user;
        this.match_user = orderHistory.match_user;
        this.order = orderHistory.order;
        this.match = orderHistory.match;
        this.transaction_volume = orderHistory.transaction_volume;
        this.transaction_price = orderHistory.transaction_price;
        this.status = orderHistory.status;
        this.created_on = orderHistory.created_on;
    }

    public static OrderHistoryModel fromMap(Map<String, Object> map) {
        OrderHistoryModel orderHistory = new OrderHistoryModel();
        
        orderHistory.id = MapGetter.getLong(map, "id");
        orderHistory.order_user(MapGetter.getLong(map, "id_order_user"));
        orderHistory.match_user(MapGetter.getLong(map, "id_match_user"));
        orderHistory.order(MapGetter.getLong(map, "id_order"));
        orderHistory.match(MapGetter.getLong(map, "id_match"));
        orderHistory.transaction_volume = MapGetter.getLong(map, "transactional_volume");
        orderHistory.transaction_price = MapGetter.getBigDecimal(map, "transactional_price");
        orderHistory.status = MapGetter.getInteger(map, "status");
        orderHistory.created_on = MapGetter.getTimestamp(map, "created_on");

        // Extras
        orderHistory.order.stock_name = MapGetter.getString(map, "stock_name");
        orderHistory.order.stock_symbol = MapGetter.getString(map, "stock_symbol");
        
        return orderHistory;
    }     
}
