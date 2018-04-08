package com.example.demo.bio;

import lombok.Cleanup;
import lombok.SneakyThrows;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerHandler implements Runnable {

    private Socket socket;

    public ServerHandler(Socket socket) {
        this.socket = socket;
    }

    @SneakyThrows
    @Override
    public void run() {

        @Cleanup BufferedReader in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
        @Cleanup PrintWriter out = new PrintWriter(this.socket.getOutputStream(), true);
        String body = null;
        while (true) {
            body = in.readLine();
            if (body == null) break;
            System.out.println("Server :" + body);
            out.println("服务器端回送响的应数据.");
        }


    }

}
