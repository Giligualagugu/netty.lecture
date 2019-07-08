package com.kele.example3;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * @AUTHOR xukele
 * @SINCE 2019/7/8 10:22
 */
public class TestserverHandler extends ChannelInboundHandlerAdapter {

	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {

		if (evt instanceof IdleStateEvent) {
			IdleStateEvent event = (IdleStateEvent) evt;

			String eventTyp = null;

			switch (event.state()) {

				case READER_IDLE:
					eventTyp = "READER_IDLE";
					break;
				case WRITER_IDLE:
					eventTyp = "WRITER_IDLE";
					break;
				case ALL_IDLE:
					eventTyp = "read write idle";
					break;
			}

			System.out.println("超时事件:" + eventTyp);

			ctx.channel().close();
		}

	}
}
