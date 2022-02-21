package com.nicolas.ordersapi.data.datasources.postgre;

import com.nicolas.ordersapi.data.datasources.IUserStockBalanceDatasource;
import com.nicolas.ordersapi.data.models.UserStockBalanceModel;
import com.nicolas.ordersapi.domain.entities.UserEntity;
import com.nicolas.ordersapi.domain.entities.UserStockBalanceEntity;

import io.vavr.collection.List;
import io.vavr.control.Either;

public class PostgreUserStockBalanceDatasource extends PostgreDatasource implements IUserStockBalanceDatasource {

    public PostgreUserStockBalanceDatasource() {
		super("user_stock_balances");
	}

    @Override
    public Either<Exception, List<UserStockBalanceEntity>> getUserBalance(UserEntity user) {
        var sqlString = String.format("SELECT * FROM %s WHERE id_user = %s", tableName, user.id);

        return super.execute(sqlString).map((list) -> { 
            if (list.length() == 0) return null;
            return list.map((e) -> UserStockBalanceModel.fromMap(e));
        });
    }

    @Override
    public Either<Exception, UserStockBalanceEntity> getUserStockBalance(
        UserStockBalanceEntity userStockBalance) {
        var sqlString = String.format("SELECT * FROM %s WHERE id_user = %s AND id_stock = %s", tableName, userStockBalance.id_user, userStockBalance.id_stock);

        return super.execute(sqlString).map((list) -> { 
            if (list.length() == 0 || list.get(0) == null) return null;
            return UserStockBalanceModel.fromMap(list.get(0));
        });
    }

    @Override
    public Either<Exception, Object> createOrUpdateBalance(UserStockBalanceEntity balance) {

        var sqlString = String.format(
            "INSERT INTO %s AS t (id_stock, id_user, stock_symbol, stock_name, volume) VALUES(%s, %s, '%s', '%s', %s) " +
            "ON CONFLICT (id_user, id_stock) DO UPDATE SET volume = t.volume + %s RETURNING *", 
            tableName,  balance.id_stock, balance.id_user, balance.stock_symbol, balance.stock_name, balance.volume,
            balance.volume
        );

        return super.execute(sqlString).map((list) -> { 
            if (list.length() == 0 || list.get(0) == null) return null;
            return UserStockBalanceModel.fromMap(list.get(0));
        });
    }
}
