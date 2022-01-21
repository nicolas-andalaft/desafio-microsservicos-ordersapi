package com.nicolas.ordersapi.data.repositories;

import java.util.Map;

import com.nicolas.ordersapi.data.datasources.IUserDatasource;
import com.nicolas.ordersapi.data.mappers.UserMapper;
import com.nicolas.ordersapi.domain.entities.UserEntity;
import com.nicolas.ordersapi.domain.repositories.UserRepository;
import io.vavr.control.Either;

public class UserRepositoryImplementation extends UserRepository {
    private IUserDatasource datasource;

    public UserRepositoryImplementation(IUserDatasource userDatasource) {
        datasource = userDatasource;
    }

    @Override
    public Either<Exception, UserEntity> getUser(UserEntity user) {
        Either<Exception, Map<String, Object>> response = datasource.getUser(user.username);
        if (response.isLeft())
            return Either.left(response.getLeft());
        else {
            UserEntity result = UserMapper.fromMap(response.get());
            return Either.right(result);
        }
    }

    @Override
    public Either<Exception, Boolean> createUser(UserEntity user) {
        return datasource.createUser(UserMapper.toMap(user));
        
    }
    
}
