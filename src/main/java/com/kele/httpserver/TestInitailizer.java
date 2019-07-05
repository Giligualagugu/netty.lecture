package com.kele.httpserver;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

public class TestInitailizer extends ChannelInitializer<SocketChannel> {

	@Override
	protected void initChannel(SocketChannel ch) throws Exception {

		ChannelPipeline pipeline = ch.pipeline();
		HttpServerCodec httpServerCodec = new HttpServerCodec();
		TestHttpServerHanderl testHttpServerHander = new TestHttpServerHanderl();

		pipeline.addLast("kelecodec", httpServerCodec);
		pipeline.addLast("kelehandler", testHttpServerHander);//自定义处理请求类；

	}
}
