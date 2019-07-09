package com.kele.exwebsocket;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

import java.time.LocalDateTime;

/**
 * @AUTHOR xukele
 * @SINCE 2019/7/8 11:35
 * <p>
 * 处理websocket的消息; 对象类型是TextWebSocketFrame;处理文本桢;
 */
public class MyWebSochandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {

		System.out.println("获取消息:" + msg.text());

		//响应给客户端;

		ctx.channel().writeAndFlush(new TextWebSocketFrame("来自服务器的响应:" + LocalDateTime.now()));

	}

	@Override
	public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
		System.out.println("链接建立:" + ctx.channel().id().asLongText());
	}

	@Override
	public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
		System.out.println("链接关闭" + ctx.channel().id().asLongText());
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {

		ctx.channel().close();
	}
}
