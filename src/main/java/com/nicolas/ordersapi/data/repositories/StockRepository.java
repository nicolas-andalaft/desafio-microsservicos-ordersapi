package com.nicolas.ordersapi.data.repositories;

import com.nicolas.ordersapi.data.datasources.IStockDatasource;
import com.nicolas.ordersapi.domain.entities.OrderEntity;
import com.nicolas.ordersapi.domain.entities.StockEntity;
import com.nicolas.ordersapi.domain.repositories.IStockRepository;

import io.vavr.collection.List;
import io.vavr.control.Either;

public class StockRepository implements IStockRepository {
    private IStockDatasource datasource;

    public StockRepository(IStockDatasource stockDatasource) {
        datasource = stockDatasource;
    }

    @Override
    public Either<Exception, StockEntity> getStock(StockEntity stock) {
        return datasource.getStock(stock).map((e) -> (StockEntity)e);
    }

    @Override
    public Either<Exception, List<StockEntity>> getRandomStocks(int qty) {
        return datasource.getRandomStocks(qty);
    }

    @Override
    public Either<Exception, StockEntity> tryUpdateBidAsk(OrderEntity order) {
        return datasource.tryUpdateBidAsk(order);
    }

    @Override
    public Either<Exception, StockEntity> forceUpdateBidAsk(StockEntity stock) {
        return datasource.forceUpdateBidAsk(stock);
    }
    
}
