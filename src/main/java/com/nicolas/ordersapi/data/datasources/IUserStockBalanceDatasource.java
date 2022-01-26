package com.nicolas.ordersapi.data.datasources;

import com.nicolas.ordersapi.data.models.UserStockBalanceModel;

import io.vavr.control.Either;

public interface IUserStockBalanceDatasource {
    public abstract Either<Exception, UserStockBalanceModel[]> getUserStockBalancesFromUser(UserStockBalanceModel userStockBalance);
    public abstract Either<Exception, UserStockBalanceModel> getUserStockBalanceFromUserOfStock(UserStockBalanceModel userStockBalance);
    public abstract Either<Exception, Integer> createUserStockBalance(UserStockBalanceModel userStockBalance);
    public abstract Either<Exception, Integer> adjustUserStockBalanceFromUserOfStock(UserStockBalanceModel userStockBalance);
}
