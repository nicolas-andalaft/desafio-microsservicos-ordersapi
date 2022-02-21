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

        // Check if user has said stock to sell
        if (order.type == 1) {
            var result = userHasStock(order);
            if (result.isLeft()) 
                return Either.left(result.getLeft());
        }

        // Get correct stock name and symbol
        var stockResult = getStockData(order.id_stock);
        if (stockResult.isLeft())
            return Either.left(stockResult.getLeft());

        order.stock_name = stockResult.get().stock_name;
        order.stock_symbol = stockResult.get().stock_symbol;

        // Return created order
        var orderResult = createOrder(order);
        if (orderResult.isLeft())
        return Either.left(orderResult.getLeft());
        
        order = orderResult.get();

        // Update Stock bid/ask
        var bidAskResult = stockRepository.updateBidAsk(order);
        if (bidAskResult.isLeft())
            return Either.left(bidAskResult.getLeft());

        return Either.right(order);
    }  

    private Either<Exception, Object> userHasStock(OrderEntity order) {
        var userStockBalance = new UserStockBalanceEntity();
        userStockBalance.id_user = order.id_user;
        userStockBalance.id_stock = order.id_stock;

        var result = userStockBalanceRepository.getUserStockBalance(userStockBalance);
        if (result.isLeft())
            return Either.left(result.getLeft());
            
        if (result.get() == null)
            return Either.left(new Exception("user does not own stock"));

        return Either.right(null);
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
}
