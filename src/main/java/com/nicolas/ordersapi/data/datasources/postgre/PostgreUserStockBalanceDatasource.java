package com.nicolas.ordersapi.data.datasources.postgre;

import java.util.Map;

import com.nicolas.ordersapi.data.datasources.IUserStockBalanceDatasource;

import io.vavr.control.Either;

public class PostgreUserStockBalanceDatasource extends PostgreDatasource implements IUserStockBalanceDatasource {

	public PostgreUserStockBalanceDatasource() {
		super.databaseUrl = "localhost:5432";
		super.databaseName = "orders_db";
		super.username = "postgres";
		super.password = "postgres";
		super.tableName = "user_stock_balances";
	}

    @Override
    public Either<Exception, Map<String, Object>[]> getUserStockBalanceFromUser(String userId) {
        return super.execute(
            String.format("SELECT * FROM %s WHERE id_user = '%s'", tableName, userId), 
            true);		
    }

    @Override
    public Either<Exception, Map<String, Object>[]> getUserStockBalanceFromUserOfStock(String userId, String stockId) {
        return super.execute(
            String.format("SELECT * FROM %s WHERE id_user = %s AND id_stock = %s", tableName, userId, stockId), 
            true);	
    }

    @Override
    public Either<Exception, Map<String, Object>> createUserStockBalance(Map<String, Object> map) {
        var response = super.execute(
            String.format("INSERT INTO %s(id_user, id_stock, stock_symbol, stock_name, volume)"+
            " VALUES(%s, %s, '%s', '%s', %s)",
            tableName, map.get("id_user"), map.get("id_stock"), map.get("stock_symbol"), map.get("stock_name"), map.get("volume")), 
            false);	

        if (response.isLeft())
            return Either.left(response.getLeft());

        return Either.right(response.get()[0]);
    }
}
