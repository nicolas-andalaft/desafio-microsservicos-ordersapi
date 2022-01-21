package com.nicolas.ordersapi.data.repositories;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.nicolas.ordersapi.data.datasources.IUserStockBalanceDatasource;
import com.nicolas.ordersapi.data.mappers.UserStockBalanceMapper;
import com.nicolas.ordersapi.domain.entities.UserStockBalanceEntity;
import com.nicolas.ordersapi.domain.repositories.UserStockBalanceRepository;

import io.vavr.control.Either;

public class UserStockBalanceRepositoryImplementation extends UserStockBalanceRepository {
    private IUserStockBalanceDatasource datasource;

    public UserStockBalanceRepositoryImplementation(IUserStockBalanceDatasource datasource) {
        this.datasource = datasource;
    }
    

    @Override
    public Either<Exception, UserStockBalanceEntity[]> getUserStockBalanceFromUser(UserStockBalanceEntity userStockBalance) {
        var response = datasource.getUserStockBalanceFromUser(userStockBalance.idUser.toString());

        if (response.isLeft())
            return Either.left(response.getLeft());
        
        List<UserStockBalanceEntity> result = new ArrayList<>();
        for (Map<String, Object> map : response.get()) {
            result.add(UserStockBalanceMapper.fromMap(map));
        }

        return Either.right((UserStockBalanceEntity[])result.toArray());
    }

    @Override
    public Either<Exception, UserStockBalanceEntity[]> getUserStockBalanceFromUserOfStock(UserStockBalanceEntity userStockBalance) {
        var response = datasource.getUserStockBalanceFromUserOfStock(userStockBalance.idUser.toString(), userStockBalance.idStock.toString());

        if (response.isLeft())
            return Either.left(response.getLeft());
        
        List<UserStockBalanceEntity> result = new ArrayList<>();
        for (Map<String, Object> map : response.get()) {
            result.add(UserStockBalanceMapper.fromMap(map));
        }

        return Either.right((UserStockBalanceEntity[])result.toArray());
    }


    @Override
    public Either<Exception, UserStockBalanceEntity> createUserStockBalance(UserStockBalanceEntity userStockBalance) {
        var response = datasource.createUserStockBalance(UserStockBalanceMapper.toMap(userStockBalance));

        if (response.isLeft())
            return Either.left(response.getLeft());

        return Either.right(UserStockBalanceMapper.fromMap(response.get()));
    }
    
}
