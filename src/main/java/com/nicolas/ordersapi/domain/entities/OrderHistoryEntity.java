package com.nicolas.ordersapi.domain.entities;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class OrderHistoryEntity {
    protected Long id;
    protected UserEntity orderUser;
    protected UserEntity matchUser;
    protected OrderEntity order;
    protected OrderEntity match;
    protected Long transactionVolume;
    protected BigDecimal transactionPrice;
    protected Integer status;
    protected Timestamp createdOn;

    public OrderHistoryEntity() {}

    public OrderHistoryEntity(Long id) {
        this.id = id;
    }

    public Long getId() { return this.id; }
    public UserEntity getOrderUser() { return this.orderUser; }
    public UserEntity getMatchUser() { return this.matchUser; }
    public OrderEntity getOrder() { return this.order; }
    public OrderEntity getMatch() { return this.match; }
    public Long getTransactionVolume() { return this.transactionVolume; }
    public BigDecimal getTransactionPrice() { return this.transactionPrice; }
    public Integer getStatus() { return this.status; }
    public Timestamp getCreatedOn() { return this.createdOn; }

    public void setId(Long id) { this.id = id; }
    public void setOrderUser(UserEntity orderUser) { this.orderUser = orderUser; }
    public void setMatchUser(UserEntity matchUser) { this.matchUser = matchUser; }
    public void setOrder(OrderEntity order) { this.order = order; }
    public void setMatch(OrderEntity match) { this.match = match; }
    public void setTransactionVolume(Long transactionVolume) { this.transactionVolume = transactionVolume; }
    public void setTransactionPrice(BigDecimal transactionPrice) { this.transactionPrice = transactionPrice; }
    public void setStatus(Integer status) { this.status = status; }
    public void setCreatedOn(Timestamp createdOn) { this.createdOn = createdOn; }

    public Long getOrderUserId() { return this.orderUser == null ? null : this.orderUser.id; }
    public Long getMatchUserId() { return this.matchUser == null ? null : this.matchUser.id; }
    public Long getOrderId() { return this.order == null ? null : this.order.id; }
    public Long getMatchId() { return this.match == null ? null : this.match.id; }

    public void setOrderUser(Long id) { 
        this.orderUser = new UserEntity(id); 
    }
    public void setMatchUser(Long id) { 
        this.matchUser = new UserEntity(id);
    }
    public void setOrder(Long id) { 
        this.order = new OrderEntity(id); 
    }
    public void setMatch(Long id) { 
        this.match = new OrderEntity(id); 
    }
}
