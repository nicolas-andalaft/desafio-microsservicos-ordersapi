package com.nicolas.ordersapi.data.datasources.postgre;

import com.nicolas.ordersapi.data.datasources.IOrderDatasource;
import com.nicolas.ordersapi.data.models.OrderModel;

import io.vavr.control.Either;

public class PostgreOrderDatasource extends PostgreDatasource implements IOrderDatasource {

    public PostgreOrderDatasource() {
		super.databaseUrl = "localhost:5432";
		super.databaseName = "orders_db";
		super.username = "postgres";
		super.password = "postgres";
		super.tableName = "user_orders";
	}

    @Override
    public Either<Exception, Integer> createOrder(OrderModel order) {
        String sqlString = String.format(
            "INSERT INTO %s(id_user, id_stock, stock_symbol, stock_name, volume, price, type, status)"+
            " VALUES(%s, %s, '%s', '%s', %s, %s, %s, %s)", 
            tableName, order.id_user, order.id_stock, order.stock_symbol, order.stock_name, order.volume, order.price, order.type, order.status);

        return super.executeUpdate(sqlString);
    }
    
}
