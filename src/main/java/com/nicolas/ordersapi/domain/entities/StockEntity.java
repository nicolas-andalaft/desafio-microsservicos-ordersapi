package com.nicolas.ordersapi.domain.entities;

import java.sql.Timestamp;

public class StockEntity {
    protected Long id;
    protected String stockSymbol;
    protected String stockName;
    protected Float askMin;
    protected Float askMax;
    protected Float bidMin;
    protected Float bidMax;
    protected Timestamp createdOn;
    protected Timestamp updatedOn;

    public StockEntity() {}

    public StockEntity(Long id) {
        this.id = id;
    }

    public Long getId() { return this.id; }
    public String getStockSymbol() { return this.stockSymbol; }
    public String getStockName() { return this.stockName; }
    public Float getAskMin() { return this.askMin; }
    public Float getAskMax() { return this.askMax; }
    public Float getBidMin() { return this.bidMin; }
    public Float getBidMax() { return this.bidMax; }
    public Timestamp getCreatedOn() { return this.createdOn; }
    public Timestamp getUpdatedOn() { return this.updatedOn; }

    public void setId(Long id) { this.id = id; }
    public void setStockSymbol(String stockSymbol) { this.stockSymbol = stockSymbol; }
    public void setStockName(String stockName) { this.stockName = stockName; }
    public void setAskMin(Float askMin) { this.askMin = askMin; }
    public void setAskMax(Float askMax) { this.askMax = askMax; }
    public void setBidMin(Float bidMin) { this.bidMin = bidMin; }
    public void setBidMax(Float bidMax) { this.bidMax = bidMax; }
    public void setCreatedOn(Timestamp createdOn) { this.createdOn = createdOn; }
    public void setUpdatedOn(Timestamp updatedOn) { this.updatedOn = updatedOn; }
}
