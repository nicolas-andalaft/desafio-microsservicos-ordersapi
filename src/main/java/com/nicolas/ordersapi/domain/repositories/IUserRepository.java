package com.nicolas.ordersapi.domain.repositories;

import com.nicolas.ordersapi.domain.entities.UserEntity;
import io.vavr.control.Either;

public interface IUserRepository {
    public Either<Exception, UserEntity> getUser(UserEntity user);
    public Either<Exception, UserEntity> createUser(UserEntity user);
    public Either<Exception, UserEntity> adjustDollarBalance(UserEntity user);
}
