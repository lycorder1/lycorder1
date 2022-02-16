package com.yaocoder.myset.chatServices;


import com.alibaba.fastjson.JSONObject;
import com.yaocoder.myset.CustomConfiguration.SpringUtil;
import com.yaocoder.myset.chat.ChatResponseDto;
import com.yaocoder.myset.chat.RequestDto;
import com.yaocoder.myset.common.RedisHelp;
import com.yaocoder.myset.common.SessionCommon;
import com.yaocoder.myset.commonDto.UserFriendDto;
import com.yaocoder.myset.entities.Information;
import com.yaocoder.myset.entitiesMysql.User;
import com.yaocoder.myset.entitiesMysql.UserFriend;
import com.yaocoder.myset.primaryReposition.InfomationRepository;
import com.yaocoder.myset.primaryReposition.OUserRepository;
import com.yaocoder.myset.secondReposition.UserFriendRepository;
import com.yaocoder.myset.secondReposition.UserRepository;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.var;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;


//@Component
@Service
public class ChatUserService implements IChatUserService{

//    private InfomationRepository repository = (InfomationRepository) SpringUtil.getBean(InfomationRepository.class);
//    private UserRepository userRepository = (UserRepository) SpringUtil.getBean(UserRepository.class);
//    private OUserRepository oUserRepository = (OUserRepository) SpringUtil.getBean(OUserRepository.class);
//    private UserFriendRepository userFriendRepository = (UserFriendRepository) SpringUtil.getBean(UserFriendRepository.class);
//    protected SessionCommon sessionCommon = (SessionCommon) SpringUtil.getBean(SessionCommon.class);
//    protected RedisHelp redisHelp = (RedisHelp) SpringUtil.getBean(RedisHelp.class);
    private InfomationRepository repository;
    private UserRepository userRepository;
    private OUserRepository oUserRepository;
    private UserFriendRepository userFriendRepository;
    protected SessionCommon sessionCommon;
    protected RedisHelp redisHelp;

    @Autowired
    public ChatUserService(InfomationRepository repository,
                           UserRepository userRepository,
                           OUserRepository oUserRepository,
                           UserFriendRepository userFriendRepository,
                           SessionCommon sessionCommon,
                           RedisHelp redisHelp) {
        this.repository = repository;
        this.userRepository = userRepository;
        this.oUserRepository = oUserRepository;
        this.userFriendRepository = userFriendRepository;
        this.sessionCommon = sessionCommon;
        this.redisHelp = redisHelp;
    }

    @Override
    public ChatResponseDto sendMsgUser(RequestDto dto, int userId, HashMap<String, Channel> arr){
        var result = new ChatResponseDto(0,null,"success");
        var informationObj = JSONObject.toJSONString(dto.getData());
        var information = JSONObject.parseObject(informationObj, Information.class);
        //TODO 判断发送的用户是否在线
        var uid = dto.userId;
        result.handleName ="chat/sendMsgUser";
        var longId =  redisHelp.getValueByKey("socket_"+uid);

        var dto1 = new Information();
        BeanUtils.copyProperties(information,dto1);
        dto1.setCreateDate(new Date());
        dto1.setUserId(userId);
        var oUser = oUserRepository.findById(dto1.getOId()).orElse(null);
        dto1.setOUser(oUser);
        result.uId = dto1.getOId();
//        result.uId = userId;

        if(!StringUtils.hasText(longId)){
            dto1.setIsRead(false);
            result.data = dto1;
        }else{
            //判断longID 是否在线
            var channl = arr.get(longId);
            if(channl!=null&&channl.isActive()){
                dto1.setIsRead(true);
                result.data = dto1;
                result.uId = userId;
                result.handleName ="chat/sendMsgUser";
                //TODO 在线情况下，直接返回消息数据，并报错到数据库
                var resultStr = JSONObject.toJSONString(result);
                channl.writeAndFlush(new TextWebSocketFrame(resultStr));
            }else{
                //TODO 离线情况下，直接保存消息
                dto1.setIsRead(false);
                result.data = dto1;
            }
        }

        result.uId = dto1.getOId();
        repository.save(dto1);
        return result;
    }

    @Override
    public ChatResponseDto getOnlineOrUnlineUser(int userId,HashMap<String, Channel> arr) {
        //获取好友LIst
        List<UserFriendDto> onLineFrList = new ArrayList<UserFriendDto>();
        List<UserFriendDto> unLineFrList = new ArrayList<UserFriendDto>();
        List<UserFriend> friends =userFriendRepository.getUserFriends(userId);
        friends.forEach(item->{
            UserFriendDto uDto = new UserFriendDto();

            var newMsg = repository.getNewMsg(userId,item.getOId());
            uDto.userFriend = item;
            var msg = newMsg.stream().findFirst().orElse(null);
            uDto.userFriend.setDes(msg==null?"":msg.getMsg());
            uDto.mation = newMsg.stream().findFirst().orElse(null);

            var longId = redisHelp.getValueByKey("socket_"+item.getOId());
            if(!StringUtils.hasText(longId)){
                //离线的情况
                unLineFrList.add(uDto);
                return;
            }

            var channl = arr.get(longId);
            if(channl!=null&&channl.isActive()){
                onLineFrList.add(uDto);
            }else{
                unLineFrList.add(uDto);
            }

        });

        Map<String,List<UserFriendDto>> map = new HashMap<String,List<UserFriendDto>>();
        map.put("online",onLineFrList);
        map.put("offline",unLineFrList);
        var result = new ChatResponseDto(0, null,"success");
        result.res = map;
        return result;
    }

    @Override
    public ChatResponseDto Login(User user,String _longId,HashMap<String, Channel> arr) {
        //可以根据Session来获取uid 判断登录，进而判断用户在线情况
        var uid = user.getId();
        var longId = _longId;
        //判断longID 是否在线
        var channl = arr.get(longId);
        if(channl!=null&&channl.isActive()){
            //存储登录信息到session
            redisHelp.setValue("socket_"+uid,longId);
        }else{
            //TODO 未登录
            redisHelp.removeItme("socket_"+uid);
            return  new ChatResponseDto(-1,null,"fail");
        }
        var newUser = new User();
        BeanUtils.copyProperties(user,newUser);
        var data = JSONObject.toJSONString(newUser);

        //TODO 给所有在线好友通知好友上线
        var friends = userFriendRepository.getUserFriends(user.getId());
        for(var frid :friends){
            var _fLongId = redisHelp.getValueByKey("socket_"+frid.getOId());
            var fridChannl = arr.get(_fLongId);
            if(fridChannl!=null&&fridChannl.isActive()){
                var activeRe = new ChatResponseDto(0,null,"success");
                activeRe.handleName ="chat/LoginNotice";
                activeRe.uId = user.getId();
                var resultStr = JSONObject.toJSONString(activeRe);
                fridChannl.writeAndFlush(new TextWebSocketFrame(resultStr));
            }
        }

        return new ChatResponseDto(0, data,"success");
    }

    @Override
    public ChatResponseDto getFriendMsg(int userId,String paramUid) {
        //获取好友List
        var lid = repository.getNewMsg(userId,Integer.valueOf(paramUid));

        var result = new ChatResponseDto(0, lid,"success");
        result.uId = Integer.valueOf(paramUid);
        return result;
    }
}
