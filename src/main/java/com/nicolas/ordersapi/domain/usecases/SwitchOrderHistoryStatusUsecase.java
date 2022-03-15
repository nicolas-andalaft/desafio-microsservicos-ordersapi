package com.nicolas.ordersapi.domain.usecases;

import com.nicolas.ordersapi.core.IUsecase;
import com.nicolas.ordersapi.domain.entities.OrderHistoryEntity;
import com.nicolas.ordersapi.domain.repositories.IOrderRepository;

import io.vavr.control.Either;

public class SwitchOrderHistoryStatusUsecase implements IUsecase<OrderHistoryEntity, OrderHistoryEntity>{
    private IOrderRepository repository;

    public SwitchOrderHistoryStatusUsecase(IOrderRepository orderRepository) {
        repository = orderRepository;
    }

    @Override
    public Either<Exception, OrderHistoryEntity> call(OrderHistoryEntity orderHistory) {
        return repository.switchOrderHistoryStatus(orderHistory);
    }
    
}
