package com.nicolas.ordersapi.data.datasources.postgre;

import com.nicolas.ordersapi.data.datasources.IUserDatasource;
import com.nicolas.ordersapi.data.models.UserModel;
import com.nicolas.ordersapi.domain.entities.UserEntity;

import io.vavr.control.Either;

public class PostgreUserDatasource extends PostgreDatasource implements IUserDatasource {

	public PostgreUserDatasource() {
		super.databaseUrl = "localhost:5432";
		super.databaseName = "orders_db";
		super.username = "postgres";
		super.password = "postgres";
		super.tableName = "users";
	}

    public Either<Exception, UserEntity> getUser(UserEntity user) {
		var sqlString = String.format("SELECT * FROM %s WHERE username = '%s'", super.tableName, user.username);

		return super.executeQuery(sqlString).map((list) -> { 
			if (list.length() != 1) return null;
			return UserModel.fromMap(list.get(0));
		});
        
    }

	@Override
	public Either<Exception, Integer> createUser(UserEntity user) {
		var sqlString = String.format("INSERT INTO %s(%s,%s,%s) VALUES('%s','%s',%s)", 
		super.tableName, 
		"username", "password", "dollar_balance", 
		user.username, "", user.dollar_balance);

		return super.executeUpdate(sqlString);
	}

	@Override
	public Either<Exception, Integer> adjustDollarBalance(UserEntity user) {
		var sqlString = String.format("UPDATE %s SET dollar_balance = dollar_balance + %s WHERE id = %s", 
		super.tableName, user.dollar_balance, user.id);

		return super.executeUpdate(sqlString);
	}
}
