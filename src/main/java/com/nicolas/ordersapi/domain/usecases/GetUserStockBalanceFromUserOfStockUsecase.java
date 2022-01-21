package com.nicolas.ordersapi.domain.usecases;

import com.nicolas.ordersapi.core.IUsecase;
import com.nicolas.ordersapi.domain.entities.UserStockBalanceEntity;
import com.nicolas.ordersapi.domain.repositories.IUserStockBalanceRepository;

import io.vavr.control.Either;

public class GetUserStockBalanceFromUserOfStockUsecase implements IUsecase<UserStockBalanceEntity, UserStockBalanceEntity[]> {
    private IUserStockBalanceRepository repository;

    public GetUserStockBalanceFromUserOfStockUsecase(IUserStockBalanceRepository repository) {
        this.repository = repository;
    }
    
    @Override
    public Either<Exception, UserStockBalanceEntity[]> call(UserStockBalanceEntity userStockBalance) {
        if (userStockBalance.id_user == null)
            return Either.left(new Exception("user_id could not be found"));
        if (userStockBalance.id_user == null)
            return Either.left(new Exception("stock_id could not be found"));

        var result = repository.getUserStockBalanceFromUserOfStock(userStockBalance);

        if (result.isLeft())
            return Either.left(result.getLeft());
        
        return Either.right(result.get());
    }
    
}
