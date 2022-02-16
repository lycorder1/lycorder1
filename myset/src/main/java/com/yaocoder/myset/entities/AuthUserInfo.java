package com.yaocoder.myset.entities;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


@Entity  //代表此类为一个表的映射entity类
//@Table(name="fileRecord",schema = "dbo")  //设置对应的表名
@Table(name="authUserInfo")  //设置对应的表名
@Getter
@Setter
public class AuthUserInfo implements Serializable{
    /**
     * 功能描述:序列化时候的唯一性，相应的get和set方法已经省略。
     */
    private static final long serialVersionUID = 1L;

    /** 主键-id uuid */
    @Id  //此备注代表该字段为该类的主键
//    @GeneratedValue(generator="system-uuid")
//    @GenericGenerator(name="system-uuid",strategy = "uuid")
    //name - 指定对应列的名称 ,length - 最大长度
    @Column(name="Id") //
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /** 数字,具有唯一性 */
    //nullable - 是否可以为null,默认为true   unique - 是否唯一,默认为false
    @Column(name="Code")
    private String code;

    /** 部门电话 */
    @Column(name="Name")
    private String name;

    @Column(name="Password")
    private String password;

    @Column(name="Age")
    private short age;

    @Column(name="Sex")
    private short Sex;

    @Column(name="Favorit")
    private short favorit;

    @Column(name="Portrait")
    private String portrait;

    @Column(name="AuthAppID")
    private Integer authAppID;

    @Column(name="City")
    private String city;

    @Column(name="Salt")
    private String salt;

    @Column(name="OpenID")
    private Integer openID;

    @Column(name="createDate")
    private Date createDate;

    @Column(name="moddifyDate")
    private Date moddifyDate;

    @Column(name="isRec")
    private boolean isRec;

}
