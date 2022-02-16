package com.yaocoder.myset.commonDto;


import com.yaocoder.myset.entities.Information;
import com.yaocoder.myset.entitiesMysql.User;
import com.yaocoder.myset.entitiesMysql.UserFriend;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserFriendDto {
    public User userDto;
    public Information mation;
    public UserFriend userFriend;
}
