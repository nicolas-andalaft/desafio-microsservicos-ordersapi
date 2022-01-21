package com.nicolas.ordersapi.data.datasources.postgre;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

import com.nicolas.ordersapi.data.utils.ResultConverter;

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

	protected Either<Exception, Map<String, Object>[]> execute(String sqlString, boolean query) {
		var tryConnect = connect();
        if (tryConnect.isLeft()) return Either.left(tryConnect.getLeft());

		var conn = tryConnect.get();
        Either<Exception, Map<String, Object>[]> result;
		ResultSet rs;
        
		try {
			Statement statement = conn.createStatement();
			if (query)
				rs = statement.executeQuery(sqlString);
			else {
				statement.execute(sqlString);
				rs = statement.getResultSet();
			}
			
            var response = ResultConverter.toMapArray(rs);
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
