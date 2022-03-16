package com.nicolas.ordersapi.data.models;

import java.util.HashMap;
import java.util.Map;

import com.nicolas.ordersapi.core.IModel;
import com.nicolas.ordersapi.data.utils.MapGetter;
import com.nicolas.ordersapi.domain.entities.UserEntity;

public class UserModel extends UserEntity implements IModel<UserEntity> {

    public UserModel() {}

    public UserModel(UserEntity user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.dollarBalance = user.getDollarBalance();
        this.createdOn = user.getCreatedOn();
        this.updatedOn = user.getUpdatedOn();
    }

    public static UserModel fromMap(Map<String, Object> map) {
        UserModel user = new UserModel();
        if (map == null) return user;

        user.id = MapGetter.getLong(map, "id");
        user.username = MapGetter.getString(map, "username");
        user.dollarBalance = MapGetter.getBigDecimal(map, "dollar_balance");
        user.createdOn = MapGetter.getTimestamp(map, "created_on");
        user.updatedOn = MapGetter.getTimestamp(map, "updated_on");
        
        return user;
    }   

    @Override
    public Map<String, Object> toMap(UserEntity user) {
        Map<String, Object> map = new HashMap<>();

        map.put("id", user.getId()); 
        map.put("username", user.getUsername()); 
        map.put("dollar_balance", user.getDollarBalance()); 
        map.put("created_on", user.getCreatedOn()); 
        map.put("updated_on", user.getUpdatedOn()); 

        return map;
    }
}