package com.nicolas.ordersapi.data.datasources;

import com.nicolas.ordersapi.domain.entities.StockEntity;

import io.vavr.control.Either;

public interface IStockDatasource {
    public Either<Exception, StockEntity> getStock(StockEntity stock);
}
