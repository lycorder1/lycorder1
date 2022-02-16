package com.yaocoder.myset.commonDto;

import com.yaocoder.myset.entities.Information;
import com.yaocoder.myset.entitiesMysql.User;
import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@Getter
@Setter
@XmlRootElement(name="xml")
@XmlAccessorType(XmlAccessType.FIELD)
public class WxGzhDto implements Serializable {

    private static final long serialVersionUID = 1L;

    public String signature;

    public String timestamp;

    public String nonce;

    public String echostr;

    public String ToUserName;

    public String FromUserName;

    public Long CreateTime;

    public String MsgType;

    public String Content;

    public String MsgId;
}
