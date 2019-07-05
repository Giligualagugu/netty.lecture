package com.kele.serversoket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * 聊天服务器；
 * socketserver服务器；用于处理socket链接；
 */
public class ChattingServer {

	public static void main(String[] args) throws InterruptedException {
		new ChattingServer().init();
	}

	private void init() throws InterruptedException {

		EventLoopGroup bossgroup = new NioEventLoopGroup();
		EventLoopGroup workgroup = new NioEventLoopGroup();

		try {

			ServerBootstrap serverBootstrap = new ServerBootstrap();

			serverBootstrap.group(bossgroup, workgroup)
					.channel(NioServerSocketChannel.class)
					.childHandler(new ServerInitializer())
					.bind(8033).sync()
					.channel().closeFuture().sync();

		} finally {
			bossgroup.shutdownGracefully();
			workgroup.shutdownGracefully();
		}

	}

}
