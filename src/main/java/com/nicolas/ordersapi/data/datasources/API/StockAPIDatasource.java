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
        var response = super.execute(super.baseUrl + "/stocks/" + stock.id, null, false);

        return response.map((list) -> {
            if (list.size() == 0) return null;
            return StockModel.fromMap(list.get(0));
        });
    }

    @Override
    public Either<Exception, List<StockEntity>> getRandomStocks(int qty) {
        var response = super.execute(super.baseUrl + "/stocks/random/" + qty, null, true);

        return response.map((list) -> {
            return list.map((e) -> StockModel.fromMap(e));
        });
    }

    @Override
    public Either<Exception, StockEntity> tryUpdateBidAsk(OrderEntity order) {
        String url = String.format("%s/stocks/%s/check/%s/%s", baseUrl, order.id_stock, order.type, order.price);
        var response = super.execute(url, null, false);

        return response.map((list) -> {
            return StockModel.fromMap(list.get(0));
        });
    }

    @Override
    public Either<Exception, StockEntity> forceUpdateBidAsk(StockEntity stock) {
        String url = String.format("%s/stocks/%s/update", baseUrl, stock.id);
        var response = super.execute(url, StockModel.toMap(stock), false);

        return response.map((list) -> {
            return StockModel.fromMap(list.get(0));
        });
    }
    
}