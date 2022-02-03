package com.nicolas.ordersapi.data.repositories;

import java.time.LocalDateTime;
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
    public Either<Exception, List<UserStockBalanceEntity>> getUserStockBalanceFromUser(UserEntity user) {
        return datasource.getUserStockBalancesFromUser(user).map((list) -> list.asJava());
    }

    @Override
    public Either<Exception, UserStockBalanceEntity> getUserStockBalanceFromUserOfStock(UserStockBalanceEntity userStockBalance) {
        var userStockBalanceModel = new UserStockBalanceModel(userStockBalance);
        return datasource.getUserStockBalanceFromUserOfStock(userStockBalanceModel).map((e) -> (UserStockBalanceEntity)e);
    }


    @Override
    public Either<Exception, UserStockBalanceEntity> createOrUpdateUserStockBalanceFromUserOfStock(UserStockBalanceEntity userStockBalance) {
        var userStockBalanceModel = new UserStockBalanceModel(userStockBalance);
        // Check if balance exists
        var search = datasource.getUserStockBalanceFromUserOfStock(userStockBalanceModel);

        if (search.isLeft()) 
            return Either.left(search.getLeft());
        
        if (search.get() == null)
            // Create balance if it does not exist
            return datasource.createUserStockBalance(userStockBalanceModel);
        else {
            // Update balance if it does
            userStockBalanceModel.updated_on = LocalDateTime.now();
            return datasource.adjustUserStockBalanceFromUserOfStock(userStockBalanceModel);
        }
    }


    @Override
    public Either<Exception, UserStockBalanceEntity> adjustUserStockBalanceFromUserOfStock(UserStockBalanceEntity userStockBalance) {
        return datasource.adjustUserStockBalanceFromUserOfStock(userStockBalance);
    }
}