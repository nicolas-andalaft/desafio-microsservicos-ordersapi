package com.nicolas.ordersapi.data.datasources;

import com.nicolas.ordersapi.domain.entities.OrderEntity;
import com.nicolas.ordersapi.domain.entities.UserEntity;

import io.vavr.collection.List;
import io.vavr.control.Either;

public interface IOrderDatasource {
    public Either<Exception, Integer> createOrder(OrderEntity order);
    public Either<Exception, List<OrderEntity>> getUserOrders(UserEntity user);
}
