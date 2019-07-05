package com.kele.chat;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 * 接收聊天室发送的消息；
 * <p>
 * 某个客户端上线后，通知其他客户端；
 */
public class ChatServerMessageHandler extends SimpleChannelInboundHandler<String> {

	private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
		Channel channelsender = ctx.channel();
		System.out.println(msg);
		// 把消息发送到聊天室；
		channelGroup.forEach(channel -> {
			if (!channel.equals(channelsender)) {
				channel.writeAndFlush("公共消息：" + msg);
			} else {
				channel.writeAndFlush("您已发送：" + msg);
			}
		});

	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.channel().close();
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		System.out.println("上线：" + ctx.channel().remoteAddress());

		System.out.println(channelGroup.size());

	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		System.out.println("下线：" + ctx.channel().remoteAddress());
		System.out.println(channelGroup.size());

	}

	@Override
	public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
		Channel channel = ctx.channel();
		//先广播；
		channelGroup.writeAndFlush("客户端上线了：" + channel.remoteAddress());
		//存贮新建立的连接信息；
		channelGroup.add(channel);
	}

	@Override
	public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {

		channelGroup.writeAndFlush("客户端下线了：" + ctx.channel().remoteAddress());

//		channelGroup.remove(ctx.channel());// 此行代码会自动调用
	}
}
