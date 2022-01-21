package com.nicolas.ordersapi.domain.repositories;

import com.nicolas.ordersapi.domain.entities.OrderEntity;

import io.vavr.control.Either;

public interface IOrderRepository {
    public Either<Exception, Integer> createOrder(OrderEntity order);
    
}
