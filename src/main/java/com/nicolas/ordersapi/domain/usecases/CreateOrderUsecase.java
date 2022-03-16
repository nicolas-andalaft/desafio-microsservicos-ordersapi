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
        if (!(order.getType() == 0 || order.getType() == 1))
            return Either.left(new Exception("type property incorrect"));

        // Check if user has said stock to sell
        if (order.getType() == 1) {
            var result = userHasStock(order);
            if (result.isLeft()) 
                return Either.left(result.getLeft());
        }

        // Get correct stock name and symbol
        var idStock = order.getIdStock();
        var stockResult = stockRepository.getStock(new StockEntity(idStock));
        if (stockResult.isLeft())
            return Either.left(stockResult.getLeft());

        var stock = stockResult.get();
        order.setStockName(stock.getStockName());
        order.setStockSymbol(stock.getStockSymbol());

        // Return created order
        var orderResult = createOrder(order);
        if (orderResult.isLeft())
            return Either.left(orderResult.getLeft());
        
        order = orderResult.get();

        // Update Stock bid/ask
        var bidAskResult = stockRepository.tryUpdateBidAsk(order);
        if (bidAskResult.isLeft())
            return Either.left(bidAskResult.getLeft());

        return Either.right(order);
    }  

    private Either<Exception, Object> userHasStock(OrderEntity order) {
        var userStockBalance = new UserStockBalanceEntity();
        userStockBalance.setIdUser(order.getIdUser());
        userStockBalance.setIdStock(order.getIdStock());

        var result = userStockBalanceRepository.getUserStockBalance(userStockBalance);
        if (result.isLeft())
            return Either.left(result.getLeft());
            
        if (result.get() == null)
            return Either.left(new Exception("user does not own stock"));

        return Either.right(null);
    }

    private Either<Exception, OrderEntity> createOrder(OrderEntity order) {
        // "open" status default value
        order.setStatus(1);

        return orderRepository.createOrder(order);
    }
}
