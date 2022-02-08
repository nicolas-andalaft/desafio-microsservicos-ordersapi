package com.nicolas.ordersapi.data.repositories;

import com.nicolas.ordersapi.data.datasources.IOrderDatasource;
import com.nicolas.ordersapi.domain.entities.OrderEntity;
import com.nicolas.ordersapi.domain.entities.UserEntity;
import com.nicolas.ordersapi.domain.repositories.IOrderRepository;

import io.vavr.collection.List;
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
        return datasource.getUserOrders(user);
    }
    
    @Override
    public Either<Exception, List<OrderEntity>> getOrderMatches(OrderEntity order) {
        return datasource.getOrderMatches(order);
    }

    @Override
    public Either<Exception, Object> updateOrders(List<OrderEntity> orders) {
        return datasource.updateOrders(orders);
    }
}
