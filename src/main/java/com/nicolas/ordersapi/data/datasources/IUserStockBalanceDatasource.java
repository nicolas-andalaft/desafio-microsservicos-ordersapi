package com.nicolas.ordersapi.data.datasources;

import com.nicolas.ordersapi.domain.entities.UserStockBalanceEntity;

import io.vavr.collection.List;
import io.vavr.control.Either;

public interface IUserStockBalanceDatasource {
    public abstract Either<Exception, List<UserStockBalanceEntity>> getUserStockBalancesFromUser(UserStockBalanceEntity userStockBalance);
    public abstract Either<Exception, UserStockBalanceEntity> getUserStockBalanceFromUserOfStock(UserStockBalanceEntity userStockBalance);
    public abstract Either<Exception, UserStockBalanceEntity> createUserStockBalance(UserStockBalanceEntity userStockBalance);
    public abstract Either<Exception, UserStockBalanceEntity> adjustUserStockBalanceFromUserOfStock(UserStockBalanceEntity userStockBalance);
}
