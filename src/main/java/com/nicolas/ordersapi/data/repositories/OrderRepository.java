package com.nicolas.ordersapi.data.repositories;

import com.nicolas.ordersapi.data.datasources.IOrderDatasource;
import com.nicolas.ordersapi.data.models.OrderModel;
import com.nicolas.ordersapi.domain.entities.OrderEntity;
import com.nicolas.ordersapi.domain.repositories.IOrderRepository;

import io.vavr.control.Either;

public class OrderRepository implements IOrderRepository {
    private IOrderDatasource datasource;

    public OrderRepository(IOrderDatasource orderDatasource) {
        datasource = orderDatasource;
    }

    @Override
    public Either<Exception, Integer> createOrder(OrderEntity order) {
        return datasource.createOrder((OrderModel)order);
    }
}
