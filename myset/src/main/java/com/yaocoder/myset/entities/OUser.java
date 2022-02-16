package com.yaocoder.myset.entities;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


@Entity  //代表此类为一个表的映射entity类
//@Table(name="fileRecord",schema = "dbo")  //设置对应的表名
@Table(name="OUser")  //设置对应的表名
@Getter
@Setter
public class OUser implements Serializable{
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
    private Integer id;

    @Column(name="CreateDate")
    private Date createDate;

    @Column(name="isRec")
    private boolean isRec;

    @Column(name="userId")
    private int userId;

    @Column(name="Name")
    private String Name;
}
