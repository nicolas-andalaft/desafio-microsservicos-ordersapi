package com.nicolas.ordersapi.domain.usecases;

import java.math.BigDecimal;

import com.nicolas.ordersapi.core.IUsecase;
import com.nicolas.ordersapi.domain.entities.OrderEntity;
import com.nicolas.ordersapi.domain.entities.UserEntity;
import com.nicolas.ordersapi.domain.entities.UserStockBalanceEntity;
import com.nicolas.ordersapi.domain.repositories.IOrderRepository;
import com.nicolas.ordersapi.domain.repositories.IUserRepository;
import com.nicolas.ordersapi.domain.repositories.IUserStockBalanceRepository;

import io.vavr.control.Either;

public class CreateOrderUsecase implements IUsecase<OrderEntity, Integer> {
    private IOrderRepository orderRepository;
    private IUserStockBalanceRepository userStockBalanceRepository;
    private IUserRepository userRepository;

    public CreateOrderUsecase(
        IOrderRepository orderRepository, 
        IUserStockBalanceRepository userStockBalanceRepository, 
        IUserRepository userRepository
        ) {
        this.orderRepository = orderRepository;
        this.userStockBalanceRepository = userStockBalanceRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Either<Exception, Integer> call(OrderEntity order) {
        if (!(order.type == 0 || order.type == 1))
            return Either.left(new Exception("type property incorrect"));

        // "open" status default value
        order.status = 0;

        var orderResult = orderRepository.createOrder(order);
        if (orderResult.isLeft())
            return Either.left(orderResult.getLeft());

        // User stock balance creation
        var userStockBalance = new UserStockBalanceEntity();
        userStockBalance.id_user = order.id_user;
        userStockBalance.id_stock = order.id_stock;
        userStockBalance.stock_symbol = order.stock_symbol;
        userStockBalance.stock_name = order.stock_name;
        userStockBalance.volume = order.volume;

        var userStockBalanceResult = userStockBalanceRepository.createOrUpdateUserStockBalanceFromUserOfStock(userStockBalance);
        if (userStockBalanceResult.isLeft())
            return Either.left(userStockBalanceResult.getLeft());

        // User dollar balance update
        var user = new UserEntity();
        user.id = order.id_user;

        // Adjust value = price * volume * -1
        var volume = BigDecimal.valueOf(order.volume);
        user.dollar_balance = order.price.multiply(volume).negate();

        return userRepository.adjustDollarBalance(user);
    }  
}
