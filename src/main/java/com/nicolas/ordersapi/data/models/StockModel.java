package com.nicolas.ordersapi.data.models;

import java.util.HashMap;
import java.util.Map;

import com.nicolas.ordersapi.core.IModel;
import com.nicolas.ordersapi.data.utils.MapGetter;
import com.nicolas.ordersapi.domain.entities.StockEntity;

public class StockModel extends StockEntity implements IModel<StockEntity> {

    public StockModel() {}

    public StockModel(StockEntity stock) {
        this.id= stock.getId();
        this.stockSymbol= stock.getStockSymbol();
        this.stockName= stock.getStockName();
        this.askMin= stock.getAskMin();
        this.askMax= stock.getAskMax();
        this.bidMin= stock.getBidMin();
        this.bidMax= stock.getBidMax();
        this.createdOn= stock.getCreatedOn();
        this.updatedOn= stock.getUpdatedOn();
    }

    public static StockModel fromMap(Map<String, Object> map) {
        StockModel stock = new StockModel();
        if (map == null) return stock;

        stock.id = MapGetter.getLong(map, "id");
        stock.stockSymbol = MapGetter.getString(map, "stock_symbol");
        stock.stockName = MapGetter.getString(map, "stock_name");
        stock.askMin = MapGetter.getFloat(map, "ask_min");
        stock.askMax = MapGetter.getFloat(map, "ask_max");
        stock.bidMin = MapGetter.getFloat(map, "bid_min");
        stock.bidMax = MapGetter.getFloat(map, "bid_max");
        stock.createdOn = MapGetter.getTimestamp(map, "created_on");
        stock.updatedOn = MapGetter.getTimestamp(map, "updated_on");
        
        return stock;
    }   

    @Override
    public Map<String, Object> toMap(StockEntity stock) {
        Map<String, Object> map = new HashMap<>();

        map.put("id", stock.getId()); 
        map.put("stock_symbol", stock.getStockSymbol()); 
        map.put("stock_name", stock.getStockName()); 
        map.put("ask_min", stock.getAskMin()); 
        map.put("ask_max", stock.getAskMax()); 
        map.put("bid_min", stock.getBidMin()); 
        map.put("bid_max", stock.getBidMax()); 
        map.put("created_on", stock.getCreatedOn()); 
        map.put("updated_on", stock.getUpdatedOn()); 

        return map;
    }
}
