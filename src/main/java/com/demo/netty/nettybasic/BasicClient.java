package com.demo.netty.nettybasic;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;

/**
 * @Description netty 基础客户端
 * @Author zzj
 * @date 2020.04.24
 */
public class BasicClient {
    private final int port;
    private final String host;

    public BasicClient(int port, String host) {
        this.port = port;
        this.host = host;
    }

    public void start() throws InterruptedException {
        //线程组
        EventLoopGroup group = new NioEventLoopGroup();
        try{
            //客户端启动必备
            Bootstrap bs = new Bootstrap();
            //将线程组传入
            bs.group(group)
                    //指定NIO进行网络传输
                    .channel(NioSocketChannel.class)
                    .remoteAddress(new InetSocketAddress(host,port))
                    .handler(new BasicClientHandle());
            //连接到远程节点,阻塞直到连接完成
            ChannelFuture f = bs.connect().sync();
            //阻塞程序,直到channel发生了关闭
            f.channel().closeFuture().sync();
        }finally {
            group.shutdownGracefully().sync();
        }

    }

    public static void main(String[] args) {
        BasicClient client = new BasicClient(9999, "127.0.0.1");
        try {
            client.start();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
