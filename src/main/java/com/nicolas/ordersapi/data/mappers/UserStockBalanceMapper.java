package com.nicolas.ordersapi.data.mappers;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import com.nicolas.ordersapi.data.utils.MapGetter;
import com.nicolas.ordersapi.domain.entities.UserStockBalanceEntity;

public class UserStockBalanceMapper {
    public static UserStockBalanceEntity fromMap(Map<String, Object> map) {
        return new UserStockBalanceEntity(
            MapGetter.getLong(map, "id_user"),
            MapGetter.getLong(map, "id_stock"),
            MapGetter.getString(map, "stock_symbol"),
            MapGetter.getString(map, "stock_name"),
            MapGetter.getLong(map, "volume"),
            MapGetter.getLocalDateTime(map, "created_on"),
            MapGetter.getLocalDateTime(map, "updated_on")
        );
    }

    public static Map<String, Object> toMap(UserStockBalanceEntity userStockBalance) {
        var map = new HashMap<String, Object>();
        map.put("id_user", userStockBalance.idUser);
        map.put("id_stock", userStockBalance.idStock);
        map.put("stock_symbol", userStockBalance.stockSymbol);
        map.put("stock_name", userStockBalance.stockName);
        map.put("volume", userStockBalance.volume);
        map.put("created_on", userStockBalance.createdOn);
        map.put("updated_on", userStockBalance.updatedOn);

        return map;
    }
}
