package com.nicolas.ordersapi.domain.usecases;

import java.math.BigDecimal;

import com.nicolas.ordersapi.core.IUsecase;
import com.nicolas.ordersapi.domain.entities.StockEntity;
import com.nicolas.ordersapi.domain.entities.UserEntity;
import com.nicolas.ordersapi.domain.entities.UserStockBalanceEntity;
import com.nicolas.ordersapi.domain.repositories.IStockRepository;
import com.nicolas.ordersapi.domain.repositories.IUserRepository;
import com.nicolas.ordersapi.domain.repositories.IUserStockBalanceRepository;

import io.vavr.control.Either;

public class GetOrCreateUserUsecase implements IUsecase<UserEntity, UserEntity> {
    private IUserRepository userRepository;
    private IStockRepository stockRepository;
    private IUserStockBalanceRepository userStockBalanceRepository;

    public GetOrCreateUserUsecase(
        IUserRepository userRepository, 
        IStockRepository stockRepository,
        IUserStockBalanceRepository userStockBalanceRepository
    ) {
        this.userRepository = userRepository;
        this.stockRepository = stockRepository;
        this.userStockBalanceRepository = userStockBalanceRepository;
    }

    @Override
    public Either<Exception, UserEntity>  call(UserEntity user)  {
        // Search for user 
        var result = userRepository.getUser(user);
        if (result.isLeft()) return result;
        
        // Return found user
        if (result.get() != null && result.get().id != null) {
            return Either.right(result.get());
        }

        // Create user if no match was found 
        UserEntity newUser = new UserEntity();
        newUser.username = user.username;
        newUser.dollar_balance = new BigDecimal(10000);

        var userResult = userRepository.createUser(newUser);

        if (userResult.isLeft())
            return Either.left(userResult.getLeft());
        
        // Get created user
        userResult = userRepository.getUser(user);
        if (userResult.isLeft())
            return Either.left(userResult.getLeft());

        var randomStocks = giveRandomStockBalances(userResult.get());
        if (randomStocks.isLeft())
            return Either.left(randomStocks.getLeft());

        return Either.right(userResult.get());
    }

    private Either<Exception, Object> giveRandomStockBalances(UserEntity user) {
        // Default values
        int stockBalancesQty = 5;
        long stocksVolume = 100;

        // Get random stocks
        var stocksResponse = stockRepository.getRandomStocks(stockBalancesQty);
        if (stocksResponse.isLeft())
            return Either.left(stocksResponse.getLeft());

        var stocksList = stocksResponse.get();
        var userStockBalance = new UserStockBalanceEntity();

        Exception exception = null;
        Either<Exception, ?> balanceResult;

        // Insert userstockbalance
        for (StockEntity stock : stocksList) {
            userStockBalance.id_user = user.id;
            userStockBalance.id_stock = stock.id;
            userStockBalance.stock_symbol = stock.stock_symbol;
            userStockBalance.stock_name = stock.stock_name;
            userStockBalance.volume = stocksVolume;

            balanceResult = userStockBalanceRepository.createOrUpdateUserStockBalanceFromUserOfStock(userStockBalance);
            // Get exception if it happens
            if (balanceResult.isLeft())
                exception = balanceResult.getLeft();
        }

        if (exception != null)
            return Either.left(exception);
        
        return Either.right(null);
    }
}
