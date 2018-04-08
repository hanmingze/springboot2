package com.example.demo.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.SneakyThrows;

/**
 * Created by jiaozhiguang on 2018/4/5.
 */
public class Client {

    @SneakyThrows
    public static void main(String[] args) {

        EventLoopGroup worker = new NioEventLoopGroup();

        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(worker)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        socketChannel.pipeline().addLast(new ClientHander());
                    }
                });

        ChannelFuture cf =  bootstrap.connect("127.0.0.1", 8765).syncUninterruptibly();

        cf.channel().write(Unpooled.copiedBuffer("hello netty!-1".getBytes()));

        Thread.sleep(1000);

        cf.channel().write(Unpooled.copiedBuffer("hello netty!-2".getBytes()));

        Thread.sleep(1000);
        cf.channel().write(Unpooled.copiedBuffer("hello netty!-3".getBytes()));

        Thread.sleep(1000);
        cf.channel().write(Unpooled.copiedBuffer("hello netty!-4".getBytes()));

        Thread.sleep(1000);
        cf.channel().writeAndFlush(Unpooled.copiedBuffer("hello netty!-5".getBytes()));

        cf.channel().closeFuture().sync();
        worker.shutdownGracefully();

    }

}
