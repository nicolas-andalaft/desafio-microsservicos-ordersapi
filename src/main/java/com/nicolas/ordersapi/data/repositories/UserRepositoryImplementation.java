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
    public Either<Exception, UserEntity> getUser(String email) {
        Either<Exception, Map<String, Object>> result = datasource.getUser(email);
        if (result.isLeft())
            return Either.left(result.getLeft());
        else {
            UserEntity user = UserMapper.fromMap(result.get());
            return Either.right(user);
        }
    }

    @Override
    public Either<Exception, Boolean> createUser(UserEntity user) {
        return datasource.createUser(UserMapper.toMap(user));
        
    }
    
}
