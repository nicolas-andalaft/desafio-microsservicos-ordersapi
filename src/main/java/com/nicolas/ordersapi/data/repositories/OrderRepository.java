package com.nicolas.ordersapi.data.repositories;

import java.util.ArrayList;
import java.util.List;

import com.nicolas.ordersapi.data.datasources.IOrderDatasource;
import com.nicolas.ordersapi.domain.entities.OrderEntity;
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
        return datasource.getUserOrders(user).map((list) -> list.asJava());
    }
    
    @Override
    public Either<Exception, List<OrderEntity>> getOrderMatches(OrderEntity order) {
        return datasource.getOrderMatches(order).map((list) -> list.asJava());
    }

    @Override
    public Either<List<Exception>, List<OrderEntity>> updateOrders(List<OrderEntity> orders) {
        var updatedOrders = new ArrayList<OrderEntity>();
        var exceptionList = new ArrayList<Exception>();

        Either<Exception, OrderEntity> result_aux;

        for (OrderEntity order : orders) {
            result_aux = datasource.updateOrder(order);
            if (result_aux.isLeft())
                exceptionList.add(result_aux.getLeft());
            else
                updatedOrders.add(result_aux.get());
        }

        if (!exceptionList.isEmpty())
            return Either.left(exceptionList);
        else
            return Either.right(updatedOrders);
    }
}
