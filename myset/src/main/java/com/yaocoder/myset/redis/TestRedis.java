package com.yaocoder.myset.redis;

//import org.junit.Assert;
//import org.junit.Test;
//import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;
import java.util.concurrent.TimeUnit;

//@RunWith(SpringRunner.class)
@SpringBootTest
public class TestRedis {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private RedisTemplate redisTemplate;

//    @Test
    public void test() throws Exception {
//        stringRedisTemplate.opsForValue().set("aaa", "111");
//        Assert.assertEquals("111", stringRedisTemplate.opsForValue().get("aaa"));

//        key-value存储方式
//        Set<String> set1=new HashSet<String>();
//        set1.add("set1");
//        set1.add("set2");
//        set1.add("set3");
//        redisTemplate.opsForSet().add("set1",set1);
//        Set<String> resultSet =redisTemplate.opsForSet().members("set1");
//        System.out.println("resultSet:"+resultSet);

//        Map<String,String> map=new HashMap<String,String>();
//        map.put("key1","value1");
//        map.put("key2","value2");
//        map.put("key3","value3");
//        map.put("key4","value4");
//        map.put("key5","value5");
//        redisTemplate.opsForHash().putAll("map1",map);
//        Map<String,String> resultMap= redisTemplate.opsForHash().entries("map1");
//        List<String> reslutMapList=redisTemplate.opsForHash().values("map1");
//        Set<String>resultMapSet=redisTemplate.opsForHash().keys("map1");
//        String value=(String)redisTemplate.opsForHash().get("map1","key1");
//        System.out.println("value:"+value);
//        System.out.println("resultMapSet:"+resultMapSet);
//        System.out.println("resultMap:"+resultMap);
//        System.out.println("resulreslutMapListtMap:"+reslutMapList);

//        List<String> list1=new ArrayList<String>();
//        list1.add("a1");
//        list1.add("a2");
//        list1.add("a3");
//
//        List<String> list2=new ArrayList<String>();
//        list2.add("b1");
//        list2.add("b2");
//        list2.add("b3");
//        redisTemplate.opsForList().leftPush("listkey1",list1);
//        redisTemplate.opsForList().rightPush("listkey2",list2);
//        List<String> resultList1=(List<String>)redisTemplate.opsForList().leftPop("listkey1");
//        List<String> resultList2=(List<String>)redisTemplate.opsForList().rightPop("listkey2");
//        System.out.println("resultList1:"+resultList1);
//        System.out.println("resultList2:"+resultList2);

        //实体类测试
        List<AuthUser> blackList= new ArrayList<AuthUser>();
        AuthUser _a1 = new AuthUser();
        _a1.setId(2);
        _a1.setName("yao1");
        blackList.add(_a1);
        ValueOperations<String, List<AuthUser>> operations=redisTemplate.opsForValue();
        operations.set("blacklist1",blackList);
        List<AuthUser> resultBlackList = operations.get("blacklist1");
        for(AuthUser blacklist:resultBlackList){
            System.out.println("ip:"+blacklist.getName());
        }
    }

//    @Test
    public void testObj() throws Exception {
        AuthUser user=new AuthUser(3, "c1", "yao", "abc123","yao@yaocoder.cn","none");
//        AuthUser user=new AuthUser();
        ValueOperations<String, AuthUser> operations=redisTemplate.opsForValue();
        operations.set("cod1", user);
        operations.set("yao.f", user,1, TimeUnit.SECONDS);
//        ValueOperations<String, AuthUser> operations=redisTemplate.opsForValue();

//        Thread.sleep(1000);
        //redisTemplate.delete("com.neo.f");

        AuthUser resultBlackList = operations.get("cod1");
        System.out.println("exists is true"+resultBlackList.getName()+resultBlackList.getEmail());
        AuthUser exists=operations.get("yao.f");
        boolean exists1=redisTemplate.hasKey("yao.f");
        System.out.println("name:"+exists.getName()+" email:"+exists.getEmail());
        if(exists1){
            System.out.println("exists is true");
        }else{
            System.out.println("exists is false");
        }
        // Assert.assertEquals("aa", operations.get("com.neo.f").getUserName());
    }
}