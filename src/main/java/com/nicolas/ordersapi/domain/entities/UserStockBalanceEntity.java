package com.nicolas.ordersapi.domain.entities;

import java.sql.Timestamp;

public class UserStockBalanceEntity {
    protected Long id;
    protected Long idUser;
    protected Long idStock;
    protected String stockSymbol;
    protected String stockName;
    protected Long volume;
    protected Timestamp createdOn;
    protected Timestamp updatedOn;

    public UserStockBalanceEntity() {}

    public UserStockBalanceEntity(Long id) {
        this.id = id;
    }

    public Long getId() { return this.id; }
    public Long getIdUser() { return this.idUser; }
    public Long getIdStock() { return this.idStock; }
    public String getStockSymbol() { return this.stockSymbol; }
    public String getStockName() { return this.stockName; }
    public Long getVolume() { return this.volume; }
    public Timestamp getCreatedOn() { return this.createdOn; }
    public Timestamp getUpdatedOn() { return this.updatedOn; }

    public void setId(Long id) { this.id = id; }
    public void setIdUser(Long idUser) { this.idUser = idUser; }
    public void setIdStock(Long idStock) { this.idStock = idStock; }
    public void setStockSymbol(String stockSymbol) { this.stockSymbol = stockSymbol; }
    public void setStockName(String stockName) { this.stockName = stockName; }
    public void setVolume(Long volume) { this.volume = volume; }
    public void setCreatedOn(Timestamp createdOn) { this.createdOn = createdOn; }
    public void setUpdatedOn(Timestamp updatedOn) { this.updatedOn = updatedOn; }

    public void invertVolume() {
        this.volume *= -1;
    }
}
