package com.nicolas.ordersapi.data.repositories;

import java.util.Map;

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
        Either<Exception, Map<String, Object>> response = datasource.getUser(user.username);
        if (response.isLeft())
            return Either.left(response.getLeft());
        else {
            UserEntity result = UserModel.fromMap(response.get());
            return Either.right(result);
        }
    }

    @Override
    public Either<Exception, Boolean> createUser(UserEntity user) {
        return datasource.createUser(UserModel.toMap((UserModel)user));
    }
}
