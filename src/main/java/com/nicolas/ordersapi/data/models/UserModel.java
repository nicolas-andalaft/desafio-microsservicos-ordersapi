package com.nicolas.ordersapi.data.models;

import java.util.Map;

import com.nicolas.ordersapi.data.utils.MapGetter;
import com.nicolas.ordersapi.domain.entities.UserEntity;

import io.vavr.collection.HashMap;

public class UserModel extends UserEntity {

    public static UserModel fromMap(Map<String, Object> map) {
        UserModel user = new UserModel();
        user.id = MapGetter.getLong(map, "id");
        user.username = MapGetter.getString(map, "username");
        user.dollar_balance = MapGetter.getBigDecimal(map, "dollarBalance");
        user.created_on = MapGetter.getLocalDateTime(map, "createdOn");
        user.updated_on = MapGetter.getLocalDateTime(map, "updatedOn");
        
        return user;
    }   
        
    public static Map<String, Object> toMap(UserModel user) {
        HashMap<String, Object> map = HashMap.of(
            "id", user.id,
            "username", user.username,
            "dollar_balance", user.dollar_balance,
            "created_on", user.created_on,
            "updated_on", user.updated_on
        );

        return map.toJavaMap();
    }
}