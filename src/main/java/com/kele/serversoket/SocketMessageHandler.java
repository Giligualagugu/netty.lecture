package com.kele.serversoket;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.net.SocketAddress;
import java.util.UUID;

/**
 * 处理sokcet传输过来的message;   msg为接受到的参数；
 */
public class SocketMessageHandler extends SimpleChannelInboundHandler<String> {
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {

		SocketAddress socketAddress = ctx.channel().remoteAddress();
		System.out.println("client address is :" + socketAddress);
		System.out.println("receive msg is :" + msg);
		//处理msg信息，返回响应；
		ctx.channel().writeAndFlush(UUID.randomUUID().toString());

	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		//异常处理， 处理异常时，关闭连接
		cause.printStackTrace();

		ctx.channel().close();
	}
}
