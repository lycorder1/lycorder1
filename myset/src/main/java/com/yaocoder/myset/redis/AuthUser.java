package com.yaocoder.myset.redis;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

//@Entity  //代表此类为一个表的映射entity类
//@Table(name="authUser")  //设置对应的表名
@Getter
@Setter
public class AuthUser implements Serializable {

    private static final long serialVersionUID = 1L;

    public AuthUser(){}

    public AuthUser(Integer id,String code,String name,String password,
                    String email,String favorite){
        this.id = id;
        this.code = code;
        this.name = name;
        this.password = password;
        this.email = email;
        this.favorite = favorite;
    }
//    @Id
//    @Column(name="Id")
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

//    @Column(name="code")
    private String code;

//    @Column(name="name")
    private String name;

//    @Column(name="email")
    private String email;

//    @Column(name="password")
    private String password;

//    @Column(name="favorite")
    private String favorite;

}
