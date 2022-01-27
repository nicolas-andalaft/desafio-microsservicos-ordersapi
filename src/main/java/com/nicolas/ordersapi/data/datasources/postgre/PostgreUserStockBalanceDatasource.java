package com.nicolas.ordersapi.data.datasources.postgre;

import com.nicolas.ordersapi.data.datasources.IUserStockBalanceDatasource;
import com.nicolas.ordersapi.data.models.UserStockBalanceModel;
import com.nicolas.ordersapi.data.utils.DateTimeFormat;
import com.nicolas.ordersapi.domain.entities.UserStockBalanceEntity;

import io.vavr.collection.List;
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
    public Either<Exception, List<UserStockBalanceEntity>> getUserStockBalancesFromUser(
        UserStockBalanceEntity userStockBalance) {
        var sqlString = String.format("SELECT * FROM %s WHERE id_user = %s", tableName, userStockBalance.id_user);

        return super.executeQuery(sqlString).map((list) -> { 
            return list.map((e) -> UserStockBalanceModel.fromMap(e));
        });
    }

    @Override
    public Either<Exception, UserStockBalanceEntity> getUserStockBalanceFromUserOfStock(
        UserStockBalanceEntity userStockBalance) {
        var sqlString = String.format("SELECT * FROM %s WHERE id_user = %s AND id_stock = %s", tableName, userStockBalance.id_user, userStockBalance.id_stock);

        return super.executeQuery(sqlString).map((list) -> { 
            if (list.length() == 0) return null;
            return UserStockBalanceModel.fromMap(list.get(0));
        });
    }

    @Override
    public Either<Exception, Integer> createUserStockBalance(UserStockBalanceEntity userStockBalance) {
        var sqlString = String.format("INSERT INTO %s(id_stock, id_user, stock_symbol, stock_name, volume) VALUES(%s, %s, '%s', '%s', %s)", 
        tableName, userStockBalance.id_stock, userStockBalance.id_user, userStockBalance.stock_symbol, userStockBalance.stock_name, userStockBalance.volume);

        return super.executeUpdate(sqlString);
    }

    @Override
    public Either<Exception, Integer> adjustUserStockBalanceFromUserOfStock(UserStockBalanceEntity userStockBalance) {
        var updated_on = DateTimeFormat.toString(userStockBalance.updated_on);

        var sqlString = String.format("UPDATE %s SET volume = volume + %s, updated_on = '%s' WHERE id_user = %s AND id_stock = %s", 
        tableName, userStockBalance.volume, updated_on, userStockBalance.id_user, userStockBalance.id_stock);

        return super.executeUpdate(sqlString);
    }
}
