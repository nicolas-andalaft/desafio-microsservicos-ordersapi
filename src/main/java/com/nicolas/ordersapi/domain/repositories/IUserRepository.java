package com.nicolas.ordersapi.domain.repositories;

import com.nicolas.ordersapi.domain.entities.UserEntity;
import io.vavr.control.Either;

public interface IUserRepository {
    public Either<Exception, UserEntity> getUser(UserEntity user);
    public Either<Exception, Integer> createUser(UserEntity user);
    public Either<Exception, Integer> adjustDollarBalance(UserEntity user);
}
