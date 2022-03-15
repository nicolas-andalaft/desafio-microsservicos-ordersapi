package com.nicolas.ordersapi.domain.entities;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class OrderHistoryEntity {
    public Long id;
    public UserEntity order_user;
    public UserEntity match_user;
    public OrderEntity order;
    public OrderEntity match;
    public Long transaction_volume;
    public BigDecimal transaction_price;
    public Integer status;
    public Timestamp created_on;

    public OrderHistoryEntity() {};

    public void order_user(Long id) {
        var user = new UserEntity();
        user.id = id;
        this.order_user = user;
    }

    public void match_user(Long id) {
        var user = new UserEntity();
        user.id = id;
        this.match_user = user;
    }

    public void order(Long id) {
        var order = new OrderEntity();
        order.id = id;
        this.order = order;
    }

    public void match(Long id) {
        var order = new OrderEntity();
        order.id = id;
        this.order = order;
    }

    public Long id_order_user() {
        return this.order_user == null ? null : this.order_user.id;
    }

    public Long id_match_user() {
        return this.match_user == null ? null : this.match_user.id;
    }

    public Long id_order() {
        return this.order == null ? null : this.order.id;
    }

    public Long id_match() {
        return this.match == null ? null : this.match.id;
    }
}
