package com.nicolas.ordersapi.domain.usecases;

import com.nicolas.ordersapi.core.IUsecase;
import com.nicolas.ordersapi.domain.entities.UserStockBalanceEntity;
import com.nicolas.ordersapi.domain.repositories.IUserStockBalanceRepository;

import io.vavr.control.Either;

public class CreateUserStockBalanceUsecase implements IUsecase<UserStockBalanceEntity, UserStockBalanceEntity> {
    private IUserStockBalanceRepository repository;

    public CreateUserStockBalanceUsecase(IUserStockBalanceRepository repository) {
        this.repository = repository;
    }

    @Override
    public Either<Exception, UserStockBalanceEntity> call(UserStockBalanceEntity userStockBalance) {
        return repository.createUserStockBalance(userStockBalance);
    }
    
}
