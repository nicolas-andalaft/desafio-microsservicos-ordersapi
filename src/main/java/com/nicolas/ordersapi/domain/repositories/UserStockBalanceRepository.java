package com.nicolas.ordersapi.domain.repositories;

import com.nicolas.ordersapi.domain.entities.UserStockBalanceEntity;

import io.vavr.control.Either;

public abstract class UserStockBalanceRepository {
    public abstract Either<Exception, UserStockBalanceEntity[]> getUserStockBalanceFromUser(UserStockBalanceEntity userStockBalance);

    public abstract Either<Exception, UserStockBalanceEntity[]> getUserStockBalanceFromUserOfStock(UserStockBalanceEntity userStockBalance);  

    public abstract Either<Exception, UserStockBalanceEntity> createUserStockBalance(UserStockBalanceEntity userStockBalance);   
}