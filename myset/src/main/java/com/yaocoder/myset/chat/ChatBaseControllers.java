package com.yaocoder.myset.chat;


import com.yaocoder.myset.CustomConfiguration.SpringUtil;
import com.yaocoder.myset.common.RedisHelp;
import com.yaocoder.myset.common.SessionCommon;
import com.yaocoder.myset.entitiesMysql.User;
import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import lombok.Getter;
import lombok.Setter;
import lombok.var;
import org.springframework.util.StringUtils;

import java.util.HashMap;

@Getter
@Setter
public class ChatBaseControllers {

   public String longId;

   public NettyRequestDto requestDto;

   public ChannelGroup channelClient;

   public HashMap<String, Channel> arr;

   public User user;

   protected SessionCommon sessionCommon = (SessionCommon) SpringUtil.getBean(SessionCommon.class);

   protected RedisHelp redisHelp = (RedisHelp) SpringUtil.getBean(RedisHelp.class);

   protected User getLoginUser(String sId){
      //TODO 根据sId 获取uid,根据uid 获取longID
      var uid = redisHelp.getValueByKey(sId);
      if(StringUtils.hasText(uid)){
//            var user = userRepository.getById(Integer.valueOf(uid));
         User user = sessionCommon.getUserById(Integer.valueOf(uid));
         return user;
      }
      return null;
   }

}
