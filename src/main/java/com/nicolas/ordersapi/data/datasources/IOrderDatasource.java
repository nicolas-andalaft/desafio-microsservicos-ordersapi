package com.nicolas.ordersapi.data.datasources;

import com.nicolas.ordersapi.data.models.OrderModel;

import io.vavr.control.Either;

public interface IOrderDatasource {
    public Either<Exception, Integer> createOrder(OrderModel order);
}
