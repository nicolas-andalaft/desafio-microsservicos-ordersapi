package com.nicolas.ordersapi.data.mappers;

import java.util.HashMap;
import java.util.Map;

import com.nicolas.ordersapi.data.utils.MapGetter;
import com.nicolas.ordersapi.domain.entities.UserEntity;

public class UserMapper {
  private final static String id = "id";
  private final static String username = "username";
  private final static String dollarBalance = "dollar_balance";
  private final static String createdOn = "created_on";
  private final static String updatedOn = "updated_on";

  public static UserEntity fromMap(Map<String, Object> map) {
    UserEntity user = new UserEntity(
      MapGetter.getLong(map, id),
      MapGetter.getString(map, username),
      MapGetter.getBigDecimal(map, dollarBalance),
      MapGetter.getLocalDateTime(map, createdOn),
      MapGetter.getLocalDateTime(map, updatedOn)
    );
    return user;
  }   
    
  public static Map<String, Object> toMap(UserEntity user) {
    Map<String, Object> map = new HashMap<String, Object>();
    map.put(id, user.id);
    map.put(username, user.username);
    map.put(dollarBalance, user.dollarBalance);
    map.put(createdOn, user.createdOn);
    map.put(updatedOn, user.updatedOn);
    
    return map;
  }
}
