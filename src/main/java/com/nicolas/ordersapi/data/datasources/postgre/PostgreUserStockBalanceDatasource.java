package com.nicolas.ordersapi.data.datasources.postgre;

import com.nicolas.ordersapi.data.datasources.IUserStockBalanceDatasource;
import com.nicolas.ordersapi.data.models.UserStockBalanceModel;

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
    @SuppressWarnings("deprecation")
    public Either<Exception, UserStockBalanceModel[]> getUserStockBalancesFromUser(
            UserStockBalanceModel userStockBalance) {
        var sqlString = String.format("SELECT * FROM %s WHERE id_user = %s", tableName, userStockBalance.id_user);

        return super.executeQuery(sqlString).map((list) -> { 
            var userStockBalanceModelList = list.map(UserStockBalanceModel::fromMap);
            return (UserStockBalanceModel[])userStockBalanceModelList.toJavaArray();
        });
    }

    @Override
    public Either<Exception, UserStockBalanceModel> getUserStockBalanceFromUserOfStock(
            UserStockBalanceModel userStockBalance) {
        var sqlString = String.format("SELECT * FROM %s WHERE id_user = %s AND id_stock = %s", tableName, userStockBalance.id_user, userStockBalance.id_stock);

        return super.executeQuery(sqlString).map((list) -> { 
            if (list.length() == 0) return null;
            return UserStockBalanceModel.fromMap(list.get(0));
        });
    }

    @Override
    public Either<Exception, Integer> createUserStockBalance(UserStockBalanceModel userStockBalance) {
        var sqlString = String.format("INSERT INTO %s(id_stock, id_user, stock_symbol, stock_name, volume) VALUES(%s, %s, '%s', '%s', %s)", 
        tableName, userStockBalance.id_stock, userStockBalance.id_user, userStockBalance.stock_symbol, userStockBalance.stock_name, userStockBalance.volume);

        return super.executeUpdate(sqlString);
    }

    @Override
    public Either<Exception, Integer> updateUserStockBalanceFromUserOfStock(UserStockBalanceModel userStockBalance) {
        var sqlString = String.format("UPDATE %s SET volume = %s, updated_on = %s WHERE id_user = %s AND id_stock = %s", 
        tableName, userStockBalance.volume, userStockBalance.updated_on, userStockBalance.id_user, userStockBalance.id_stock);

        return super.executeUpdate(sqlString);
    }
    
}
