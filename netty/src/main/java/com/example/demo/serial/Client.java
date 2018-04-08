package com.example.demo.serial;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class Client {

	public static void main(String[] args) throws Exception {
		//ONE:
		//1 线程工作组
		EventLoopGroup work = new NioEventLoopGroup();
		
		//TWO:
		//3 辅助类。用于帮助我们创建NETTY服务
		Bootstrap b = new Bootstrap();
		b.group(work)	//绑定工作线程组
		 .channel(NioSocketChannel.class)	//设置NIO的模式
		 // 初始化绑定服务通道
		 .handler(new ChannelInitializer<SocketChannel>() {
			@Override
			protected void initChannel(SocketChannel sc) throws Exception {
				
				//序列化:
				sc.pipeline().addLast(MarshallingCodeCFactory.buildMarshallingDecoder());
				sc.pipeline().addLast(MarshallingCodeCFactory.buildMarshallingEncoder());
				
				// 为通道进行初始化： 数据传输过来的时候会进行拦截和执行
				sc.pipeline().addLast(new ClientHandler());
			}
		 });
		
		ChannelFuture cf =  b.connect("127.0.0.1", 8765).syncUninterruptibly();
		
		for(int i =0; i < 100; i++){
			System.out.println("i " + i);
			//设置数据
			Req req = new Req();
			req.setId("" + i);
			req.setName("姓名" + i);
			req.setRequestMessage("数据信息" + i);
//			String path = System.getProperty("user.dir") + File.separatorChar + "sources" + File.separatorChar + "004.jpg";
//			File file = new File(path);
//			FileInputStream fis = new FileInputStream(file);
//			byte[] data = new byte[fis.available()];
//			fis.read(data);
//			fis.close();
			req.setAttachment("aaa".getBytes());
			//传输数据
			cf.channel().writeAndFlush(req);
			
		}
		
		
		//释放连接
		cf.channel().closeFuture().sync();
		work.shutdownGracefully();
	}
}
