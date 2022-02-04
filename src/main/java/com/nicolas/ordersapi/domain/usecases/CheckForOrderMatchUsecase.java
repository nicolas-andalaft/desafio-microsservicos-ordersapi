package com.nicolas.ordersapi.domain.usecases;

import java.math.BigDecimal;

import com.nicolas.ordersapi.core.IUsecase;
import com.nicolas.ordersapi.domain.entities.OrderEntity;
import com.nicolas.ordersapi.domain.entities.UserEntity;
import com.nicolas.ordersapi.domain.entities.UserStockBalanceEntity;
import com.nicolas.ordersapi.domain.repositories.IOrderRepository;
import com.nicolas.ordersapi.domain.repositories.IUserRepository;
import com.nicolas.ordersapi.domain.repositories.IUserStockBalanceRepository;

import io.vavr.collection.List;
import io.vavr.control.Either;

public class CheckForOrderMatchUsecase implements IUsecase<OrderEntity, Object>{
    private IOrderRepository orderRepository;
    private IUserRepository userRepository;
    private IUserStockBalanceRepository userStockBalanceRepository;

    public CheckForOrderMatchUsecase(
        IOrderRepository orderRepository, 
        IUserRepository userRepository, 
        IUserStockBalanceRepository userStockBalanceRepository
    ) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.userStockBalanceRepository = userStockBalanceRepository;
    }

    // Transaction values
    long transaction_volume;
    double transaction_balance;

    @Override
    public Either<Exception, Object> call(OrderEntity order) {
        var response = orderRepository.getOrderMatches(order);
        if (response.isLeft())
            return Either.left(response.getLeft());

        var matches = response.get(); 
            
        // Return null if no match was found
        if (matches.size() == 0)
            return Either.right(null);

        var match = matches.get(0);
        var orders = List.ofAll(matches).append(order);
        
        // Set transaction values
        transaction_volume = Math.min(match.volume, order.volume);
        transaction_balance = Math.min(match.price.doubleValue(), order.price.doubleValue());
        transaction_balance *= transaction_volume;

        var updateOrdersResult = updateOrders(order, match);
        if (updateOrdersResult.isLeft())
            return Either.left(updateOrdersResult.getLeft());


        var updateUserStockBalancesResult = updateUserStockBalances(orders);
        if (updateUserStockBalancesResult.isLeft())
            return Either.left(updateUserStockBalancesResult.getLeft());

        return updateUsers(orders);
    }

    private Either<Exception, Object> updateOrders(OrderEntity newOrder, OrderEntity match) {
        // Volume and Dollar balance of current transaction
        long transactional_volume = Math.min(match.volume, newOrder.volume);

        // Deactivates order if all volume was used
        newOrder.status = transactional_volume == newOrder.volume ? 0 : 1;
        match.status = transactional_volume == match.volume ? 0 : 1;

        newOrder.volume = newOrder.volume - transactional_volume;
        match.volume = match.volume - transactional_volume;

        // Update order status
        var orderUpdate = orderRepository.updateOrders(List.of(match, newOrder).asJava());
        return orderUpdate.mapLeft((exceptions) -> exceptions.get(0)).map((e) -> null);
    }

    private Either<Exception, Object> updateUserStockBalances(List<OrderEntity> orders) {
        Either<Exception, UserStockBalanceEntity> balanceUpdate = Either.right(null);

        var userStockBalance = new UserStockBalanceEntity();

        for (OrderEntity order : orders) {
            userStockBalance.id_user = order.id_user;
            userStockBalance.id_stock = order.id_stock;
            userStockBalance.stock_name = order.stock_name;
            userStockBalance.stock_symbol = order.stock_symbol;
            userStockBalance.volume = transaction_volume;

            // Remove volume if order is of type sell
            if (order.type == 1)
                userStockBalance.volume *= -1;
            
            balanceUpdate = userStockBalanceRepository.createOrUpdateBalance(userStockBalance);
        }

        return balanceUpdate.map((e) -> (Object)e);
    }

    private Either<Exception, Object> updateUsers(List<OrderEntity> orders) {
        Either<Exception, UserEntity> userUpdate = Either.right(null);

        var user = new UserEntity();

        for (OrderEntity order : orders) {
            user.id = order.id_user;
            user.dollar_balance = BigDecimal.valueOf(transaction_balance);

            // Remove money from user if it is a buy order
            if (order.type == 0)
                user.dollar_balance = user.dollar_balance.negate();

            userUpdate = userRepository.adjustDollarBalance(user);
        }

        return userUpdate.map((e) -> (Object)e);      
    }
}
