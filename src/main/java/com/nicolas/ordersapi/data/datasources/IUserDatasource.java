package com.nicolas.ordersapi.data.datasources;

import java.util.Map;
import io.vavr.control.Either;

public interface IUserDatasource {

    public Either<Exception, Map<String, Object>> getUser(String email);
    public Either<Exception, Boolean> createUser(Map<String, Object> user);
}
