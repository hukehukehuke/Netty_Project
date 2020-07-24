package com.example.netty.neetytest.chat;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

public class ChatServer {
    private int port; //端口号
    public  ChatServer(int port){
        this.port = port;
    }
    public void run() throws Exception{
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workGroup = new NioEventLoopGroup();
        try{
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup,workGroup)
            .option(ChannelOption.SO_BACKLOG,128)
            .childOption(ChannelOption.SO_KEEPALIVE,true)
            .childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel socketChannel) throws Exception {
                    ChannelPipeline channelPipeline = socketChannel.pipeline();
                    //编码器
                    channelPipeline.addLast("decoder",new StringDecoder());
                    //解码器
                    channelPipeline.addLast("encoder",new StringEncoder());
                    channelPipeline.addLast(new ChatServerHandler());
                }
            });
            ChannelFuture channelFuture = bootstrap.bind(port).sync();
            channelFuture.channel().closeFuture().sync();
        }finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }
}
