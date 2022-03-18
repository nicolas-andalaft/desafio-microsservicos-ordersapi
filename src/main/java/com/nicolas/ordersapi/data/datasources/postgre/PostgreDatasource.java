package com.nicolas.ordersapi.data.datasources.postgre;

import java.sql.Connection;
import java.util.Map;
import java.util.logging.Logger;
import java.util.logging.Level;

import javax.sql.DataSource;

import com.nicolas.ordersapi.data.utils.ResultConverter;

import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import io.vavr.collection.List;
import io.vavr.control.Either;

public abstract class PostgreDatasource {
    private static DataSource privateDataSource;
	private static Connection privateConn;
	private Logger logger;

	protected String tableName;

	protected PostgreDatasource(String tableName) {
		this.tableName = tableName;

		this.logger = Logger.getLogger("Logger");
	}

	public static Either<Exception, Boolean> openConnection() {
		// Return false on already opened connection
		if (privateConn != null) return Either.right(false);

		var result = getConnection();
		return result.map(e -> true);
	}

	public static void closeConnection() {
		try {
			privateConn.close();
		} 
		catch (Exception e) {
			var logger = Logger.getLogger("Logger");
			logger.log(Level.FINEST, e.getMessage());
		}

		privateConn = null;
	}
	
    @Bean
	private static DataSource getDataSource() {
		if (privateDataSource != null)
			return privateDataSource;

		DriverManagerDataSource dataSource = new DriverManagerDataSource();

		dataSource.setUrl("jdbc:postgresql://localhost:5432/orders_db");
		dataSource.setUsername("postgres");
		dataSource.setPassword("postgres");
		privateDataSource = dataSource;

		return privateDataSource;
	}

	protected static Either<Exception, Connection> getConnection() {
		try {
			var datasource = getDataSource();
			privateConn = datasource.getConnection();
			return Either.right(privateConn);

		} catch (Exception e) {
			return Either.left(e);
		}
	}

	protected Either<Exception, List<Map<String, Object>>> execute(String sqlString) {
        Either<Exception, List<Map<String, Object>>> result;

		var gonnResult = getConnection();
		if (gonnResult.isLeft())
			return Either.left(gonnResult.getLeft());
        
		Connection conn = gonnResult.get();
		
		try(var statement = conn.prepareStatement(sqlString);) {
			
			var rs = statement.executeQuery();
			var response = ResultConverter.toMapList(rs);
			result = Either.right(response);

		} catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage());
			result = Either.left(e);
		}

		return result;
	}
}
