package com.nicolas.ordersapi.data.datasources.postgre;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

import com.nicolas.ordersapi.data.utils.ResultConverter;

import io.vavr.collection.List;
import io.vavr.control.Either;

public abstract class PostgreDatasource {
    protected String databaseUrl;
	protected String databaseName;
	protected String username;
	protected String password;
	protected String tableName;

    protected Either<Exception, Connection> connect() {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(String.format("jdbc:postgresql://%s/%s", databaseUrl, databaseName), username, password);
			return Either.right(conn);

		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return Either.left(e);
		}
	}

	protected Either<Exception, List<Map<String, Object>>> executeQuery(String sqlString) {
		var tryConnect = connect();
        if (tryConnect.isLeft()) return Either.left(tryConnect.getLeft());

		var conn = tryConnect.get();
        Either<Exception, List<Map<String, Object>>> result;
        
		try {
			Statement statement = conn.createStatement();
			var rs = statement.executeQuery(sqlString);
	
            var response = ResultConverter.toMapList(rs);
			result = Either.right(response);

		} catch (SQLException e) {
			result = Either.left(e);
		}

		try {
			conn.close();
		} catch(Exception e) {}

		return result;
	}
}
