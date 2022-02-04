package com.nicolas.ordersapi.data.repositories;

import java.util.List;

import com.nicolas.ordersapi.data.datasources.IUserStockBalanceDatasource;
import com.nicolas.ordersapi.data.models.UserStockBalanceModel;
import com.nicolas.ordersapi.domain.entities.UserEntity;
import com.nicolas.ordersapi.domain.entities.UserStockBalanceEntity;
import com.nicolas.ordersapi.domain.repositories.IUserStockBalanceRepository;

import io.vavr.control.Either;

public class UserStockBalanceRepository implements IUserStockBalanceRepository {
    private IUserStockBalanceDatasource datasource;

    public UserStockBalanceRepository(IUserStockBalanceDatasource userStockBalanceDatasource) {
        datasource = userStockBalanceDatasource;
    }
    

    @Override
    public Either<Exception, List<UserStockBalanceEntity>> getUserBalance(UserEntity user) {
        return datasource.getUserBalance(user).map((list) -> list.asJava());
    }

    @Override
    public Either<Exception, UserStockBalanceEntity> getUserStockBalance(UserStockBalanceEntity userStockBalance) {
        var userStockBalanceModel = new UserStockBalanceModel(userStockBalance);
        return datasource.getUserStockBalance(userStockBalanceModel).map((e) -> (UserStockBalanceEntity)e);
    }


    @Override
    public Either<Exception, UserStockBalanceEntity> createOrUpdateBalance(UserStockBalanceEntity userStockBalance) {
        return datasource.createOrUpdateBalance(userStockBalance);
    }
}