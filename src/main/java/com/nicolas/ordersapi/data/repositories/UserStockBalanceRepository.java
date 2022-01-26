package com.nicolas.ordersapi.data.repositories;

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
        var userStockBalanceModel = new UserStockBalanceModel(userStockBalance);
        return datasource.getUserStockBalancesFromUser(userStockBalanceModel).map((e) -> (UserStockBalanceEntity[])e);
    }

    @Override
    public Either<Exception, UserStockBalanceEntity> getUserStockBalanceFromUserOfStock(UserStockBalanceEntity userStockBalance) {
        var userStockBalanceModel = new UserStockBalanceModel(userStockBalance);
        return datasource.getUserStockBalanceFromUserOfStock(userStockBalanceModel).map((e) -> (UserStockBalanceEntity)e);
    }


    @Override
    public Either<Exception, Integer> createOrUpdateUserStockBalanceFromUserOfStock(UserStockBalanceEntity userStockBalance) {
        var userStockBalanceModel = new UserStockBalanceModel(userStockBalance);
        // Check if balance exists
        var search = datasource.getUserStockBalanceFromUserOfStock(userStockBalanceModel);

        if (search.isLeft()) 
            return Either.left(search.getLeft());
        
        if (search.get() == null)
            // Create balance if it does not exist
            return datasource.createUserStockBalance(userStockBalanceModel);
        else
            // Update balance if it does
            return datasource.updateUserStockBalanceFromUserOfStock(userStockBalanceModel);
    }
}