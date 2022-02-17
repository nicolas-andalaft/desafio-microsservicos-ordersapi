package com.nicolas.ordersapi.domain.usecases;

import com.nicolas.ordersapi.core.IUsecase;
import com.nicolas.ordersapi.domain.entities.OrderEntity;
import com.nicolas.ordersapi.domain.repositories.IOrderRepository;

import io.vavr.control.Either;

public class SwitchOrderStatusUsecase implements IUsecase<OrderEntity, OrderEntity>{
    private IOrderRepository repository;

    public SwitchOrderStatusUsecase(IOrderRepository orderRepository) {
        repository = orderRepository;
    }

    @Override
    public Either<Exception, OrderEntity> call(OrderEntity order) {
        return repository.switchOrderStatus(order);
    }
}
