package com.yaocoder.myset.netty;


import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;

/**
 * 用于和客户端进行连接
 *
 * @author phubing
 *
 */
@Component
public class WebSocketServer {
    private static final Logger logger  = Logger.getLogger(WebSocketServer.class);

//    public static void main(String[] args) throws InterruptedException {
    public void start(InetSocketAddress address) throws InterruptedException{
        //定义线程组
        EventLoopGroup mainGroup =  new NioEventLoopGroup();
        EventLoopGroup subGroup =  new NioEventLoopGroup();
        try {
            ServerBootstrap server = new ServerBootstrap();
            server.group(mainGroup, subGroup)
                    //channel类型
                    .channel(NioServerSocketChannel.class)
                    .localAddress(address)
                    //针对subGroup做的子处理器，childHandler针对WebSokect的初始化器
                    .childHandler(new WebSocketinitializer())
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);

            //绑定端口并以同步方式进行使用
            ChannelFuture channelFuture = server.bind(address).sync();

            //针对channelFuture，进行相应的监听
            logger.info("Server start listen at " + address.getPort());
            channelFuture.channel().closeFuture().sync();
        }finally {
            //针对两个group进行优雅地关闭
            mainGroup.shutdownGracefully();
            subGroup.shutdownGracefully();
        }

    }

}