package com.nicolas.ordersapi.domain.usecases;

import com.nicolas.ordersapi.core.IUsecase;
import com.nicolas.ordersapi.domain.entities.OrderEntity;
import com.nicolas.ordersapi.domain.entities.StockEntity;
import com.nicolas.ordersapi.domain.repositories.IOrderRepository;
import com.nicolas.ordersapi.domain.repositories.IStockRepository;

import io.vavr.control.Either;

public class SwitchOrderStatusUsecase implements IUsecase<OrderEntity, OrderEntity>{
    private IOrderRepository orderRepository;
    private IStockRepository stockRepository;

    public SwitchOrderStatusUsecase(IOrderRepository orderRepository, IStockRepository stockRepository) {
        this.orderRepository = orderRepository;
        this.stockRepository = stockRepository;
    }

    @Override
    public Either<Exception, OrderEntity> call(OrderEntity order) {
        var switchResult = orderRepository.switchOrderStatus(order);
        if (switchResult.isLeft())
            return switchResult;

        var stock = new StockEntity(switchResult.get().getId());

        var bidAskResult = orderRepository.getStockBidAsk(stock);
        if (bidAskResult.isLeft())
            return Either.left(bidAskResult.getLeft());

        var newStock = bidAskResult.get();
        newStock.setId(stock.getId());

        var updateBidAskResult = stockRepository.forceUpdateBidAsk(newStock);
        if (updateBidAskResult.isLeft())
            return Either.left(updateBidAskResult.getLeft());

        return switchResult;
    }
}
