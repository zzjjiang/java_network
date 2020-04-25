package com.jone.basic;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;

/**
 * @description bio通信服务端  该方式只能监听一次
 * @author zzj
 * @date 2020.04.14
 */
public class BioServer {
    public static void main(String[] args) throws Exception {
        //服务器必备
        ServerSocket serverSocket = new ServerSocket();
        //绑定监听端口
        SocketAddress socketAddress = new InetSocketAddress(10001);
        serverSocket.bind(socketAddress);
        System.out.println("Server start.......");
        Socket socket = serverSocket.accept();

        ObjectInputStream inputStream
                = new ObjectInputStream(socket.getInputStream());
        ObjectOutputStream outputStream
                = new ObjectOutputStream(socket.getOutputStream());
        /*服务器的输入*/
        String userName = inputStream.readUTF();
        System.out.println("服务器接收到的客户端的信息为:"+userName);

        outputStream.writeUTF("Hello,"+userName);
        outputStream.flush();
        outputStream.close();
        inputStream.close();
        socket.close();
    }
}
