package com.nicolas.ordersapi.data.datasources.API;

import com.nicolas.ordersapi.data.datasources.IStockDatasource;
import com.nicolas.ordersapi.data.models.StockModel;
import com.nicolas.ordersapi.domain.entities.StockEntity;

import io.vavr.control.Either;

public class StockAPIDatasource extends APIDatasource implements IStockDatasource {

    public StockAPIDatasource() {
        super.baseUrl = "http://localhost:8082";
	}

    @Override
    public Either<Exception, StockModel> getStock(StockEntity stock) {
        var response = super.get(super.baseUrl + "/stocks");
        return response.map((e) -> {
            return StockModel.fromMap(e[0]);
        });
    }
    
}