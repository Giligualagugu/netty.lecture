package com.kele.clientsocket;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * socket 客户端功能； 聊天客户端；
 */
public class ChattinClient {

	public static void main(String[] args) throws InterruptedException {
		new ChattinClient().init();
	}

	private void init() throws InterruptedException {

		EventLoopGroup clientgroup = new NioEventLoopGroup();

		try {

			Bootstrap bootstrap = new Bootstrap();
			bootstrap.group(clientgroup)
					.channel(NioSocketChannel.class)
					.handler(new ClientInitializer());

			bootstrap.connect("localhost", 8033).sync()
					.channel().closeFuture().sync();

		} finally {
			clientgroup.shutdownGracefully();
		}
	}
}
