package com.yaocoder.myset.controllers;

import com.alibaba.fastjson.JSONObject;
import com.yaocoder.myset.common.RedisHelp;
import com.yaocoder.myset.common.Result;
import com.yaocoder.myset.common.SessionCommon;
import com.yaocoder.myset.entitiesMysql.User;
import com.yaocoder.myset.redis.AuthUser;
import com.yaocoder.myset.secondReposition.BaseAppDAOimpl;
import com.yaocoder.myset.secondReposition.UserRepository;
import lombok.var;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@RestController
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserRepository repository;

    private BaseAppDAOimpl<User> allRepository = new BaseAppDAOimpl<User>();

    @Autowired
    private SessionCommon sessionCommon;

    @Autowired
    private RedisHelp redisHelp;

    @RequestMapping("/getUser")
    @Cacheable(value="user-key")
    public AuthUser getUser() {
        AuthUser user=new AuthUser(3, "c1", "yao", "abc123","yao@yaocoder.cn","none");
        System.out.println("若下面没出现“无缓存的时候调用”字样且能打印出数据表示测试成功");
        return user;
    }

    @RequestMapping("/uid")
    String uid(HttpSession session) {
        //TODO 共享session 以及设置完成 此处设置session对应的用户id
        UUID uid = (UUID) session.getAttribute("uid");
        if (uid == null) {
            uid = UUID.randomUUID();
        }
        session.setAttribute("uid", uid);
        return session.getId();
    }

    //TODO 用户登录页面
    @CrossOrigin
    @RequestMapping("/login")
    Object login(HttpServletResponse response, HttpSession session, String userName, String password) {
        User u = new User();
        //TODO 根据登录的用户获取用户信息
        var list = repository.getUserByNamePwd(userName,password);
        var id = list.getId();
        var sessionId = redisHelp.getValueByKey("userId_"+id);

        logger.info("login:"+JSONObject.toJSONString(list));
        UUID uid = UUID.randomUUID();
        UUID _uid = null;

        //判断是否登录，保存session
        if(sessionId!=null&&!sessionId.isEmpty()){
            _uid = UUID.fromString(sessionId);
        }

        redisHelp.setValue("userId_"+id,uid.toString());
        if(_uid!=null){
            session.removeAttribute(_uid.toString());
            redisHelp.removeItme(_uid.toString());
        }

        session.setAttribute(uid.toString(),id);
        redisHelp.setValue(uid.toString(),id.toString()); // 一个用户只允许登录一个客户端

        Cookie cookie = new Cookie( "sessionId", uid.toString());
        cookie.setPath("/");
        response.addCookie(cookie);

        Result result1 = null;
        if(list!=null&&list.getId()>0){
            result1 = new Result(0, list, "success");
        }else{
            result1 = new Result(1, null, "fail");
        }

        return result1;
    }

    //TODO 用户登录操作 成功报错session 返回code 记录token值
    //TODO 用户登录页面
    @RequestMapping("/getUser1")
    public Object getUser(HttpServletRequest request) {
        var user = sessionCommon.getUserInfoBySession(request);

        logger.info("getUser1:"+JSONObject.toJSONString(user));
        return  new Result(0, user, "login is fail");
    }

    @RequestMapping("/checkLogin")
    public Object checkLogin(HttpServletRequest request, HttpServletResponse response) {
        var isLogin = sessionCommon.checkLoginBycookie(request);
        return  new Result(0, isLogin, "");
    }
}
