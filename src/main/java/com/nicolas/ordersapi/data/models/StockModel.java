package com.nicolas.ordersapi.data.models;

import java.sql.Timestamp;
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
        stock.id = MapGetter.parse(map, "id", Long.class);
        stock.stock_symbol = MapGetter.parse(map, "stock_symbol", String.class);
        stock.stock_name = MapGetter.parse(map, "stock_name", String.class);
        stock.ask_min = MapGetter.parse(map, "ask_min", Float.class);
        stock.ask_max = MapGetter.parse(map, "ask_max", Float.class);
        stock.bid_min = MapGetter.parse(map, "bid_min", Float.class);
        stock.bid_max = MapGetter.parse(map, "bid_max", Float.class);
        stock.created_on = MapGetter.parse(map, "created_on", Timestamp.class);
        stock.updated_on = MapGetter.parse(map, "updated_on", Timestamp.class);
        
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
