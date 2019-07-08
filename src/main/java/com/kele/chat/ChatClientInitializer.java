package com.kele.chat;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;

/**
 * channel的初始化器;
 * 定义消息处理器;
 * 定义消息的处理格式: 4位长度+真实消息msg; 最大长度是integer.maxvalue;
 * <p>
 * 消息处理机制, socket读取前4位,确定消息体的长度lenbody,然后读取lenbody个byte放入byte[],
 * 解析成String, 就是msg;
 */
public class ChatClientInitializer extends ChannelInitializer<SocketChannel> {
	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		ChannelPipeline pipeline = ch.pipeline();

		pipeline.addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, 0, 4));
		pipeline.addLast(new LengthFieldPrepender(4));
		pipeline.addLast(new StringDecoder(CharsetUtil.UTF_8));
		pipeline.addLast(new StringEncoder(CharsetUtil.UTF_8));

		//消息处理业务;
		pipeline.addLast(new ChatClientMessageHandler());

	}
}
