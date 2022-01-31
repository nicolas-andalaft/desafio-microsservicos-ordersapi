package com.nicolas.ordersapi.domain.usecases;

import com.nicolas.ordersapi.core.IUsecase;
import com.nicolas.ordersapi.domain.entities.OrderEntity;
import com.nicolas.ordersapi.domain.entities.StockEntity;
import com.nicolas.ordersapi.domain.entities.UserStockBalanceEntity;
import com.nicolas.ordersapi.domain.repositories.IOrderRepository;
import com.nicolas.ordersapi.domain.repositories.IStockRepository;
import com.nicolas.ordersapi.domain.repositories.IUserStockBalanceRepository;

import io.vavr.control.Either;

public class CreateOrderUsecase implements IUsecase<OrderEntity, OrderEntity> {
    private IOrderRepository orderRepository;
    private IUserStockBalanceRepository userStockBalanceRepository;
    private IStockRepository stockRepository;

    public CreateOrderUsecase(
        IOrderRepository orderRepository, 
        IUserStockBalanceRepository userStockBalanceRepository, 
        IStockRepository stockRepository
        ) {
        this.orderRepository = orderRepository;
        this.userStockBalanceRepository = userStockBalanceRepository;
        this.stockRepository = stockRepository;
    }

    @Override
    public Either<Exception, OrderEntity> call(OrderEntity order) {
        if (!(order.type == 0 || order.type == 1))
            return Either.left(new Exception("type property incorrect"));

        // Get correct stock name and symbol
        var stockResult = getStockData(order.id_stock);
        if (stockResult.isLeft())
            return Either.left(stockResult.getLeft());

        order.stock_name = stockResult.get().stock_name;
        order.stock_symbol = stockResult.get().stock_symbol;

        // Create order
        var orderResult = createOrder(order);
        if (orderResult.isLeft())
            return Either.left(orderResult.getLeft());
        

        // Create user stock balance
        var userStockBalanceResult = createUserStockBalance(order);
        if (userStockBalanceResult.isLeft())
            return Either.left(userStockBalanceResult.getLeft());

        // Return updated order if no exception caught
        return Either.right(orderResult.get());
    }  

    private Either<Exception, StockEntity> getStockData(Long id_stock) {
        var stock = new StockEntity();
        stock.id = id_stock;

        return stockRepository.getStock(stock);
    }

    private Either<Exception, OrderEntity> createOrder(OrderEntity order) {
        // "open" status default value
        order.status = 1;

        return orderRepository.createOrder(order);
    }

    private Either<Exception, UserStockBalanceEntity> createUserStockBalance(OrderEntity order) {
        var userStockBalance = new UserStockBalanceEntity();
        userStockBalance.id_user = order.id_user;
        userStockBalance.id_stock = order.id_stock;
        userStockBalance.stock_symbol = order.stock_symbol;
        userStockBalance.stock_name = order.stock_name;
        // Make sure user doesn't get volume on buy order
        userStockBalance.volume = order.type == 1 ? order.volume : 0;

        return userStockBalanceRepository.createOrUpdateUserStockBalanceFromUserOfStock(userStockBalance);
    }
}
