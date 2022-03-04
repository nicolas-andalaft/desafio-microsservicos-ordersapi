package com.nicolas.ordersapi.domain.repositories;

import com.nicolas.ordersapi.domain.entities.OrderEntity;
import com.nicolas.ordersapi.domain.entities.StockEntity;

import io.vavr.collection.List;
import io.vavr.control.Either;

public interface IStockRepository {
    public Either<Exception, StockEntity> getStock(StockEntity stock);
    public Either<Exception, List<StockEntity>> getRandomStocks(int qty);
    public Either<Exception, StockEntity> tryUpdateBidAsk(OrderEntity order);
    public Either<Exception, StockEntity> forceUpdateBidAsk(StockEntity stock);
}
