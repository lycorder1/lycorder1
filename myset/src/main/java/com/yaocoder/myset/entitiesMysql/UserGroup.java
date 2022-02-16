package com.yaocoder.myset.entitiesMysql;

//import org.hibernate.annotations.Proxy;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


@Entity  //代表此类为一个表的映射entity类
@Table(name="UserGroup")  //设置对应的表名
@Getter
@Setter
public class UserGroup implements Serializable{
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

    /** 名称*/
    //nullable - 是否可以为null,默认为true   unique - 是否唯一,默认为false
    @Column(name="name")
    private String name;

    /** 创建者userID */
    @Column(name="CreatUId")
    private Integer CreatUId;

    /** 群主ID */
    @Column(name="UserId")
    private Integer UserId;

    /** 群成员数量 */
    @Column(name="count")
    private Integer count;

    /** 备注 */
    @Column(name="remark")
    private String remark;

    /** 描述 */
    @Column(name="des")
    private String des;


    /** 是否删除 */
    @Column(name="isRec")
    private boolean isRec;

    /** 创建日期 */
    @Column(name="createDate")
    private Date createDate;

}
