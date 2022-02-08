package com.nicolas.ordersapi.domain.usecases;

import java.math.BigDecimal;
import java.util.ArrayList;

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
    double transaction_price;

    @Override
    public Either<Exception, Object> call(OrderEntity order) {
        var response = orderRepository.getOrderMatches(order);
        if (response.isLeft())
            return Either.left(response.getLeft());

        var matches = response.get(); 
            
        // Return false if no match was found
        if (matches.size() == 0)
            return Either.right(false);

        Either<Exception, Object> result;
        for (int i = 0; order.status == 1 && i < matches.size(); i++) {

            result = checkMatch(order, matches.get(i));
            if (result.isLeft())
                return result;
        }

        return Either.right(true);
    }

    private Either<Exception, Object> checkMatch(OrderEntity order, OrderEntity match) {
        // Set transaction values
        transaction_volume = Math.min(match.volume, order.volume);
        transaction_price = Math.min(match.price.doubleValue(), order.price.doubleValue());
        transaction_price *= transaction_volume;

        Exception anyException = null;

        // Adjust Orders
        var updateOrders = adjustOrders(order, match);
        if (updateOrders.isLeft())
            anyException = updateOrders.getLeft();

        // Adjust Balances
        var updateBalances = adjustBalances(order, match);
        if (updateBalances.isLeft())
            anyException = updateBalances.getLeft();

        // Adjust Dollar balance
        var updateDollar = adjustDollarBalance(order, match);
        if (updateDollar.isLeft())
            anyException = updateDollar.getLeft();

        if (anyException != null)
            return Either.left(anyException);

        return Either.right(null);
        
    }

    private Either<Exception, Object> adjustOrders(OrderEntity order, OrderEntity match) {
        // Deactivates order if all volume was used
        order.status = transaction_volume == order.volume ? 0 : 1;
        match.status = transaction_volume == match.volume ? 0 : 1;

        // Remove used volume
        order.volume -= transaction_volume;
        match.volume -= transaction_volume;

        return orderRepository.updateOrders(List.of(order, match));
    }

    private Either<Exception, Object> adjustBalances(OrderEntity order, OrderEntity match) {
        var orderList = List.of(order, match);
        var balanceList = new ArrayList<UserStockBalanceEntity>();
        
        for (OrderEntity orderEntity : orderList) {
            var balance = new UserStockBalanceEntity();

            balance.id_user = orderEntity.id_user;
            balance.id_stock = orderEntity.id_stock;
            balance.stock_name = orderEntity.stock_name;
            balance.stock_symbol = orderEntity.stock_symbol;
            balance.volume = transaction_volume;
            
            // Remove volume if order is of type sell
            if (orderEntity.type == 1)
                balance.volume *= -1;

            balanceList.add(balance);
        }

        return userStockBalanceRepository.createOrUpdateBalances(List.ofAll(balanceList));
    }

    private Either<Exception, Object> adjustDollarBalance(OrderEntity order, OrderEntity match) {
        var orderList = List.of(order, match);
        Exception anyException = null;
        Either<Exception, ?> result;
        
        for (OrderEntity orderEntity : orderList) {
            var user = new UserEntity();

            user.id = orderEntity.id_user;
            user.dollar_balance = BigDecimal.valueOf(transaction_price);

            // Remove dollar balance if order is of type buy
            if (orderEntity.type == 0)
            user.dollar_balance = user.dollar_balance.negate();

            result = userRepository.adjustDollarBalance(user);
            if (result.isLeft())
                anyException = result.getLeft();
        }

        if (anyException != null)
            return Either.left(anyException);

        return Either.right(null);
    }
}
