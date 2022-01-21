package com.nicolas.ordersapi.domain.repositories;

import com.nicolas.ordersapi.domain.entities.UserStockBalanceEntity;

import io.vavr.control.Either;

public interface IUserStockBalanceRepository {
    public Either<Exception, UserStockBalanceEntity[]> getUserStockBalanceFromUser(UserStockBalanceEntity userStockBalance);

    public Either<Exception, UserStockBalanceEntity[]> getUserStockBalanceFromUserOfStock(UserStockBalanceEntity userStockBalance);  

    public Either<Exception, UserStockBalanceEntity> createUserStockBalance(UserStockBalanceEntity userStockBalance);   
}