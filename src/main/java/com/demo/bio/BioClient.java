package com.demo.bio;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

/**
 * @description bio通信客户端
 * @author zzj
 * @date 2020.04.14
 */
public class BioClient {
    public static void main(String[] args) throws IOException {
        //客户端启动必备
        Socket socket = null;
        //实例化与服务端通信的输入输出流
        ObjectOutputStream output = null;
        ObjectInputStream input = null;
        try{
            socket = new Socket();
            SocketAddress socketAddress = new InetSocketAddress("127.0.0.1",10002);
            socket.connect(socketAddress);
            output = new ObjectOutputStream(socket.getOutputStream());
            input = new ObjectInputStream(socket.getInputStream());
            /*向服务器输出请求*/
            output.writeUTF("test");
            output.flush();

            //接收服务器的输出
            System.out.println("客户端收到的服务器信息为:" + input.readUTF());
        }finally {
            if (socket!=null) {
                socket.close();
            }
            if (output!=null) {
                output.close();
            }
            if (input!=null) {
                input.close();
            }
        }

    }
}
