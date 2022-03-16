package com.nicolas.ordersapi.data.models;

import java.util.HashMap;
import java.util.Map;

import com.nicolas.ordersapi.core.IModel;
import com.nicolas.ordersapi.data.utils.MapGetter;
import com.nicolas.ordersapi.domain.entities.UserStockBalanceEntity;

public class UserStockBalanceModel extends UserStockBalanceEntity implements IModel<UserStockBalanceEntity> {

    public UserStockBalanceModel() {}

    public UserStockBalanceModel(UserStockBalanceEntity userStockBalance) {
        this.idUser = userStockBalance.getIdUser();
        this.idStock = userStockBalance.getIdStock();
        this.stockSymbol = userStockBalance.getStockSymbol();
        this.stockName = userStockBalance.getStockName();
        this.volume = userStockBalance.getVolume();
        this.createdOn = userStockBalance.getCreatedOn();
        this.updatedOn = userStockBalance.getUpdatedOn();
    }
    
    public static UserStockBalanceModel fromMap(Map<String, Object> map) {
        UserStockBalanceModel userStockBalance = new UserStockBalanceModel();
        if (map == null) return userStockBalance;
        
        userStockBalance.idUser = MapGetter.getLong(map, "id_user");
        userStockBalance.idStock = MapGetter.getLong(map, "id_stock");
        userStockBalance.stockSymbol = MapGetter.getString(map, "stock_symbol");
        userStockBalance.stockName = MapGetter.getString(map, "stock_name");
        userStockBalance.volume = MapGetter.getLong(map, "volume");
        userStockBalance.createdOn = MapGetter.getTimestamp(map, "created_on");
        userStockBalance.updatedOn = MapGetter.getTimestamp(map, "updated_on");
        
        return userStockBalance;
    }  

    @Override
    public Map<String, Object> toMap(UserStockBalanceEntity userStockBalance) {
        Map<String, Object> map = new HashMap<>();

        map.put("id_user", userStockBalance.getIdUser());
        map.put("id_stock", userStockBalance.getIdStock());
        map.put("stock_symbol", userStockBalance.getStockSymbol());
        map.put("stock_name", userStockBalance.getStockName());
        map.put("volume", userStockBalance.getVolume());
        map.put("created_on", userStockBalance.getCreatedOn());
        map.put("updated_on", userStockBalance.getUpdatedOn());

        return map;
    }
}
