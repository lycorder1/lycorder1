package com.yaocoder.myset.common;

import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
//@Service
public class RedisHelp{

//    @Autowired
//    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private RedisTemplate redisTemplate;

//    private RedisTemplate redisTemplate = (RedisTemplate)SpringUtil.getBean(RedisTemplate.class);

//    private UserRepository repository = (UserRepository)SpringUtil.getBean(UserRepository.class);

    //
    public void setValue(String key,String value){
        ValueOperations<String, String> operations=redisTemplate.opsForValue();
        operations.set(key, value);
    }

    public String getValueByKey(String key){
        ValueOperations<String, String> operations=redisTemplate.opsForValue();
//        operations.set("spring.session.sessions."+key, "1");
        var value =  operations.get(key);
        return value;
    }

    public void removeItme(String key){
        ValueOperations<String, String> operations=redisTemplate.opsForValue();
        var value =  redisTemplate.delete(key);
    }
}
