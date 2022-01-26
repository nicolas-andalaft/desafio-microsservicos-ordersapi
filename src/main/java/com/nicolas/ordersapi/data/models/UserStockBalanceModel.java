package com.nicolas.ordersapi.data.models;

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
        userStockBalance.id_user = MapGetter.getLong(map, "id_user");
        userStockBalance.id_stock = MapGetter.getLong(map, "id_stock");
        userStockBalance.stock_symbol = MapGetter.getString(map, "stock_symbol");
        userStockBalance.stock_name = MapGetter.getString(map, "stock_name");
        userStockBalance.volume = MapGetter.getLong(map, "volume");
        userStockBalance.created_on = MapGetter.getLocalDateTime(map, "createdOn");
        userStockBalance.updated_on = MapGetter.getLocalDateTime(map, "updatedOn");
        
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
