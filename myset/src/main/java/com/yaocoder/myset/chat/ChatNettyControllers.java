package com.yaocoder.myset.chat;

import com.yaocoder.myset.CustomConfiguration.SpringUtil;
import com.yaocoder.myset.chatServices.ChatUserService;
import com.yaocoder.myset.chatServices.IChatUserService;
import com.yaocoder.myset.primaryReposition.InfomationRepository;
import com.yaocoder.myset.primaryReposition.OUserRepository;
import com.yaocoder.myset.secondReposition.UserFriendRepository;
import com.yaocoder.myset.secondReposition.UserRepository;
import lombok.var;

@ChatControllerAnno
public class ChatNettyControllers extends  ChatBaseControllers {

//    private SessionCommon sessionCommon = (SessionCommon) SpringUtil.getBean(SessionCommon.class);
//    private RedisHelp redisHelp = (RedisHelp) SpringUtil.getBean(RedisHelp.class);
//    private RedisHelp redisHelp ;
    private IChatUserService chatUserService = (ChatUserService) SpringUtil.getBean(ChatUserService.class);

    //发送消息
    @ChatAction(method="chat/sendMsgUser")
    public ChatResponseDto sendMsgUser(RequestDto dto){
       return chatUserService.sendMsgUser(dto,user.getId(),arr);
    }

    //聊天室用户登录
    @ChatAction(method="chat/Login")
    public ChatResponseDto Login(RequestDto dto){
        return chatUserService.Login(user,longId,arr);
    }

    //获取在线和离线用户list
    @ChatAction(method="chat/getOnlineOrUnlineUser")
    public ChatResponseDto getOnlineOrUnlineUser(RequestDto dto){
        return chatUserService.getOnlineOrUnlineUser(user.getId(),arr);
    }

    //获取朋友的聊天信息
    @ChatAction(method="chat/getFriendMsg")
    public ChatResponseDto getFriendMsg(RequestDto dto){
        return chatUserService.getFriendMsg(user.getId(),dto.getUserId());
    }

    //已读消息
    @ChatAction(method="chat/readed")
    public ChatResponseDto readed(RequestDto dto){
        //根据好友 key 获取是否在线list
        var lid = redisHelp.getValueByKey("socket_"+dto.userId);
        var chanl = arr.get(lid);
        return new ChatResponseDto(0, null,"success");
    }

}
