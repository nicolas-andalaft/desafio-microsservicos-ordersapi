package com.nicolas.ordersapi.domain.usecases;

import com.nicolas.ordersapi.core.IUsecase;
import com.nicolas.ordersapi.domain.entities.UserStockBalanceEntity;
import com.nicolas.ordersapi.domain.repositories.UserStockBalanceRepository;

import io.vavr.control.Either;

public class GetUserStockBalanceFromUserUsecase implements IUsecase<UserStockBalanceEntity, UserStockBalanceEntity[]> {
    private UserStockBalanceRepository repository;

    public GetUserStockBalanceFromUserUsecase(UserStockBalanceRepository repository) {
        this.repository = repository;
    }

    @Override
    public Either<Exception, UserStockBalanceEntity[]> call(UserStockBalanceEntity userStockBalance) {
        return repository.getUserStockBalanceFromUser(userStockBalance);
    }
    
}
