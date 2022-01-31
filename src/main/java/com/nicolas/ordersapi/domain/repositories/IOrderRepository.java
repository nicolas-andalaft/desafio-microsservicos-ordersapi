package com.nicolas.ordersapi.domain.repositories;

import java.util.List;

import com.nicolas.ordersapi.domain.entities.OrderEntity;
import com.nicolas.ordersapi.domain.entities.UserEntity;

import io.vavr.control.Either;

public interface IOrderRepository {
    public Either<Exception, OrderEntity> createOrder(OrderEntity order);
    public Either<Exception, List<OrderEntity>> getUserOrders(UserEntity user);
    public Either<Exception, List<OrderEntity>> getOrderMatches(OrderEntity order);
    public Either<List<Exception>, List<OrderEntity>> updateOrders(List<OrderEntity> orders);
}
