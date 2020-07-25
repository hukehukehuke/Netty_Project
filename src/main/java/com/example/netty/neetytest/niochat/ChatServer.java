package com.example.netty.neetytest.niochat;

import io.netty.buffer.ByteBuf;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.text.SimpleDateFormat;
import java.util.Iterator;

public class ChatServer {

    private ServerSocketChannel listenerChannel;  //老大 监听通道
    private Selector selector;  //间谍
    private static final int port = 9999;
    public void run(){
        try{
            listenerChannel = ServerSocketChannel.open();
            selector = Selector.open();
            listenerChannel.bind(new InetSocketAddress(port));
            listenerChannel.configureBlocking(false);
            listenerChannel.register(selector, SelectionKey.OP_ACCEPT);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

        }
    }

    public void start(){
        try {
            while(true){
                if(selector.select(2000) == 0){
                    System.out.println("server没有客户找我我就干其他的事儿");
                    continue;
                }
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while(iterator.hasNext()){
                    SelectionKey key = iterator.next();
                    if(key.isReadable()){  //连接请求的事件
                        SocketChannel accept = listenerChannel.accept();
                        accept.configureBlocking(false);
                        accept.register(selector,SelectionKey.OP_READ);
                        System.out.println(accept.getRemoteAddress().toString()+"上线了");
                    }
                    if(key.isAcceptable()){  //读取事件
                        readMsg(key);
                    }
                    //一定要把key删除
                    iterator.remove();
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }finally {

        }
    }

    private void readMsg(SelectionKey key) throws IOException {
        SocketChannel channel = (SocketChannel) key.channel();
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        int read = channel.read(buffer);
        if(read > 0){
            String msg = new String(buffer.array());
            printInfo(msg);
            //发广播
            broadCast(channel,msg);
        }
    }

    private void broadCast(SocketChannel channel, String msg) throws IOException {
        System.out.println("服务器法广播了");
        for(SelectionKey key : selector.selectedKeys()){
            SocketChannel targetChannel = (SocketChannel) key.channel();
            if(targetChannel instanceof SocketChannel && targetChannel != channel){
                ByteBuffer buffer = ByteBuffer.wrap(msg.getBytes());
                targetChannel.write(buffer);
            }
        }
    }

    public void printInfo(String str){
        SimpleDateFormat sdf = new SimpleDateFormat("xxxx");

    }
}
