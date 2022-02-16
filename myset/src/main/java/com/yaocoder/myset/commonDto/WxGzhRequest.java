package com.yaocoder.myset.commonDto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class WxGzhRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    public String grant_type;

    public String appid;

    public String secret;

}
