package com.nicolas.ordersapi.data.datasources;

import com.nicolas.ordersapi.domain.entities.OrderEntity;
import com.nicolas.ordersapi.domain.entities.OrderHistoryEntity;
import com.nicolas.ordersapi.domain.entities.StockEntity;
import com.nicolas.ordersapi.domain.entities.UserEntity;

import io.vavr.collection.List;
import io.vavr.control.Either;

public interface IOrderDatasource {
    public Either<Exception, OrderEntity> createOrder(OrderEntity order);
    public Either<Exception, List<OrderEntity>> getUserOrders(UserEntity user);
    public Either<Exception, List<OrderEntity>> getOrderMatches(OrderEntity order);
    public Either<Exception, Object> updateOrders(List<OrderEntity> orders);
    public Either<Exception, OrderEntity> switchOrderStatus(OrderEntity order);
    public Either<Exception, StockEntity> getStockBidAsk(StockEntity stock);
    public Either<Exception, OrderHistoryEntity> createOrderHistory(OrderHistoryEntity orderHistory);
    public Either<Exception, List<OrderHistoryEntity>> getUserOrdersHistory(UserEntity user, Integer status);
    public Either<Exception, OrderHistoryEntity> switchOrderHistoryStatus(OrderHistoryEntity orderHistory);
}
