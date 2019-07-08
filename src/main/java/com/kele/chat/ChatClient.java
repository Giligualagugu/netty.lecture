package com.kele.chat;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 聊天室客户端
 */
public class ChatClient {
	public static void main(String[] args) throws InterruptedException, IOException {

		EventLoopGroup clientGroup = new NioEventLoopGroup();

		try {

			Bootstrap bootstrap = new Bootstrap();

			bootstrap.group(clientGroup)
					.channel(NioSocketChannel.class)
					.handler(new ChatClientInitializer());

			Channel channel = bootstrap.connect("localhost", 9002).sync().channel();
			//获取键盘输入,发送到服务器;
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

			while (true) {
				channel.writeAndFlush(bufferedReader.readLine() + "\r\n");
			}

		} finally {
			clientGroup.shutdownGracefully();
		}
	}
}
