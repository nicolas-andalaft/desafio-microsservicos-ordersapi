package com.nicolas.ordersapi.domain.usecases;

import java.math.BigDecimal;
import java.util.ArrayList;

import com.nicolas.ordersapi.core.IUsecase;
import com.nicolas.ordersapi.domain.entities.OrderEntity;
import com.nicolas.ordersapi.domain.entities.OrderHistoryEntity;
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
    private long transactionVolume;
    private double transactionPrice;

    @Override
    public Either<Exception, Object> call(OrderEntity order) {
        var response = orderRepository.getOrderMatches(order);
        if (response.isLeft())
            return Either.left(response.getLeft());

        var matches = response.get(); 
            
        // Return false if no match was found
        if (matches.isEmpty())
            return Either.right(false);

        Either<Exception, Object> result;
        for (int i = 0; order.getStatus() == 1 && i < matches.size(); i++) {

            result = checkMatch(order, matches.get(i));
            if (result.isLeft())
                return result;
        }

        return Either.right(true);
    }

    private Either<Exception, Object> checkMatch(OrderEntity order, OrderEntity match) {
        // Set transaction values
        transactionVolume = Math.min(match.getVolume(), order.getVolume());
        transactionPrice = Math.min(match.getPrice().doubleValue(), order.getPrice().doubleValue());
        transactionPrice *= transactionVolume;

        Exception anyException = null;

        // Adjust Orders
        var updateOrders = adjustOrders(order, match);
        if (updateOrders.isLeft())
            anyException = updateOrders.getLeft();

        // Create Orders History
        var ordersHistory = createOrderHistory(order, match);
        if (ordersHistory.isLeft())
            anyException = ordersHistory.getLeft();

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
        order.setStatus(transactionVolume == order.getVolume() ? 0 : 1);
        match.setStatus(transactionVolume == match.getVolume() ? 0 : 1);

        // Remove used volume
        order.adjustVolume(-transactionVolume);
        match.adjustVolume(-transactionVolume);

        return orderRepository.updateOrders(List.of(order, match).asJava());
    }

    private Either<Exception, Object> adjustBalances(OrderEntity order, OrderEntity match) {
        var orderList = List.of(order, match);
        var balanceList = new ArrayList<UserStockBalanceEntity>();
        
        for (OrderEntity orderEntity : orderList) {
            var balance = new UserStockBalanceEntity();

            balance.setIdUser(orderEntity.getIdUser());
            balance.setIdStock(orderEntity.getIdStock());
            balance.setStockName(orderEntity.getStockName());
            balance.setStockSymbol(orderEntity.getStockSymbol());
            balance.setVolume(transactionVolume);
            
            // Remove volume if order is of type sell
            if (orderEntity.getType() == 1)
                balance.invertVolume();

            balanceList.add(balance);
        }

        return userStockBalanceRepository.createOrUpdateBalances(balanceList);
    }

    private Either<Exception, Object> adjustDollarBalance(OrderEntity order, OrderEntity match) {
        var orderList = List.of(order, match);
        Exception anyException = null;
        Either<Exception, ?> result;
        
        for (OrderEntity orderEntity : orderList) {
            var user = new UserEntity();

            user.setId(orderEntity.getId());
            user.setDollarBalance(BigDecimal.valueOf(transactionPrice));

            // Remove dollar balance if order is of type buy
            if (orderEntity.getType() == 0) 
                user.setDollarBalance(user.getDollarBalance().negate());           

            result = userRepository.adjustDollarBalance(user);
            if (result.isLeft())
                anyException = result.getLeft();
        }

        if (anyException != null)
            return Either.left(anyException);

        return Either.right(null);
    }

    private Either<Exception, OrderHistoryEntity> createOrderHistory(OrderEntity order, OrderEntity match) {
        var orderHistory = new OrderHistoryEntity();
        
        orderHistory.setOrderUser(order.getIdUser());
        orderHistory.setMatchUser(match.getIdUser());
        orderHistory.setOrder(order.getId());
        orderHistory.setMatch(match.getId());
        orderHistory.setTransactionVolume(transactionVolume);
        orderHistory.setTransactionPrice(BigDecimal.valueOf(transactionPrice));
        
        return orderRepository.createOrderHistory(orderHistory);
    }
}
