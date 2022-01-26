package com.nicolas.ordersapi.data.datasources;

import com.nicolas.ordersapi.data.models.UserModel;

import io.vavr.control.Either;

public interface IUserDatasource {
    public Either<Exception, UserModel> getUser(UserModel user);
    public Either<Exception, Integer> createUser(UserModel user);
    public Either<Exception, Integer> adjustDollarBalance(UserModel user);
}
