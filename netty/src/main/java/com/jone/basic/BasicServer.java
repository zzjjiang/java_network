package com.jone.basic;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;

/**
 * @Description netty服务器端
 * @Author zzj
 * @date 2020.04.24
 */
public class BasicServer {

    private final int port;

    public BasicServer(int port) {
        this.port = port;
    }

    public static void main(String[] args) throws InterruptedException {
        int port = 9999;
        BasicServer server = new BasicServer(port);
        System.out.println("服务器开启");
        server.start();
        System.out.println("服务器关闭");
    }

    public void start() throws InterruptedException {
        BasicServerHandler serverHandler = new BasicServerHandler();
        //线程组
        NioEventLoopGroup group = new NioEventLoopGroup();

        try{
            //服务端启动  必须
            ServerBootstrap bs = new ServerBootstrap();
        //将线程组传入
        bs.group(group)
                //指定使用nio 进行网络传输
                .channel(NioServerSocketChannel.class)
                //指定服务器监听端口
                .localAddress(new InetSocketAddress(port))
                /*服务端每接收到一个连接请求，就会新建socket通信,也就是channel,
                * 所以下面这段代码的作用就是为这个子channel增加handle
                * */
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        //添加到该子channel的pipeline的尾部
                        socketChannel.pipeline().addLast(serverHandler);
                    }
                });
        //异步绑定到服务器,sync()会阻塞直到完成
        ChannelFuture cf = bs.bind().sync();
        //阻塞直到服务器channel关闭
        cf.channel().closeFuture().sync();
        } finally {
            //优雅关闭线程组
            group.shutdownGracefully().sync();
        }
    }

}

