package com.nicolas.ordersapi.data.models;

import java.util.HashMap;
import java.util.Map;

import com.nicolas.ordersapi.core.IModel;
import com.nicolas.ordersapi.data.utils.MapGetter;
import com.nicolas.ordersapi.domain.entities.OrderHistoryEntity;

public class OrderHistoryModel extends OrderHistoryEntity implements IModel<OrderHistoryEntity> {

    public OrderHistoryModel() {}

    public OrderHistoryModel(OrderHistoryEntity orderHistory) {
        this.id = orderHistory.getId();
        this.orderUser = orderHistory.getOrderUser();
        this.matchUser = orderHistory.getMatchUser();
        this.order = orderHistory.getOrder();
        this.match = orderHistory.getMatch();
        this.transactionVolume = orderHistory.getTransactionVolume();
        this.transactionPrice = orderHistory.getTransactionPrice();
        this.status = orderHistory.getStatus();
        this.createdOn = orderHistory.getCreatedOn();
    }

    public static OrderHistoryModel fromMap(Map<String, Object> map) {
        OrderHistoryModel orderHistory = new OrderHistoryModel();
        if (map == null) return orderHistory;
        
        orderHistory.id = MapGetter.getLong(map, "id");
        orderHistory.setOrderUser(MapGetter.getLong(map, "id_order_user"));
        orderHistory.setMatchUser(MapGetter.getLong(map, "id_match_user"));
        orderHistory.setOrder(MapGetter.getLong(map, "id_order"));
        orderHistory.setMatch(MapGetter.getLong(map, "id_match"));
        orderHistory.transactionVolume = MapGetter.getLong(map, "transactional_volume");
        orderHistory.transactionPrice = MapGetter.getBigDecimal(map, "transactional_price");
        orderHistory.status = MapGetter.getInteger(map, "status");
        orderHistory.createdOn = MapGetter.getTimestamp(map, "created_on");

        // Extras
        orderHistory.order.setStockName(MapGetter.getString(map, "stock_name"));
        orderHistory.order.setStockSymbol(MapGetter.getString(map, "stock_symbol"));
        
        return orderHistory;
    }

    @Override
    public Map<String, Object> toMap(OrderHistoryEntity orderHistory) {
        Map<String, Object> map = new HashMap<>();

        map.put("id", orderHistory.getId());
        map.put("order_user", orderHistory.getOrderUser());
        map.put("match_user", orderHistory.getMatchUser());
        map.put("order", orderHistory.getOrder());
        map.put("match", orderHistory.getMatch());
        map.put("transaction_volume", orderHistory.getTransactionVolume());
        map.put("transaction_price", orderHistory.getTransactionPrice());
        map.put("status", orderHistory.getStatus());
        map.put("created_on", orderHistory.getCreatedOn());

        return map;
    }     
}
