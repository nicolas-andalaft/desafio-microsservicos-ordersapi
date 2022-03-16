package com.nicolas.ordersapi.core;

import java.util.Map;

public interface IModel<T> {
    public Map<String, Object> toMap(T entity);
}
