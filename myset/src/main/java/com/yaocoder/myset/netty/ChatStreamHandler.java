package com.yaocoder.myset.netty;

import com.alibaba.fastjson.JSONObject;
import com.yaocoder.myset.chat.ChatCustomerHandler;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;
import lombok.var;

import java.util.HashMap;

//TextWebSocketFrame：处理消息的handler，在Netty中用于处理文本的对象，frames是消息的载体
public class ChatStreamHandler extends SimpleChannelInboundHandler<BinaryWebSocketFrame>{

    //用于记录和管理所有客户端的channel，可以把相应的channel保存到一整个组中
    //DefaultChannelGroup：用于对应ChannelGroup，进行初始化
    private static ChannelGroup channelClient =  new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    private static HashMap<String, Channel> arr = new HashMap<String,Channel>();

    //当客户端连接服务端（或者是打开连接之后）
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        //获取客户端所对应的channel，添加到一个管理的容器中即可
        channelClient.add(ctx.channel());
        System.out.println("处理器hashCode："+this.hashCode());
        System.out.println("处理器："+this.toString());

        //TODO 使用一个对应当前类的全局容器
        arr.put(ctx.channel().id().asLongText(),ctx.channel());
    }

    //客户端断开
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        //实际上是多余的，只要handler被移除，client会自动的把对应的channel移除掉
        channelClient.remove(ctx.channel());
        //每一而channel都会有一个长ID与短ID
        //一开始channel就有了，系统会自动分配一串很长的字符串作为唯一的ID，如果使用asLongText()获取的ID是唯一的，asShortText()会把当前ID进行精简，精简过后可能会有重复
        System.out.println("channel的长ID："+ctx.channel().id().asLongText());
        System.out.println("channel的短ID："+ctx.channel().id().asShortText());

        arr.remove(ctx.channel().id().asLongText());
    }


    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, BinaryWebSocketFrame binaryWebSocketFrame) throws Exception {
        //text()获取从客户端发送过来的字符串
        var content = binaryWebSocketFrame.content();

        //TODO 处理传输的数据

    }
}
