package com.nicolas.ordersapi.data.datasources.postgre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;
import com.nicolas.ordersapi.data.datasources.IUserDatasource;
import com.nicolas.ordersapi.data.utils.ResultConverter;
import io.vavr.control.Either;

public class PostgreUserDatasource extends PostgreDatasource implements IUserDatasource {

	public PostgreUserDatasource() {
		super.databaseUrl = "localhost:5432";
		super.databaseName = "orders_db";
		super.username = "postgres";
		super.password = "postgres";
		super.tableName = "users";
	}

    public Either<Exception, Map<String, Object>> getUser(String email) {
        var tryConnect = connect();
        if (tryConnect.isLeft()) return Either.left(tryConnect.getLeft());

		var conn = tryConnect.get();
        Either<Exception, Map<String, Object>> result;

        try {
			Statement statement = conn.createStatement();
			ResultSet rs = statement.executeQuery(String.format("SELECT * FROM %s WHERE username = '%s'", super.tableName, email));
			
            Map<String, Object> user = ResultConverter.toMap(rs);
			result = Either.right(user);

		} catch (SQLException e) {
			result = Either.left(e);
		}

		try {
			conn.close();
		} catch(Exception e) {}

		return result;
    }

	@Override
	public Either<Exception, Boolean> createUser(Map<String, Object> user) {
		var tryConnect = connect();
        if (tryConnect.isLeft()) return Either.left(tryConnect.getLeft());

		var conn = tryConnect.get();
		Either<Exception, Boolean> result;


        try {
			Statement statement = conn.createStatement();
			
			var status = statement.execute(String.format("INSERT INTO %s(%s,%s,%s) VALUES('%s','%s',%s)", 
				super.tableName, 
				"username", "password", "dollar_balance", 
				user.get("username"), user.get("password"), user.get("dollar_balance")));

			result = Either.right(status);
			
		} catch (SQLException e) {
			result = Either.left(e);
		}

		try {
			conn.close();
		} catch(Exception e) {}

		return result;
	}
}
