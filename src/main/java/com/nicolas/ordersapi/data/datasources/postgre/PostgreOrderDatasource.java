package com.nicolas.ordersapi.data.datasources.postgre;

import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import com.nicolas.ordersapi.data.datasources.IOrderDatasource;
import com.nicolas.ordersapi.data.models.OrderModel;
import com.nicolas.ordersapi.domain.entities.OrderEntity;
import com.nicolas.ordersapi.domain.entities.UserEntity;

import io.vavr.collection.List;
import io.vavr.control.Either;

public class PostgreOrderDatasource extends PostgreDatasource implements IOrderDatasource {

    public PostgreOrderDatasource() {
		super("user_orders");
	}
    
    @Override
    public Either<Exception, OrderEntity> createOrder(OrderEntity order) {
        String sqlString = String.format(
            "INSERT INTO %s(id_user, id_stock, stock_symbol, stock_name, volume, price, type, status)"+
            " VALUES(%s, %s, '%s', '%s', %s, %s, %s, %s) RETURNING *", 
            tableName, order.id_user, order.id_stock, order.stock_symbol, order.stock_name, order.volume, order.price, order.type, order.status);

        return super.execute(sqlString).map((list) -> { 
            if (list.length() == 0) return null;
            return OrderModel.fromMap(list.get(0));
        });
    }

    @Override
    public Either<Exception, List<OrderEntity>> getUserOrders(UserEntity user) {
        String sqlString = String.format(
            "SELECT * FROM %s WHERE id_user = %s ORDER BY stock_name, stock_symbol", tableName, user.id);

        return super.execute(sqlString).map((list) -> {
            return list.map(OrderModel::fromMap);
        });
    }

    @Override
    public Either<Exception, List<OrderEntity>> getOrderMatches(OrderEntity order) {
        String sqlString = String.format(
            "SELECT * FROM %s WHERE id_stock = %s AND type = %s AND price %s %s ORDER BY price, created_on", 
            tableName, order.id_stock, order.type == 0 ? 1 : 0, order.type == 0 ? "<=" : ">=", order.price
        );

        return super.execute(sqlString).map((list) -> {
            return list.map(OrderModel::fromMap);
        });
    }

    @Override
    public Either<Exception, Object> updateOrders(List<OrderEntity> orders) {
        var updated_on = Timestamp.valueOf(LocalDateTime.now());

        String sqlString = String.format(
            "UPDATE %s SET  volume = ?, status = ?, updated_on = ? WHERE id = ?", 
            tableName
        );

        var conn = super.getConnection();
        if (conn.isLeft())
            return Either.left(conn.getLeft());

        PreparedStatement statement;
        try {
            // Set sql query
            statement = conn.get().prepareStatement(sqlString);
            // Set values

            for (OrderEntity order : orders) {
                statement.setLong(1, order.volume);
                statement.setInt(2, order.status);
                statement.setTimestamp(3, updated_on);
                statement.setLong(4, order.id);

                statement.addBatch();
            }

            statement.executeBatch();
            statement.close();
        } catch (Exception e) {
            return Either.left(e);
        }

        return Either.right(null);
    }
}
