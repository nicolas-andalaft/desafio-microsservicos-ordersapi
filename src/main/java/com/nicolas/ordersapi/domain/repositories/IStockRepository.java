package com.nicolas.ordersapi.domain.repositories;

import java.util.List;

import com.nicolas.ordersapi.domain.entities.StockEntity;

import io.vavr.control.Either;

public interface IStockRepository {
    public Either<Exception, StockEntity> getStock(StockEntity stock);
    public Either<Exception, List<StockEntity>> getRandomStocks(int qty);
}
