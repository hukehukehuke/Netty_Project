package com.example.netty.neetytest.channel;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

/**
 * 客户端处理类
 */
public class NettyClientHandeler extends ChannelInboundHandlerAdapter {

    //通道就绪
    @Override
    public void channelActive(ChannelHandlerContext cc){
        cc.writeAndFlush(Unpooled.copiedBuffer("老板还钱把", CharsetUtil.UTF_8));
    }
    //读取数据
    @Override
    public void channelRead(ChannelHandlerContext cc,Object msg){
        ByteBuf buf = (ByteBuf)msg;
    }
}
