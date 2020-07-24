package com.example.netty.neetytest.channel;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class NettyServer {

    public static void main(String[] args) throws InterruptedException {
        //创建一个线程组，接收客户端连接
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        //创建一个线程组，处理网络操作
        EventLoopGroup workGroup = new NioEventLoopGroup();
        //创建服务端启动助手来配置参数
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(bossGroup,workGroup)  //设置两个线程组
        .channel(NioServerSocketChannel.class)   //使用NioServerSocketChannel作为连接通道
        .option(ChannelOption.SO_BACKLOG,128)  //设置线程队列中等待的个数
        .childOption(ChannelOption.SO_KEEPALIVE,true)
        .childHandler(new ChannelInitializer<SocketChannel>() { //往pipeline管道中添加对象
            @Override
            protected void initChannel(SocketChannel socketChannel) throws Exception {
                socketChannel.pipeline().addLast(new NettyServerHandeler());
            }
        });
        System.out.println("Server 已经就绪");
        ChannelFuture channelFuture = serverBootstrap.bind(9999).sync();//异步启动
        channelFuture.channel().closeFuture().sync();
        bossGroup.shutdownGracefully().sync();
        workGroup.shutdownGracefully().sync();
    }
}
