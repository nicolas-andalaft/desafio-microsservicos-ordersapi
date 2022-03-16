package com.nicolas.ordersapi.domain.entities;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class UserEntity {
    protected Long id;
    protected String username;
    protected BigDecimal dollarBalance;
    protected Timestamp createdOn;
    protected Timestamp updatedOn;

    public UserEntity() {}

    public UserEntity(Long id) {
        this.id = id;
    }

    public Long getId() { return this.id; }
    public String getUsername() { return this.username; }
    public BigDecimal getDollarBalance() { return this.dollarBalance; }
    public Timestamp getCreatedOn() { return this.createdOn; }
    public Timestamp getUpdatedOn() { return this.updatedOn; }
    
    public void setId(Long id) { this.id = id; }
    public void setUsername(String username) { this.username = username; }
    public void setDollarBalance(BigDecimal dollarBalance) { this.dollarBalance = dollarBalance; }
    public void setCreatedOn(Timestamp createdOn) { this.createdOn = createdOn; }
    public void setUpdatedOn(Timestamp updatedOn) { this.updatedOn = updatedOn; }
}
