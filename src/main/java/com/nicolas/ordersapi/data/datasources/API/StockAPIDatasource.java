package com.nicolas.ordersapi.data.datasources.API;

import com.nicolas.ordersapi.data.datasources.IStockDatasource;
import com.nicolas.ordersapi.data.models.StockModel;
import com.nicolas.ordersapi.domain.entities.OrderEntity;
import com.nicolas.ordersapi.domain.entities.StockEntity;

import io.vavr.collection.List;
import io.vavr.control.Either;

public class StockAPIDatasource extends APIDatasource implements IStockDatasource {

    public StockAPIDatasource() {
        super.baseUrl = "http://localhost:8082";
	}

    @Override
    
    public Either<Exception, StockEntity> getStock(StockEntity stock) {
        var response = super.get(super.baseUrl + "/stocks/" + stock.id, false);

        return response.map((list) -> {
            if (list.size() == 0) return null;
            return StockModel.fromMap(list.get(0));
        });
    }

    @Override
    public Either<Exception, List<StockEntity>> getRandomStocks(int qty) {
        var response = super.get(super.baseUrl + "/stocks/random/" + qty, true);

        return response.map((list) -> {
            return list.map((e) -> StockModel.fromMap(e));
        });
    }

    @Override
    public Either<Exception, StockEntity> updateBidAsk(OrderEntity order) {
        String url = String.format("%s/stocks/%s/update/%s/%s", baseUrl, order.id, order.type, order.price);
        var response = super.get(url, false);

        return response.map((list) -> {
            return StockModel.fromMap(list.get(0));
        });
    }
    
}