package com.kele.example3;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * @AUTHOR xukele
 * @SINCE 2019/7/8 10:10
 */
public class TestServer {
	public static void main(String[] args) throws InterruptedException {

		EventLoopGroup parent = new NioEventLoopGroup();
		EventLoopGroup child = new NioEventLoopGroup();
		try {
			ServerBootstrap serverBootstrap = new ServerBootstrap();
			serverBootstrap.group(parent, child)
					.channel(NioServerSocketChannel.class)
					.handler(new LoggingHandler(LogLevel.INFO))
					.childHandler(new ThreeInitializer())
					.bind(9002).sync()
					.channel().closeFuture().sync();
		} finally {
			parent.shutdownGracefully();
			child.shutdownGracefully();
		}
	}
}
