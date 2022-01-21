package com.nicolas.ordersapi.domain.entities;

import java.time.LocalDateTime;

public class UserStockBalanceEntity {
    public Long idUser;
    public Long idStock;
    public String stockSymbol;
    public String stockName;
    public Long volume;
    public LocalDateTime createdOn;
    public LocalDateTime updatedOn;

    public UserStockBalanceEntity() {}

    public UserStockBalanceEntity(
        Long idUser,
        Long idStock,
        String stockSymbol,
        String stockName,
        Long volume,
        LocalDateTime createdOn,
        LocalDateTime updatedOn
    ) {
         this.idUser = idUser;
         this.idStock = idStock;
         this.stockSymbol = stockSymbol;
         this.stockName = stockName;
         this.volume = volume;
         this.createdOn = createdOn;
         this.updatedOn = updatedOn;
    }
}
