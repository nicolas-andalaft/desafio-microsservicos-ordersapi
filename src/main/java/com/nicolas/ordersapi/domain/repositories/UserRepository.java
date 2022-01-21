package com.nicolas.ordersapi.domain.repositories;

import com.nicolas.ordersapi.domain.entities.UserEntity;
import io.vavr.control.Either;

public abstract class UserRepository {
    public abstract Either<Exception, UserEntity> getUser(UserEntity user);

    public abstract Either<Exception, Boolean> createUser(UserEntity user);
}
