package com.nicolas.ordersapi.data.datasources.postgre;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

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
}
