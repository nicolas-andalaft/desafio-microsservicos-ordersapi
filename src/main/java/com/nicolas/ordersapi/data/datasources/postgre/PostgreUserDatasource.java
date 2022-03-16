package com.nicolas.ordersapi.data.datasources.postgre;

import com.nicolas.ordersapi.data.datasources.IUserDatasource;
import com.nicolas.ordersapi.data.models.UserModel;
import com.nicolas.ordersapi.domain.entities.UserEntity;

import io.vavr.control.Either;

public class PostgreUserDatasource extends PostgreDatasource implements IUserDatasource {

	public PostgreUserDatasource() {
		super("users");
	}

    public Either<Exception, UserEntity> getUser(UserEntity user) {
		var sqlString = String.format("SELECT * FROM %s WHERE username = '%s'", super.tableName, user.getUsername());

		return super.execute(sqlString).map(list -> UserModel.fromMap(list.get(0)));
    }

	@Override
	public Either<Exception, UserEntity> createUser(UserEntity user) {
		var sqlString = String.format("INSERT INTO %s(%s,%s,%s) VALUES('%s','%s',%s) RETURNING *", 
		super.tableName, 
		"username", "password", "dollar_balance", 
		user.getUsername(), "", user.getDollarBalance());

		return super.execute(sqlString).map(list -> UserModel.fromMap(list.get(0)));
	}

	@Override
	public Either<Exception, UserEntity> adjustDollarBalance(UserEntity user) {
		var sqlString = String.format("UPDATE %s SET dollar_balance = dollar_balance + %s WHERE id = %s RETURNING *", 
		super.tableName, user.getDollarBalance(), user.getId());

		return super.execute(sqlString).map(list -> UserModel.fromMap(list.get(0)));
	}
}
