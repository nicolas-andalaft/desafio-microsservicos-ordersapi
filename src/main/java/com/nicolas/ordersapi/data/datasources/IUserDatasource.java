package com.nicolas.ordersapi.data.datasources;

import com.nicolas.ordersapi.domain.entities.UserEntity;

import io.vavr.control.Either;

public interface IUserDatasource {
    public Either<Exception, UserEntity> getUser(UserEntity user);
    public Either<Exception, UserEntity> createUser(UserEntity user);
    public Either<Exception, UserEntity> adjustDollarBalance(UserEntity user);
}
