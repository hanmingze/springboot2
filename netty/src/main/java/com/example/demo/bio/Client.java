package com.example.demo.bio;

import lombok.Cleanup;
import lombok.SneakyThrows;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {

    final static String ADDRESS = "127.0.0.1";
    final static int PORT = 8765;

    @SneakyThrows
    public static void main(String[] args) {

        @Cleanup Socket socket = new Socket(ADDRESS, PORT);
        @Cleanup BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        @Cleanup PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

        //向服务器端发送数据
        out.println("接收到客户端的请求数据...");
        String response = in.readLine();
        System.out.println("Client: " + response);


    }
}
