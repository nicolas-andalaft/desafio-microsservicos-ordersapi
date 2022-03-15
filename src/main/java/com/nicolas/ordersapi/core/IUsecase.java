package com.nicolas.ordersapi.core;

import io.vavr.control.Either;

public interface IUsecase<Input, Output> {
    public abstract Either<Exception, Output> call(Input params);
}
