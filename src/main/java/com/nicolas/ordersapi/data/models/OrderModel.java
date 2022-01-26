package com.nicolas.ordersapi.data.models;

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
        userOrder.id = MapGetter.getLong(map, "id");
        userOrder.id_user = MapGetter.getLong(map, "id_user");
        userOrder.id_stock = MapGetter.getLong(map, "id_stock");
        userOrder.stock_symbol = MapGetter.getString(map, "stock_symbol");
        userOrder.stock_name = MapGetter.getString(map, "stock_name");
        userOrder.volume = MapGetter.getLong(map, "volume");
        userOrder.price = MapGetter.getBigDecimal(map, "price");
        userOrder.type = MapGetter.getInt(map, "type");
        userOrder.status = MapGetter.getInt(map, "status");
        userOrder.created_on = MapGetter.getLocalDateTime(map, "createdOn");
        userOrder.updated_on = MapGetter.getLocalDateTime(map, "updatedOn");
        
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
