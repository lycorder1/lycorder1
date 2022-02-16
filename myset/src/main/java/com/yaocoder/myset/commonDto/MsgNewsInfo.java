package com.yaocoder.myset.commonDto;

import com.yaocoder.myset.entities.Information;
import com.yaocoder.myset.entitiesMysql.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
public class MsgNewsInfo  {

    public Information information;

    private String userName;

}
