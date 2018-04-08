package com.example.demo.bio;

import lombok.Cleanup;
import lombok.SneakyThrows;

import java.net.ServerSocket;
import java.net.Socket;


public class Server {

    final static int PROT = 8765;

    @SneakyThrows
    public static void main(String[] args) {

        @Cleanup ServerSocket server = server = new ServerSocket(PROT);
        System.out.println(" server start .. ");
        while (true) {
            //进行阻塞
            Socket socket = server.accept();
            //新建一个线程执行客户端的任务
            new Thread(new ServerHandler(socket)).start();
        }


    }


}
