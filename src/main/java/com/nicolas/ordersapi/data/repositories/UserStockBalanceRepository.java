package com.nicolas.ordersapi.data.repositories;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.nicolas.ordersapi.data.datasources.IUserStockBalanceDatasource;
import com.nicolas.ordersapi.data.models.UserStockBalanceModel;
import com.nicolas.ordersapi.domain.entities.UserStockBalanceEntity;
import com.nicolas.ordersapi.domain.repositories.IUserStockBalanceRepository;

import io.vavr.control.Either;

public class UserStockBalanceRepository implements IUserStockBalanceRepository {
    private IUserStockBalanceDatasource datasource;

    public UserStockBalanceRepository(IUserStockBalanceDatasource userStockBalanceDatasource) {
        datasource = userStockBalanceDatasource;
    }
    

    @Override
    public Either<Exception, UserStockBalanceEntity[]> getUserStockBalanceFromUser(UserStockBalanceEntity userStockBalance) {
        var response = datasource.getUserStockBalanceFromUser(userStockBalance.id_user.toString());

        if (response.isLeft())
            return Either.left(response.getLeft());
        
        List<UserStockBalanceEntity> result = new ArrayList<>();
        for (Map<String, Object> map : response.get()) {
            result.add(UserStockBalanceModel.fromMap(map));
        }

        return Either.right((UserStockBalanceEntity[])result.toArray());
    }

    @Override
    public Either<Exception, UserStockBalanceEntity[]> getUserStockBalanceFromUserOfStock(UserStockBalanceEntity userStockBalance) {
        var response = datasource.getUserStockBalanceFromUserOfStock(userStockBalance.id_user.toString(), userStockBalance.id_stock.toString());

        if (response.isLeft())
            return Either.left(response.getLeft());
        
        List<UserStockBalanceEntity> result = new ArrayList<>();
        for (Map<String, Object> map : response.get()) {
            result.add(UserStockBalanceModel.fromMap(map));
        }

        return Either.right((UserStockBalanceEntity[])result.toArray());
    }


    @Override
    public Either<Exception, UserStockBalanceEntity> createUserStockBalance(UserStockBalanceEntity userStockBalance) {
        var response = datasource.createUserStockBalance(UserStockBalanceModel.toMap((UserStockBalanceModel)userStockBalance));

        if (response.isLeft())
            return Either.left(response.getLeft());

        return Either.right(UserStockBalanceModel.fromMap(response.get()));
    }
    
}
