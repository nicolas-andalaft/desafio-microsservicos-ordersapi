package com.nicolas.ordersapi.domain.repositories;

import com.nicolas.ordersapi.domain.entities.StockEntity;

import io.vavr.control.Either;

public interface IStockRepository {
    public Either<Exception, StockEntity> getStock(StockEntity stock);
}
