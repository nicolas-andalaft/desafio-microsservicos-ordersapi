package com.nicolas.ordersapi.data.datasources.postgre;

import java.sql.Connection;
import java.util.Map;

import javax.sql.DataSource;

import com.nicolas.ordersapi.data.utils.ResultConverter;

import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import io.vavr.collection.List;
import io.vavr.control.Either;

public abstract class PostgreDatasource {
    private static DataSource _datasource;
	private static Connection _conn;
	protected String tableName;

	protected PostgreDatasource(String tableName) {
		this.tableName = tableName;
	}

	public static Either<Exception, Boolean> openConnection() {
		// Return false on already opened connection
		if (_conn != null) return Either.right(false);

		var result = getConnection();
		return result.map((e) -> true);
	}

	public static void closeConnection() {
		try {
			_conn.close();

		} catch (Exception e) {}

		_conn = null;
	}

	private static Either<Exception, Connection> getConnection() {
		try {
			var datasource = getDataSource();
			_conn = datasource.getConnection();
			return Either.right(_conn);

		} catch (Exception e) {
			return Either.left(e);
		}
	}
	
    @Bean
	private static DataSource getDataSource() {
		if (_datasource != null)
			return _datasource;

		DriverManagerDataSource dataSource = new DriverManagerDataSource();

		dataSource.setUrl("jdbc:postgresql://localhost:5432/orders_db");
		dataSource.setUsername("postgres");
		dataSource.setPassword("postgres");
		_datasource = dataSource;

		return _datasource;
	}

	protected Either<Exception, List<Map<String, Object>>> execute(String sqlString) {
        Either<Exception, List<Map<String, Object>>> result;
		boolean manualConn = _conn == null;

		if (manualConn) {
			var tryConnect = getConnection();
			if (tryConnect.isLeft()) 
				return Either.left(tryConnect.getLeft());

				_conn = tryConnect.get();
		}
        
		try {
			var statement = _conn.createStatement();
			var rs = statement.executeQuery(sqlString);
			var response = ResultConverter.toMapList(rs);
			result = Either.right(response);

		} catch (Exception e) {
			result = Either.left(e);
		}

		if (manualConn)
			PostgreDatasource.closeConnection();

		return result;
	}
}
