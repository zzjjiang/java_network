package com.jone.basic;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;

/**
 * @description 服务端多线程监听
 * @author zzj
 * @date 2020.04.14
 */
public class BioServerUpgrade {

    public static void main(String[] args) throws IOException {
        //服务器必备
        ServerSocket serverSocket = new ServerSocket();
        //设置端口
        SocketAddress socketAddress = new InetSocketAddress(10002);
        //绑定端口
        serverSocket.bind(socketAddress);
        //接受消息,通过socket通信
        System.out.println("Server start.......");
        while (true){
            new Thread(new ServerTask(serverSocket.accept())).start();
        }
    }

    private static class ServerTask implements Runnable{

        private Socket socket;
        public ServerTask(Socket socket){
            this.socket = socket;
        }
        @Override
        public void run() {
            /*拿和客户端通讯的输入输出流*/
            try(ObjectInputStream inputStream
                        = new ObjectInputStream(socket.getInputStream());
                ObjectOutputStream outputStream
                        = new ObjectOutputStream(socket.getOutputStream())){

                /*服务器的输入*/
                String userName = inputStream.readUTF();
                System.out.println("Accept clinet message:"+userName);

                outputStream.writeUTF("Hello,"+userName);
                outputStream.flush();


            }catch (Exception e){
                e.printStackTrace();
            }
            finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
