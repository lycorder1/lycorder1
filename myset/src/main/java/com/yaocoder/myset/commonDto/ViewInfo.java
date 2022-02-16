package com.yaocoder.myset.commonDto;

import com.yaocoder.myset.entities.Information;
import com.yaocoder.myset.entitiesMysql.User;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class ViewInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    private User user1;

    private User user2;

    private Information information;

    public ViewInfo(){
    }

    public ViewInfo(Information information,User user1, User user2){
        this.user1 = user1;
        this.user2 = user2;
        this.information = information;
    }

}
