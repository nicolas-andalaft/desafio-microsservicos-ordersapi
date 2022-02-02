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
    private DataSource _datasource;
	protected String tableName;

	protected PostgreDatasource(String tableName) {
		this.tableName = tableName;
	}
	
    @Bean
	public DataSource dataSource() {
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
		Connection conn = null;
        
		try {
			var datasource = dataSource();
			conn = datasource.getConnection();
			var statement = conn.createStatement();
			var rs = statement.executeQuery(sqlString);
			var response = ResultConverter.toMapList(rs);
			result = Either.right(response);

		} catch (Exception e) {
			result = Either.left(e);
		}

		try {
			conn.close();
		} 
		catch (Exception e) {}

		return result;
	}
}
