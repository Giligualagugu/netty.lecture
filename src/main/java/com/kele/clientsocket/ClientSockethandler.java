package com.kele.clientsocket;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.time.LocalDateTime;

/**
 * socket客户端 发送消息；
 */
public class ClientSockethandler extends SimpleChannelInboundHandler<String> {
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {

		System.out.println("服务器地址：" + ctx.channel().remoteAddress());
		System.out.println("服务器响应：" + msg);

		Thread.sleep(6000);

		ctx.writeAndFlush(LocalDateTime.now().toString());

	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		ctx.writeAndFlush("开启。。。");
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {

		ctx.channel().close();
	}
}
