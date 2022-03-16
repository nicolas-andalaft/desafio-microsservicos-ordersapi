package com.nicolas.ordersapi.domain.repositories;

import java.util.List;

import com.nicolas.ordersapi.domain.entities.UserEntity;
import com.nicolas.ordersapi.domain.entities.UserStockBalanceEntity;

import io.vavr.control.Either;

public interface IUserStockBalanceRepository {
    public Either<Exception, List<UserStockBalanceEntity>> getUserBalance(UserEntity user);
    public Either<Exception, UserStockBalanceEntity> getUserStockBalance(UserStockBalanceEntity userStockBalance);  
    public Either<Exception, Object> createOrUpdateBalances(List<UserStockBalanceEntity> userStockBalance);   
}