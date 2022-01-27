package com.nicolas.ordersapi.domain.repositories;

import java.util.List;

import com.nicolas.ordersapi.domain.entities.OrderEntity;
import com.nicolas.ordersapi.domain.entities.UserEntity;

import io.vavr.control.Either;

public interface IOrderRepository {
    public Either<Exception, Integer> createOrder(OrderEntity order);
    public Either<Exception, List<OrderEntity>> getUserOrders(UserEntity user);
}
