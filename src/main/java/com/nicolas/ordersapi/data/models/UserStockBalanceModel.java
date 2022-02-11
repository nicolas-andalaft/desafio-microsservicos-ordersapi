package com.nicolas.ordersapi.data.models;

import java.sql.Timestamp;
import java.util.Map;

import com.nicolas.ordersapi.data.utils.MapGetter;
import com.nicolas.ordersapi.domain.entities.UserStockBalanceEntity;

import io.vavr.collection.HashMap;

public class UserStockBalanceModel extends UserStockBalanceEntity {

    public UserStockBalanceModel() {}

    public UserStockBalanceModel(UserStockBalanceEntity userStockBalance) {
        this.id_user = userStockBalance.id_user;
        this.id_stock = userStockBalance.id_stock;
        this.stock_symbol = userStockBalance.stock_symbol;
        this.stock_name = userStockBalance.stock_name;
        this.volume = userStockBalance.volume;
        this.created_on = userStockBalance.created_on;
        this.updated_on = userStockBalance.updated_on;
    }
    
    public static UserStockBalanceModel fromMap(Map<String, Object> map) {
        UserStockBalanceModel userStockBalance = new UserStockBalanceModel();
        userStockBalance.id_user = MapGetter.parse(map, "id_user", Long.class);
        userStockBalance.id_stock = MapGetter.parse(map, "id_stock", Long.class);
        userStockBalance.stock_symbol = MapGetter.parse(map, "stock_symbol", String.class);
        userStockBalance.stock_name = MapGetter.parse(map, "stock_name", String.class);
        userStockBalance.volume = MapGetter.parse(map, "volume", Long.class);
        userStockBalance.created_on = MapGetter.parse(map, "createdOn", Timestamp.class);
        userStockBalance.updated_on = MapGetter.parse(map, "updatedOn", Timestamp.class);
        
        return userStockBalance;
    }   
        
    public static Map<String, Object> toMap(UserStockBalanceEntity userStockBalance) {
        HashMap<String, Object> map = HashMap.of(
            "id_user", userStockBalance.id_user,
            "id_stock", userStockBalance.id_stock,
            "stock_symbol", userStockBalance.stock_symbol,
            "stock_name", userStockBalance.stock_name,
            "volume", userStockBalance.volume,
            "created_on", userStockBalance.created_on,
            "updated_on", userStockBalance.updated_on
        );

        return map.toJavaMap();
    }
}
