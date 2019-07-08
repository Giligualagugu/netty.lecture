package com.kele.example3;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

/**
 * @AUTHOR xukele
 * @SINCE 2019/7/8 10:15
 */
public class ThreeInitializer extends ChannelInitializer<SocketChannel> {
	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		ChannelPipeline pipeline = ch.pipeline();

		pipeline.addLast(new IdleStateHandler(5, 7, 10, TimeUnit.SECONDS));
		pipeline.addLast(new TestserverHandler());

	}
}
