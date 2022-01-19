package com.nicolas.ordersapi.domain.usecases;

import java.math.BigDecimal;

import com.nicolas.ordersapi.core.IUsecase;
import com.nicolas.ordersapi.domain.entities.UserEntity;
import com.nicolas.ordersapi.domain.repositories.UserRepository;

import io.vavr.control.Either;

public class GetOrCreateUserUsecase implements IUsecase<String, UserEntity> {
    private UserRepository repository;

    public GetOrCreateUserUsecase(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public Either<Exception, UserEntity>  call(String email)  {
        // Search for user 
        var result = repository.getUser(email);
        if (result.isLeft()) return result;
        
        // Return found user
        if (result.get().id != null) {
            return Either.right(result.get());
        }

        // Create user if no match was found
        UserEntity newUser = new UserEntity();
        newUser.username = email;
        newUser.dollarBalance = new BigDecimal(10000);

        var userResult = repository.createUser(newUser);

        if (userResult.isLeft())
            return Either.left(userResult.getLeft());
        
        // Get created user
        result = repository.getUser(email);
        return result;
    }
}
