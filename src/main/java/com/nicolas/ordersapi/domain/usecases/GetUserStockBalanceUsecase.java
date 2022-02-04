package com.nicolas.ordersapi.domain.usecases;

import java.util.List;

import com.nicolas.ordersapi.core.IUsecase;
import com.nicolas.ordersapi.domain.entities.UserEntity;
import com.nicolas.ordersapi.domain.entities.UserStockBalanceEntity;
import com.nicolas.ordersapi.domain.repositories.IUserStockBalanceRepository;

import io.vavr.control.Either;

public class GetUserStockBalanceUsecase implements IUsecase<UserEntity, List<UserStockBalanceEntity>>{
    private IUserStockBalanceRepository repository;

    public GetUserStockBalanceUsecase(IUserStockBalanceRepository userStockBalanceRepository) {
        repository = userStockBalanceRepository;
    }

    @Override
    public Either<Exception, List<UserStockBalanceEntity>> call(UserEntity user) {
        return repository.getUserBalance(user);
    }
}
