package com.nicolas.ordersapi.data.repositories;

import com.nicolas.ordersapi.data.datasources.IUserDatasource;
import com.nicolas.ordersapi.data.models.UserModel;
import com.nicolas.ordersapi.domain.entities.UserEntity;
import com.nicolas.ordersapi.domain.repositories.IUserRepository;

import io.vavr.control.Either;

public class UserRepository implements IUserRepository {
    private IUserDatasource datasource;

    public UserRepository(IUserDatasource userDatasource) {
        datasource = userDatasource;
    }

    @Override
    public Either<Exception, UserEntity> getUser(UserEntity user) {
        return datasource.getUser(new UserModel(user)).map((e) -> (UserEntity)e);
    }

    @Override
    public Either<Exception, Integer> createUser(UserEntity user) {
        return datasource.createUser(new UserModel(user));
    }

    @Override
    public Either<Exception, Integer> adjustDollarBalance(UserEntity user) {
        return datasource.adjustDollarBalance(new UserModel(user));
    }
}
