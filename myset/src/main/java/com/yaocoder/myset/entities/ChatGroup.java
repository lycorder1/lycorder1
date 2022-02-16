package com.yaocoder.myset.entities;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


@Entity  //代表此类为一个表的映射entity类
//@Table(name="fileRecord",schema = "dbo")  //设置对应的表名
@Table(name="ChatGroup")  //设置对应的表名
@Getter
@Setter
public class ChatGroup implements Serializable{
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
    @Column(name="des")
    private String des;

    @Column(name="title")
    private String title;

    @Column(name="createDate")
    private Date createDate;

    @Column(name="moddifyDate")
    private Date moddifyDate;

    @Column(name="isRec")
    private boolean isRec;

    @Column(name="userId")
    private int userId;

    @Column(name="masterId")
    private int masterId;

    @Column(name="manager1Id")
    private int manager1Id;

    @Column(name="manager2Id")
    private int manager2Id;

    @Column(name="manager3Id")
    private int manager3Id;

    //0对单个用户 1 对群组
    @Column(name="type")
    private int type;

    @Column(name="count")
    private int count;
}
