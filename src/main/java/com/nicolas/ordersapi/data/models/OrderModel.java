package com.nicolas.ordersapi.data.models;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Map;
import com.nicolas.ordersapi.data.utils.MapGetter;
import com.nicolas.ordersapi.domain.entities.OrderEntity;

import io.vavr.collection.HashMap;

public class OrderModel extends OrderEntity {

    public OrderModel() {}

    public OrderModel(OrderEntity order) {
        this.id = order.id;
        this.id_user = order.id_user;
        this.id_stock = order.id_stock;
        this.stock_symbol = order.stock_symbol;
        this.stock_name = order.stock_name;
        this.volume = order.volume;
        this.price = order.price;
        this.type = order.type;
        this.status = order.status;
        this.created_on = order.created_on;
        this.updated_on = order.updated_on;
    }

    public static OrderModel fromMap(Map<String, Object> map) {
        OrderModel userOrder = new OrderModel();
        
        userOrder.id = MapGetter.parse(map, "id", Long.class);
        userOrder.id_user = MapGetter.parse(map, "id_user", Long.class);
        userOrder.id_stock = MapGetter.parse(map, "id_stock", Long.class);
        userOrder.stock_symbol = MapGetter.parse(map, "stock_symbol", String.class);
        userOrder.stock_name = MapGetter.parse(map, "stock_name", String.class);
        userOrder.volume = MapGetter.parse(map, "volume", Long.class);
        userOrder.price = MapGetter.parse(map, "price", BigDecimal.class);
        userOrder.type = MapGetter.parse(map, "type", Integer.class);
        userOrder.status = MapGetter.parse(map, "status", Integer.class);
        userOrder.created_on = MapGetter.parse(map, "created_on", Timestamp.class);
        userOrder.updated_on = MapGetter.parse(map, "updated_on", Timestamp.class);
        
        return userOrder;
    }   
        
    public static Map<String, Object> toMap(OrderEntity userOrder) {
        HashMap<String, Object> map = HashMap.of(
            "id", userOrder.id,
            "id_user", userOrder.id_user,
            "id_stock", userOrder.id_stock,
            "stock_symbol", userOrder.stock_symbol,
            "stock_name", userOrder.stock_name,
            "volume", userOrder.volume,
            "price", userOrder.price,
            "type", userOrder.type,
            "status", userOrder.status,
            "created_on", userOrder.created_on
        );
        map.put("updated_on", userOrder.updated_on);

        return map.toJavaMap();
    }
}
