package com.nicolas.ordersapi.data.datasources;

import com.nicolas.ordersapi.domain.entities.StockEntity;

import io.vavr.collection.List;
import io.vavr.control.Either;

public interface IStockDatasource {
    public Either<Exception, StockEntity> getStock(StockEntity stock);
    public Either<Exception, List<StockEntity>> getRandomStocks(int qty);
}
