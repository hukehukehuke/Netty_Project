package com.example.netty.neetytest.channel;


import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

/**
 * 服务器端代码
 */
public class NettyServerHandeler extends ChannelInboundHandlerAdapter {
     //读取数据事件
    @Override
    public void channelRead(ChannelHandlerContext cc, Object msg){
        ByteBuf buf =(ByteBuf)msg;
        System.out.println("客户端发来的消息"+buf.toString(CharsetUtil.UTF_8));
    }

    //数据读取完成事件
    @Override
    public void channelReadComplete(ChannelHandlerContext cc){
       cc.writeAndFlush(Unpooled.copiedBuffer("我没钱",CharsetUtil.UTF_8));
    }
    //异常
    @Override
    public void exceptionCaught(ChannelHandlerContext cc, Throwable throwable){
        cc.close();
    }
}
