package com.nicolas.ordersapi.data.datasources.postgre;

import com.nicolas.ordersapi.data.datasources.IOrderDatasource;
import com.nicolas.ordersapi.data.models.OrderHistoryModel;
import com.nicolas.ordersapi.data.models.OrderModel;
import com.nicolas.ordersapi.data.models.StockModel;
import com.nicolas.ordersapi.domain.entities.OrderEntity;
import com.nicolas.ordersapi.domain.entities.OrderHistoryEntity;
import com.nicolas.ordersapi.domain.entities.StockEntity;
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
            tableName, order.getIdUser(), order.getIdStock(), order.getStockSymbol(), order.getStockName(), order.getVolume(), order.getPrice(), order.getType(), order.getStatus());

        return super.execute(sqlString).map(list -> OrderModel.fromMap(list.get(0)));
    }

    @Override
    public Either<Exception, List<OrderEntity>> getUserOrders(UserEntity user) {
        String sqlString = String.format(
            "SELECT * FROM %s WHERE id_user = %s ORDER BY stock_name, stock_symbol", tableName, user.getId());

        return super.execute(sqlString).map(list -> list.map(OrderModel::fromMap));
    }

    @Override
    public Either<Exception, List<OrderEntity>> getOrderMatches(OrderEntity order) {
        String sqlString = String.format(
            "SELECT * FROM %s WHERE id_stock = %s AND type = %s AND price %s %s ORDER BY price, created_on", 
            tableName, order.getIdStock(), order.getType() == 0 ? 1 : 0, order.getType() == 0 ? "<=" : ">=", order.getPrice()
        );

        return super.execute(sqlString).map(list -> list.map(OrderModel::fromMap));
    }

    @Override
    public Either<Exception, Object> updateOrders(List<OrderEntity> orders) {

        String sqlString = String.format(
            "UPDATE %s SET  volume = ?, status = ? WHERE id = ?", 
            tableName
        );

        var conn = PostgreDatasource.getConnection();
        if (conn.isLeft())
            return Either.left(conn.getLeft());

        // Set sql query
        try(var statement = conn.get().prepareStatement(sqlString);) {
            
            // Set values
            for (OrderEntity order : orders) {
                statement.setLong(1, order.getVolume());
                statement.setInt(2, order.getStatus());
                statement.setLong(3, order.getId());

                statement.addBatch();
            }

            statement.executeBatch();

        } catch (Exception e) {
            return Either.left(e);
        }

        return Either.right(null);
    }

    @Override
    public Either<Exception, OrderEntity> switchOrderStatus(OrderEntity order) {
        String sqlString = String.format("""
        UPDATE %s SET status = 
        CASE 
            WHEN status = 0 THEN 1
            ELSE 0
        END
        WHERE id = %s
        RETURNING *""", 
        tableName, order.getId());

        return super.execute(sqlString).map(result -> OrderModel.fromMap(result.get(0)));
    }

    @Override
    public Either<Exception, StockEntity> getStockBidAsk(StockEntity stock) {
        String sqlString = String.format("""
            SELECT 
                MIN(CASE WHEN type = 1 THEN price END) AS bid_min,
                MAX(CASE WHEN type = 1 THEN price END) AS bid_max,
                MIN(CASE WHEN type = 0 THEN price END) AS ask_min,
                MAX(CASE WHEN type = 0 THEN price END) AS ask_max
            FROM %s WHERE id_stock = %s and status = 1""", 
            tableName, stock.getId());

        return super.execute(sqlString).map(list -> StockModel.fromMap(list.get(0)));
    }

    @Override
    public Either<Exception, OrderHistoryEntity> createOrderHistory(OrderHistoryEntity orderHistory) {
        String sqlString = String.format("""
            INSERT INTO orders_history(id_order_user, id_match_user, id_order, id_match, transaction_volume, transaction_price)
            VALUES (%s, %s, %s, %s, %s, %)
            RETURNING *""", 
            orderHistory.getOrderUserId(), orderHistory.getMatchUserId(), orderHistory.getOrderId(), orderHistory.getMatchId(),
            orderHistory.getTransactionVolume(), orderHistory.getTransactionPrice());

        return super.execute(sqlString).map(list -> OrderHistoryModel.fromMap(list.get(0)));
    }

    @Override
    public Either<Exception, List<OrderHistoryEntity>> getUserOrdersHistory(UserEntity user, Integer status) {
        String sqlString = String.format("""
            SELECT 
                H.*, O.stock_symbol, O.stock_name
            FROM orders_history H 
            INNER JOIN user_orders O
                ON O.id = H.id_order
            WHERE H.id_order_user = %s""", 
            user.getId());

        if (status != null)
            sqlString += " AND H.status = " + status;

        return super.execute(sqlString).map(list -> list.map(OrderHistoryModel::fromMap));
    }

    @Override
    public Either<Exception, OrderHistoryEntity> switchOrderHistoryStatus(OrderHistoryEntity orderHistory) {
        String sqlString = String.format("""
            UPDATE orders_history SET status = 
            CASE 
                WHEN status = 0 THEN 1
                ELSE 0
            END
            WHERE id = %s
            RETURNING *""", 
            orderHistory.getId());

        return super.execute(sqlString).map(list -> OrderHistoryModel.fromMap(list.get(0)));
    }
}
