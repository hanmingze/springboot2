package com.example.demo.serial;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class ServerHandler extends ChannelInboundHandlerAdapter {

	/**
	 * 当我们通道进行激活的时候 触发的监听方法
	 */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
    
    	System.err.println("--------通道激活------------");
    }
	
    /**
     * 当我们的通道里有数据进行读取的时候 触发的监听方法
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx /*NETTY服务上下文*/, Object msg /*实际的传输数据*/) throws Exception {
        Req req = (Req)msg;
    	System.err.println("SERVER: "+ req.getId() + "," + req.getName() + "," + req.getRequestMessage());

    	Resp resp = new Resp();
    	resp.setId("响应id" + req.getId() );
    	resp.setName("响应名称：" + req.getName());
    	resp.setResponseMessage("响应信息: " + req.getRequestMessage());
    	ctx.writeAndFlush(resp);
    }
    
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
       System.err.println("--------数据读取完毕----------");
    }
    
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
    	System.err.println("--------数据读异常----------");
        ctx.close();
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
}
