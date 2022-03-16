package com.nicolas.ordersapi.data.repositories;

import java.util.List;

import com.nicolas.ordersapi.data.datasources.IOrderDatasource;
import com.nicolas.ordersapi.domain.entities.OrderEntity;
import com.nicolas.ordersapi.domain.entities.OrderHistoryEntity;
import com.nicolas.ordersapi.domain.entities.StockEntity;
import com.nicolas.ordersapi.domain.entities.UserEntity;
import com.nicolas.ordersapi.domain.repositories.IOrderRepository;

import io.vavr.control.Either;

public class OrderRepository implements IOrderRepository {
    private IOrderDatasource datasource;

    public OrderRepository(IOrderDatasource orderDatasource) {
        datasource = orderDatasource;
    }

    @Override
    public Either<Exception, OrderEntity> createOrder(OrderEntity order) {
        return datasource.createOrder(order);
    }

    @Override
    public Either<Exception, List<OrderEntity>> getUserOrders(UserEntity user) {
        return datasource.getUserOrders(user).map(io.vavr.collection.List::asJava);
    }
    
    @Override
    public Either<Exception, List<OrderEntity>> getOrderMatches(OrderEntity order) {
        return datasource.getOrderMatches(order).map(io.vavr.collection.List::asJava);
    }

    @Override
    public Either<Exception, Object> updateOrders(List<OrderEntity> orders) {
        return datasource.updateOrders(io.vavr.collection.List.ofAll(orders));
    }

    @Override
    public Either<Exception, OrderEntity> switchOrderStatus(OrderEntity order) {
        return datasource.switchOrderStatus(order);
    }

    @Override
    public Either<Exception, StockEntity> getStockBidAsk(StockEntity stock) {
        return datasource.getStockBidAsk(stock);
    }

    @Override
    public Either<Exception, OrderHistoryEntity> createOrderHistory(OrderHistoryEntity orderHistory) {
        return datasource.createOrderHistory(orderHistory);
    }

    @Override
    public Either<Exception, List<OrderHistoryEntity>> getUserOrdersHistory(UserEntity user, Integer status) {
        return datasource.getUserOrdersHistory(user, status).map(io.vavr.collection.List::asJava);
    }

    @Override
    public Either<Exception, OrderHistoryEntity> switchOrderHistoryStatus(OrderHistoryEntity orderHistory) {
        return datasource.switchOrderHistoryStatus(orderHistory);
    }
}
