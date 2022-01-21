package com.nicolas.ordersapi.domain.usecases;

import java.math.BigDecimal;

import com.nicolas.ordersapi.core.IUsecase;
import com.nicolas.ordersapi.domain.entities.UserEntity;
import com.nicolas.ordersapi.domain.repositories.IUserRepository;

import io.vavr.control.Either;

public class GetOrCreateUserUsecase implements IUsecase<UserEntity, UserEntity> {
    private IUserRepository repository;

    public GetOrCreateUserUsecase(IUserRepository repository) {
        this.repository = repository;
    }

    @Override
    public Either<Exception, UserEntity>  call(UserEntity user)  {
        // Search for user 
        var result = repository.getUser(user);
        if (result.isLeft()) return result;
        
        // Return found user
        if (result.get().id != null) {
            return Either.right(result.get());
        }

        // Create user if no match was found 
        UserEntity newUser = new UserEntity();
        newUser.username = user.username;
        newUser.dollar_balance = new BigDecimal(10000);

        var userResult = repository.createUser(newUser);

        if (userResult.isLeft())
            return Either.left(userResult.getLeft());
        
        // Get created user
        result = repository.getUser(user);
        return result;
    }
}
