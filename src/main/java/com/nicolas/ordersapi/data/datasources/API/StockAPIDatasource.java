package com.nicolas.ordersapi.data.datasources.API;

import java.util.Map;

import com.nicolas.ordersapi.data.datasources.IStockDatasource;
import com.nicolas.ordersapi.data.models.StockModel;
import com.nicolas.ordersapi.domain.entities.StockEntity;

import io.vavr.control.Either;

public class StockAPIDatasource extends APIDatasource implements IStockDatasource {

    public StockAPIDatasource() {
        super.baseUrl = "http://localhost:8082";
	}

    @Override
    @SuppressWarnings("unchecked")
    public Either<Exception, StockEntity> getStock(StockEntity stock) {
        var response = super.get(super.baseUrl + "/stocks/" + stock.id, false);
        return response.map((map) -> {
            return StockModel.fromMap((Map<String, Object>)map);
        });
    }
    
}