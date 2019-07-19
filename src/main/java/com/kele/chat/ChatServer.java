package com.kele.chat;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * 聊天室。。。
 */
public class ChatServer {
	public static void main(String[] args) throws InterruptedException {

		EventLoopGroup bossgroup = new NioEventLoopGroup();
		EventLoopGroup workgroup = new NioEventLoopGroup();

		try {

			ServerBootstrap serverBootstrap = new ServerBootstrap();

			serverBootstrap.group(bossgroup, workgroup)
					.channel(NioServerSocketChannel.class)
					.handler(new LoggingHandler(LogLevel.INFO))
					.childHandler(new ChatServerInitializer())
					.bind(9001).sync()
					.channel().closeFuture().sync();
		} finally {
			bossgroup.shutdownGracefully();
			workgroup.shutdownGracefully();
		}

	}
}
