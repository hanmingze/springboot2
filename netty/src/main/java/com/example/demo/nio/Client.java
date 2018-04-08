package com.example.demo.nio;

import lombok.SneakyThrows;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * Created by jiaozhiguang on 2018/4/5.
 */
public class Client implements Runnable {

    //1 缓冲区
    private ByteBuffer readBuf = ByteBuffer.allocate(1024);
    //2 多路复用器
    private Selector selector;

    @SneakyThrows
    public Client(int port) {
        this.selector = Selector.open();
        ServerSocketChannel ssc = ServerSocketChannel.open();
        //3设置非阻塞
        ssc.configureBlocking(false);
        //4绑定端口
        ssc.bind(new InetSocketAddress(port));
        //5把服务器通道注册到多路复用器上 并且监听阻塞事件
        ssc.register(selector, SelectionKey.OP_ACCEPT);
    }

    @SneakyThrows
    public static void main(String[] args) {

        new Thread(new Client(8764)).start();
        write2server();

    }

    @SneakyThrows
    public static void write2server() {
        InetSocketAddress address = new InetSocketAddress("127.0.0.1", 8765);

        ByteBuffer writeBuffer = ByteBuffer.allocate(1024);

        SocketChannel sc = SocketChannel.open();
        sc.connect(address);

        while (true) {
            byte[] data = new byte[1024];
            System.in.read(data);
            writeBuffer.put(data);


            writeBuffer.flip();
            sc.write(writeBuffer);
            writeBuffer.clear();
        }
    }

    @SneakyThrows
    @Override
    public void run() {

        System.out.println("Client stated at 8764");

        while (true) {
            //1.start monitor
            this.selector.select();
            Iterator<SelectionKey> it = this.selector.selectedKeys().iterator();
            while (it.hasNext()) {
                SelectionKey key = it.next();
                it.remove();
                if (key.isValid()) {
                    if (key.isAcceptable()) {
                        this.accept(key);
                    }
                    if (key.isReadable()) {
                        this.read(key);
                    }
                }
            }
        }

    }

    @SneakyThrows
    private void read(SelectionKey key) {
        this.readBuf.clear();
        SocketChannel sc= (SocketChannel)key.channel();
        int index = sc.read(this.readBuf);
        if (index == -1) {
            key.channel().close();
            key.cancel();
            return;
        }

        this.readBuf.flip();
        byte[] bytes = new byte[this.readBuf.remaining()];
        this.readBuf.get(bytes);

        String body = new String(bytes);
        System.out.println(body);

    }

    @SneakyThrows
    private void accept(SelectionKey key) {
        ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
        SocketChannel sc = ssc.accept();
        sc.configureBlocking(false);
        sc.register(this.selector, SelectionKey.OP_READ);
    }
}
