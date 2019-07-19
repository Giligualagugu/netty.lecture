package com.kele.niotest;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * @AUTHOR xukele
 * @SINCE 2019/7/18 16:18
 */
public class Test07GatheringandScatting {
	public static void main(String[] args) throws IOException {

		ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

		serverSocketChannel.socket().bind(new InetSocketAddress(8899));

		ByteBuffer len = ByteBuffer.allocate(4);

		SocketChannel accept = serverSocketChannel.accept();

		String string = "收到啦";

		ByteBuffer bodt = ByteBuffer.allocate(string.getBytes().length);
		bodt.put(string.getBytes());

		while (true) {

			int count = 0;
			while (count < len.capacity()) {
				int read = accept.read(len);
				count += read;
			}

			System.out.println("readLen" + count);
			
			len.flip();

			accept.write(len);

		}

	}
}
