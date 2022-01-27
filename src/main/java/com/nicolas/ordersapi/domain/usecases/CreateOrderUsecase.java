package com.nicolas.ordersapi.domain.usecases;

import java.math.BigDecimal;

import com.nicolas.ordersapi.core.IUsecase;
import com.nicolas.ordersapi.domain.entities.OrderEntity;
import com.nicolas.ordersapi.domain.entities.StockEntity;
import com.nicolas.ordersapi.domain.entities.UserStockBalanceEntity;
import com.nicolas.ordersapi.domain.repositories.IOrderRepository;
import com.nicolas.ordersapi.domain.repositories.IStockRepository;
import com.nicolas.ordersapi.domain.repositories.IUserStockBalanceRepository;

import io.vavr.control.Either;

public class CreateOrderUsecase implements IUsecase<OrderEntity, Integer> {
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
    public Either<Exception, Integer> call(OrderEntity order) {
        if (!(order.type == 0 || order.type == 1))
            return Either.left(new Exception("type property incorrect"));

        // "open" status default value
        order.status = 0;

        var stock = new StockEntity();
        stock.id = order.id_stock;

        // Get correct stock name and symbol
        var stockResult = stockRepository.getStock(stock);
        if (stockResult.isLeft())
            return Either.left(stockResult.getLeft());

        order.stock_name = stockResult.get().stock_name;
        order.stock_symbol = stockResult.get().stock_symbol;

        // Create order
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
        return Either.left(userStockBalanceResult.getLeft());
    }  
}
