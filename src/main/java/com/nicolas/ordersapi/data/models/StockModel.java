package com.nicolas.ordersapi.data.models;

import java.util.Map;

import com.nicolas.ordersapi.data.utils.MapGetter;
import com.nicolas.ordersapi.domain.entities.StockEntity;

import io.vavr.collection.HashMap;

public class StockModel extends StockEntity {

    public StockModel() {}

    public StockModel(StockEntity stock) {
        this.id = stock.id;
        this.stock_symbol = stock.stock_symbol;
        this.stock_name = stock.stock_name;
        this.ask_min = stock.ask_min;
        this.ask_max = stock.ask_max;
        this.bid_min = stock.bid_min;
        this.bid_max = stock.bid_max;
        this.created_on = stock.created_on;
        this.updated_on = stock.updated_on;
    }

    public static StockModel fromMap(Map<String, Object> map) {
        StockModel stock = new StockModel();
        stock.id = MapGetter.getLong(map, "id");
        stock.stock_symbol = MapGetter.getString(map, "stock_symbol");
        stock.stock_name = MapGetter.getString(map, "stock_name");
        stock.ask_min = MapGetter.getFloat(map, "ask_min");
        stock.ask_max = MapGetter.getFloat(map, "ask_max");
        stock.bid_min = MapGetter.getFloat(map, "bid_min");
        stock.bid_max = MapGetter.getFloat(map, "bid_max");
        stock.created_on = MapGetter.getTimestamp(map, "created_on");
        stock.updated_on = MapGetter.getTimestamp(map, "updated_on");
        
        return stock;
    }   
        
    public static Map<String, Object> toMap(StockEntity stock) {
        HashMap<String, Object> map = HashMap.of(
            "id",stock.id,
            "stock_symbol",stock.stock_symbol,
            "stock_name",stock.stock_name,
            "ask_min",stock.ask_min,
            "ask_max",stock.ask_max,
            "bid_min",stock.bid_min,
            "bid_max",stock.bid_max,
            "created_on",stock.created_on,
            "updated_on",stock.updated_on
        );

        return map.toJavaMap();
    }
    
}
