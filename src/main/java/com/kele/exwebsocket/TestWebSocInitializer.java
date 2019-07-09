package com.kele.exwebsocket;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * @AUTHOR xukele
 * @SINCE 2019/7/8 11:11
 */
public class TestWebSocInitializer extends ChannelInitializer<SocketChannel> {
	@Override
	protected void initChannel(SocketChannel ch) throws Exception {

		ChannelPipeline pipeline = ch.pipeline();

		pipeline.addLast(new HttpServerCodec());
		pipeline.addLast(new ChunkedWriteHandler());// 用 块 的方式取写消息;
		pipeline.addLast(new HttpObjectAggregator(8192));//把消息聚合成一个fullhttpObject;

		//处理websocket的协议;
		pipeline.addLast(new WebSocketServerProtocolHandler("/kelews"));
		//websocketPath content_path初始化地址为 /kelews

		pipeline.addLast(new MyWebSochandler());
	}
}
