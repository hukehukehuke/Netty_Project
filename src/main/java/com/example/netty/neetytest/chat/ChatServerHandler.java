package com.example.netty.neetytest.chat;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * 自定义服务器端业务处理类
 */
public class ChatServerHandler extends SimpleChannelInboundHandler<String> {

    public static List<Channel> channels = new ArrayList<Channel>();

    @Override
    public void channelActive(ChannelHandlerContext cc){
        Channel channel = cc.channel();
        channels.add(channel);
        System.out.println("谁谁上线"+channel.remoteAddress().toString().substring(0));
    }
    @Override
    public void channelInactive(ChannelHandlerContext cc){
        Channel channel = cc.channel();
        channels.remove(channel);
        System.out.println("谁谁离线"+channel.remoteAddress().toString().substring(0));
    }
    @Override
    protected void channelRead0(ChannelHandlerContext cc, String s) throws Exception {
        Channel channel = cc.channel();
        for(Channel c : channels){
            c.writeAndFlush(channel.remoteAddress().toString().substring(1)+"谁谁说");
        }
    }
}
