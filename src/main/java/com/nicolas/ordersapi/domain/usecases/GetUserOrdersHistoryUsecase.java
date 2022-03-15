package com.nicolas.ordersapi.domain.usecases;

import java.util.List;

import com.nicolas.ordersapi.core.IUsecase;
import com.nicolas.ordersapi.domain.entities.OrderHistoryEntity;
import com.nicolas.ordersapi.domain.repositories.IOrderRepository;

import io.vavr.control.Either;


public class GetUserOrdersHistoryUsecase implements IUsecase<OrderHistoryEntity, List<OrderHistoryEntity>>{
    private IOrderRepository repository;

    public GetUserOrdersHistoryUsecase(IOrderRepository orderRepository) {
        repository = orderRepository;
    }

    @Override
    public Either<Exception, List<OrderHistoryEntity>> call(OrderHistoryEntity orderHistory) {
        return repository.getUserOrdersHistory(orderHistory.order_user, orderHistory.status).map(list -> list == null ? null : list.asJava());
    }
    
}
