package com.yaocoder.myset.chatServices;

import com.yaocoder.myset.chat.ChatResponseDto;
import com.yaocoder.myset.chat.RequestDto;
import com.yaocoder.myset.entitiesMysql.User;
import io.netty.channel.Channel;

import java.util.HashMap;

public interface IChatUserService {

    ChatResponseDto sendMsgUser(RequestDto dto, int userId, HashMap<String, Channel> arr);

    ChatResponseDto getOnlineOrUnlineUser(int userId,HashMap<String, Channel> arr);

    ChatResponseDto Login(User user, String _longId, HashMap<String, Channel> arr);

    ChatResponseDto getFriendMsg(int userId,String paramUid);
}
