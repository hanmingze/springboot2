package com.example.demo.serial;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

public class ClientHandler  extends ChannelInboundHandlerAdapter {

	
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
    	try {
    		Resp resp = (Resp)msg;
    		System.err.println("CLIENT: " + resp.getId() + "," + resp.getName() + resp.getResponseMessage());
		} finally {
			ReferenceCountUtil.release(msg);
		}
    }
    
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
        ctx.close();
    }
    
}
