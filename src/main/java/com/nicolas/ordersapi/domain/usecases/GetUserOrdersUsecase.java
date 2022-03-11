package com.nicolas.ordersapi.domain.usecases;

import java.util.List;

import com.nicolas.ordersapi.core.IUsecase;
import com.nicolas.ordersapi.domain.entities.OrderEntity;
import com.nicolas.ordersapi.domain.entities.UserEntity;
import com.nicolas.ordersapi.domain.repositories.IOrderRepository;

import io.vavr.control.Either;

public class GetUserOrdersUsecase implements IUsecase<UserEntity, List<OrderEntity>>{
    private IOrderRepository repository;

    public GetUserOrdersUsecase(IOrderRepository orderRepository) {
        repository = orderRepository;
    }

    @Override
    public Either<Exception, List<OrderEntity>> call(UserEntity user) {
        return repository.getUserOrders(user).map(list -> list.asJava());
    }
    
}
