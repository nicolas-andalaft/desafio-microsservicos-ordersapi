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
        return datasource.getUserBalance(user).map(io.vavr.collection.List::asJava);
    }

    @Override
    public Either<Exception, UserStockBalanceEntity> getUserStockBalance(UserStockBalanceEntity userStockBalance) {
        var userStockBalanceModel = new UserStockBalanceModel(userStockBalance);
        return datasource.getUserStockBalance(userStockBalanceModel);
    }


    @Override
    public Either<Exception, Object> createOrUpdateBalances(List<UserStockBalanceEntity> userStockBalances) {
        Exception anyException = null;
        Either<Exception, Object> result;

        for (UserStockBalanceEntity balance : userStockBalances) {
            result = datasource.createOrUpdateBalance(balance);
            if (result.isLeft())
                anyException = result.getLeft();
        }

        if (anyException != null)
            return Either.left(anyException);
        
        return Either.right(true);
    }
}