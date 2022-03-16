package com.nicolas.ordersapi.domain.entities;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class OrderEntity {
    protected Long id;
    protected Long idUser;
    protected Long idStock;
    protected String stockSymbol;
    protected String stockName;
    protected Long volume;
    protected BigDecimal price;
    protected Integer type;
    protected Integer status;
    protected Timestamp createdOn;
    protected Timestamp updatedOn;

    public OrderEntity() {}

    public OrderEntity(Long id) {
        this.id = id;
    }

    public Long getId() { return this.id; }
    public Long getIdUser() { return this.idUser; }
    public Long getIdStock() { return this.idStock; }
    public String getStockSymbol() { return this.stockSymbol; }
    public String getStockName() { return this.stockName; }
    public Long getVolume() { return this.volume; }
    public BigDecimal getPrice() { return this.price; }
    public Integer getType() { return this.type; }
    public Integer getStatus() { return this.status; }
    public Timestamp getCreatedOn() { return this.createdOn; }
    public Timestamp getUpdatedOn() { return this.updatedOn; }

    public void setId(Long id) { this.id = id; }
    public void setIdUser(Long idUser) { this.idUser = idUser; }
    public void setIdStock(Long idStock) { this.idStock = idStock; }
    public void setStockSymbol(String stockSymbol) { this.stockSymbol = stockSymbol; }
    public void setStockName(String stockName) { this.stockName = stockName; }
    public void setVolume(Long volume) { this.volume = volume; }
    public void setPrice(BigDecimal price) { this.price = price; }
    public void setType(Integer type) { this.type = type; }
    public void setStatus(Integer status) { this.status = status; }
    public void setCreatedOn(Timestamp createdOn) { this.createdOn = createdOn; }
    public void setUpdatedOn(Timestamp updatedOn) { this.updatedOn = updatedOn; }

    public void adjustVolume(Long value) {
        this.volume += value;
    }
}
