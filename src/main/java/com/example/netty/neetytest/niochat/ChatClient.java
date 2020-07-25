package com.example.netty.neetytest.niochat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class ChatClient {

    private final String host = "127.0.0.1";
    private int port = 9999;
    private SocketChannel socketChannel;
    private String name;

    public void run(){
        try {
            socketChannel = SocketChannel.open();
            socketChannel.configureBlocking(false);
            InetSocketAddress inetSocketAddress = new InetSocketAddress(host,port);
            if(!socketChannel.connect(inetSocketAddress)){
                while(!socketChannel.finishConnect()){
                    System.out.println("client连接的时候我还可以干其他的事儿");
                }
            }
            name = socketChannel.getLocalAddress().toString().substring(1);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    //向服务器发送消息
    public void send(String msg) throws IOException {
        if(msg.equalsIgnoreCase("bye")){
            socketChannel.close();
            return;
        }
        ByteBuffer buffer = ByteBuffer.wrap(msg.getBytes());
        socketChannel.write(buffer);
    }
    //向服务器读取数据
    public void read() throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        int read = socketChannel.read(buffer);
        if(read > 0){
            String str = new String(buffer.array());
            System.out.println(str);
        }
    }

}
