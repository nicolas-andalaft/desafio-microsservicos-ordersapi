package com.nicolas.ordersapi.data.datasources;

import com.nicolas.ordersapi.domain.entities.UserEntity;

import io.vavr.control.Either;

public interface IUserDatasource {
    public Either<Exception, UserEntity> getUser(UserEntity user);
    public Either<Exception, Integer> createUser(UserEntity user);
    public Either<Exception, Integer> adjustDollarBalance(UserEntity user);
}
