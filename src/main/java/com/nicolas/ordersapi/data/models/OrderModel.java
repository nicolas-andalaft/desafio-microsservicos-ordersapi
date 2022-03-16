package com.nicolas.ordersapi.data.models;

import java.util.HashMap;
import java.util.Map;

import com.nicolas.ordersapi.core.IModel;
import com.nicolas.ordersapi.data.utils.MapGetter;
import com.nicolas.ordersapi.domain.entities.OrderEntity;

public class OrderModel extends OrderEntity implements IModel<OrderEntity> {

    public OrderModel() {}

    public OrderModel(OrderEntity order) {
        this.id = order.getId();
        this.idUser = order.getIdUser();
        this.idStock = order.getIdStock();
        this.stockSymbol = order.getStockSymbol();
        this.stockName = order.getStockName();
        this.volume = order.getVolume();
        this.price = order.getPrice();
        this.type = order.getType();
        this.status = order.getStatus();
        this.createdOn = order.getCreatedOn();
        this.updatedOn = order.getUpdatedOn();
    }

    public static OrderModel fromMap(Map<String, Object> map) {
        OrderModel userOrder = new OrderModel();
        if (map == null) return userOrder;
        
        userOrder.id = MapGetter.getLong(map, "id");
        userOrder.idUser = MapGetter.getLong(map, "id_user");
        userOrder.idStock = MapGetter.getLong(map, "id_stock");
        userOrder.stockSymbol = MapGetter.getString(map, "stock_symbol");
        userOrder.stockName = MapGetter.getString(map, "stock_name");
        userOrder.volume = MapGetter.getLong(map, "volume");
        userOrder.price = MapGetter.getBigDecimal(map, "price");
        userOrder.type = MapGetter.getInteger(map, "type");
        userOrder.status = MapGetter.getInteger(map, "status");
        userOrder.createdOn = MapGetter.getTimestamp(map, "created_on");
        userOrder.updatedOn = MapGetter.getTimestamp(map, "updated_on");
        
        return userOrder;
    } 

    @Override
    public Map<String, Object> toMap(OrderEntity order) {
        Map<String, Object> map = new HashMap<>();

        map.put("id", order.getId());
        map.put("id_user", order.getIdUser());
        map.put("id_stock", order.getIdStock());
        map.put("stock_symbol", order.getStockSymbol());
        map.put("price", order.getPrice());
        map.put("type", order.getType());
        map.put("status", order.getStatus());
        map.put("created_on", order.getCreatedOn());
        map.put("updated_on", order.getUpdatedOn());

        return map;
    }
}
