package com.kele.exwebsocket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * @AUTHOR xukele
 * @SINCE 2019/7/8 11:11
 * <p>
 * netty对websocket的支持;
 */
public class WebSocketServer {

	public static void main(String[] args) throws InterruptedException {

		EventLoopGroup parent = new NioEventLoopGroup();
		EventLoopGroup child = new NioEventLoopGroup();
		try {
			ServerBootstrap serverBootstrap = new ServerBootstrap();
			serverBootstrap.group(parent, child)
					.channel(NioServerSocketChannel.class)
					.handler(new LoggingHandler(LogLevel.INFO))
					.childHandler(new TestWebSocInitializer())
					.bind(9010).sync()
					.channel().closeFuture().sync();
		} finally {
			parent.shutdownGracefully();
			child.shutdownGracefully();
		}
	}
}
