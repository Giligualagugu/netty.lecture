package com.kele.niotest;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @AUTHOR xukele
 * @SINCE 2019/7/18 17:04
 * <p>
 * Selector 在网络编程的应用;
 * 监控多个端口;
 */
public class Test08Selector {

	public static void main(String[] args) throws Exception {

		int[] ports = {9011, 9012, 9013, 9014};

		Selector selector = Selector.open();

		for (int port : ports) {
			ServerSocketChannel channel = ServerSocketChannel.open();
			channel.configureBlocking(false);
			ServerSocket socket = channel.socket();
			socket.bind(new InetSocketAddress(port));
			channel.register(selector, SelectionKey.OP_ACCEPT);

			System.out.println("监听端口:" + port);

		}

		while (true) {

			int select = selector.select();

			System.out.println("nums :" + select);

			Set<SelectionKey> selectionKeys = selector.selectedKeys();

			Iterator<SelectionKey> iterator = selectionKeys.iterator();

			while (iterator.hasNext()) {
				SelectionKey next = iterator.next();

				if (next.isAcceptable()) {
					ServerSocketChannel serverSocketChannel = (ServerSocketChannel) next.channel();

					SocketChannel accept = serverSocketChannel.accept();

					accept.configureBlocking(false);
					accept.register(selector, SelectionKey.OP_READ);

					iterator.remove();

					System.out.println("获取连接:" + accept);
				} else if (next.isReadable()) {
					SocketChannel socketChannel = (SocketChannel) next.channel();
					while (true) {
						ByteBuffer allocate = ByteBuffer.allocate(512);
						int read = socketChannel.read(allocate);
						if (read < 0) break;
						allocate.flip();
						socketChannel.write(allocate);

					}
					iterator.remove();
				}
			}
		}
	}

}
