package com.nicolas.ordersapi.data.datasources;

import java.util.Map;

import io.vavr.control.Either;

public interface IUserStockBalanceDatasource {
    public abstract Either<Exception, Map<String, Object>[]> getUserStockBalanceFromUser(String userId);
    public abstract Either<Exception, Map<String, Object>[]> getUserStockBalanceFromUserOfStock(String userId, String stockId);
    public abstract Either<Exception, Map<String, Object>> createUserStockBalance(Map<String, Object> userStockBalance);
}
