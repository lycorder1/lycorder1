package com.yaocoder.myset.commonDto;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import lombok.var;

@Getter
@Setter
@XStreamAlias("xml")
@XmlRootElement(name="xml")
@XmlAccessorType(XmlAccessType.FIELD)
public class WxGzhResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    public  WxGzhResponse(){}

    public  WxGzhResponse(String _ToUserName,
                          String _FromUserName,
                          Long _CreateTime,
                          String _MsgType,
                          String _Content){
        this.ToUserName = _ToUserName;
        this.FromUserName = _FromUserName;
        this.CreateTime = _CreateTime;
        this.MsgType = _MsgType;
        this.Content = _Content;
    }

    public  WxGzhResponse(String _ToUserName,
                          String _FromUserName,
                          Long _CreateTime,
                          String _MsgType,
                          int _ArticleCount,
                          String _title,
                          String _Description,
                          String _PicUrl,
                          String _Url){
        this.ToUserName = _ToUserName;
        this.FromUserName = _FromUserName;
        this.CreateTime = _CreateTime;
        this.MsgType = _MsgType;
        this.ArticleCount = _ArticleCount;
        var item = new Ittem();
        item.setTitle(_title);
        item.setDescription(_Description);
        item.setPicUrl(_PicUrl);
        item.setUrl(_Url);
        this.Articles.add(item);
    }

    @XStreamAlias("ToUserName")
    public String ToUserName;

    @XStreamAlias("FromUserName")
    public String FromUserName;

    @XStreamAlias("CreateTime")
    public Long CreateTime;

    @XStreamAlias("MsgType")
    public String MsgType;

    @XStreamAlias("Content")
    public String Content;

    @XStreamAlias("ArticleCount")
    public int ArticleCount;

    @XStreamAlias("Articles")
    public List<Ittem> Articles = new ArrayList<Ittem>();

    public String parseXml(){
      XStream xStream = new XStream();
      // xstream使用注解转换
      xStream.processAnnotations(this.getClass());
      return xStream.toXML(this);
    }

    @Getter
    @Setter
    @XStreamAlias("item")
    public class Ittem{
        @XStreamAlias("Title")
        public String Title;
        @XStreamAlias("Description")
        public String Description;
        @XStreamAlias("PicUrl")
        public String PicUrl;
        @XStreamAlias("Url")
        public String Url;
    }

}
