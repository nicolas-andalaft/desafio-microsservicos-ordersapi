package com.nicolas.ordersapi.domain.usecases;

import com.nicolas.ordersapi.core.IUsecase;
import com.nicolas.ordersapi.domain.entities.OrderEntity;
import com.nicolas.ordersapi.domain.repositories.IOrderRepository;

import io.vavr.control.Either;

public class CreateOrderUsecase implements IUsecase<OrderEntity, Integer> {
    private IOrderRepository repository;

    public CreateOrderUsecase(IOrderRepository orderRepository) {
        repository = orderRepository;
    }

    @Override
    public Either<Exception, Integer> call(OrderEntity order) {
        if (!(order.type == 0 || order.type == 1))
            return Either.left(new Exception("type property incorrect"));

        // "open" status default value
        order.status = 0;
        return repository.createOrder(order);
    }
    
}
