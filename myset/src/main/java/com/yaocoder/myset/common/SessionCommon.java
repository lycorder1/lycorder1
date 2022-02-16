package com.yaocoder.myset.common;

import antlr.StringUtils;
import com.yaocoder.myset.CustomConfiguration.SpringUtil;
import com.yaocoder.myset.entitiesMysql.User;
import com.yaocoder.myset.secondReposition.UserRepository;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
//@Service
public class SessionCommon {

//    private UserRepository repository = (UserRepository)SpringUtil.getBean(UserRepository.class);

    private static  UserRepository repository;

//    private RedisHelp redisHelp = (RedisHelp) SpringUtil.getBean(RedisHelp.class);
    private RedisHelp redisHelp ;

    @Autowired
    public SessionCommon(UserRepository userService,RedisHelp redisHelp ) {
        this.repository = userService;
        this.redisHelp = redisHelp;
    }

    //验证用户是否是登录状态
    public boolean checkLoginBycookie(HttpServletRequest request){
        var  user = getUserInfoBySession(request);
        if(user!=null&&user.getId()>0){
            return true;
        }

        return false;

    }

    //TODO 根据cookie判断Session 是否存在 存在情况返回用户信
    public User getUserInfoBySession(HttpServletRequest request){
        var session = request.getSession();
        var cookies = request.getCookies();
        Integer id = 0;
        String uid = "";
        if(cookies!=null){
            for(var cookie :cookies){
                if(cookie.getName().equals("sessionId")){
                    uid =  (String)cookie.getValue();
                    var sid = session.getAttribute(uid);
                    id = sid==null?0:(Integer)sid;
                    break;
                }
            }
        }
        var sessionId = redisHelp.getValueByKey("userId_"+id.toString());
        if(sessionId!=null&&!sessionId.isEmpty()&&sessionId.equals(uid)){
            var user = repository.findById(id);
            var result = user==null?null:user.isPresent()?user.get():null;

            return result;
        }

        return null;

    }

    public User getUserInfoBySession(){
       return getUserInfoBySession(getReuest());
    }

    public  HttpServletRequest getReuest() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if(null != requestAttributes) {
            HttpServletResponse response = requestAttributes.getResponse();
            HttpServletRequest request = requestAttributes.getRequest();

            if (request!=null) {
                return request;
            }
        }
        return null;
    }


    public User getUserById(Integer id){
        return repository.getById(id);
    }
}
