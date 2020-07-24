package com.example.netty.neetytest.channel;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class NettyClient {

    public static void main(String[] args) throws InterruptedException {
        //创建一个线程组
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        //创建客户端启动助手 配置参数
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(eventLoopGroup)  //设置线程组
        .channel(NioSocketChannel.class)   //设置客户端实现类
        .handler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel socketChannel) throws Exception {
                socketChannel.pipeline().addLast(new NettyServerHandeler());
            }
        });
        ChannelFuture  channelFuture = bootstrap.connect("127.0.0.,", 9999).sync();
        channelFuture.channel().closeFuture().sync();

    }
}
