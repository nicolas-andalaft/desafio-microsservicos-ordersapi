package com.nicolas.ordersapi.data.datasources;

import com.nicolas.ordersapi.domain.entities.UserEntity;
import com.nicolas.ordersapi.domain.entities.UserStockBalanceEntity;

import io.vavr.collection.List;
import io.vavr.control.Either;

public interface IUserStockBalanceDatasource {
    public abstract Either<Exception, List<UserStockBalanceEntity>> getUserBalance(UserEntity user);
    public abstract Either<Exception, UserStockBalanceEntity> getUserStockBalance(UserStockBalanceEntity userStockBalance);
    public abstract Either<Exception, UserStockBalanceEntity> createOrUpdateBalance(UserStockBalanceEntity userStockBalance);
}
