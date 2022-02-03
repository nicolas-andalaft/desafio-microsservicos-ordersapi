package com.nicolas.ordersapi.domain.repositories;

import java.util.List;

import com.nicolas.ordersapi.domain.entities.UserEntity;
import com.nicolas.ordersapi.domain.entities.UserStockBalanceEntity;

import io.vavr.control.Either;

public interface IUserStockBalanceRepository {
    public Either<Exception, List<UserStockBalanceEntity>> getUserStockBalanceFromUser(UserEntity user);
    public Either<Exception, UserStockBalanceEntity> getUserStockBalanceFromUserOfStock(UserStockBalanceEntity userStockBalance);  
    public Either<Exception, UserStockBalanceEntity> createOrUpdateUserStockBalanceFromUserOfStock(UserStockBalanceEntity userStockBalance);   
    public Either<Exception, UserStockBalanceEntity> adjustUserStockBalanceFromUserOfStock(UserStockBalanceEntity userStockBalance);   
}