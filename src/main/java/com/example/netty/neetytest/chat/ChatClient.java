package com.example.netty.neetytest.chat;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.util.Scanner;


public class ChatClient {
    private final String address;
    private final  int port;

    public ChatClient(String address, int port) {
        this.address = address;
        this.port = port;
    }
    public void run() throws Exception{
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        try{
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(eventLoopGroup)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            ChannelPipeline channelPipeline = socketChannel.pipeline();
                            //编码器
                            channelPipeline.addLast("decoder",new StringDecoder());
                            //解码器
                            channelPipeline.addLast("encoder",new StringEncoder());
                            channelPipeline.addLast(new ChatClientHandler());
                        }
                    });
            ChannelFuture channelFuture = bootstrap.connect(address, port).sync();
            Channel channel = channelFuture.channel();
            Scanner scanner = new Scanner(System.in);
            while (scanner.hasNextLine()){
                channel.writeAndFlush(scanner.next());
            }

        }finally {
            eventLoopGroup.shutdownGracefully();
        }
    }
}
