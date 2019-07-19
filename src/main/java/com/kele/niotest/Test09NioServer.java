package com.kele.niotest;

import io.netty.util.CharsetUtil;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.*;

/**
 * @AUTHOR xukele
 * @SINCE 2019/7/19 9:51
 * <p>
 * 分发;
 */
public class Test09NioServer {

	private static Map<String, SocketChannel> clients = new HashMap<>();

	public static void main(String[] args) throws IOException {

		Selector selector = Selector.open();
		ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
		serverSocketChannel.bind(new InetSocketAddress(9022));
		serverSocketChannel.configureBlocking(false);
		serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

		while (true) {
			selector.select();
			Set<SelectionKey> keys = selector.selectedKeys();
			keys.forEach(key -> {
				try {
					if (key.isAcceptable()) {
						buidConnection(selector, key);
					} else if (key.isReadable()) {
						responseMessage(key);
					}
				} catch (IOException o) {
					/*
					如果异常,在客户端map里删除此客户端;
					 */
					o.printStackTrace();
					Set<Map.Entry<String, SocketChannel>> entries = clients.entrySet();
					for (Map.Entry<String, SocketChannel> entry : entries) {
						if (entry.getValue().equals(key.channel())) {
							System.out.println("delete wrong connect:" + entry);
							entries.remove(entry);
							break;
						}

					}
					key.cancel();
				}
			});
			keys.clear();
		}
	}

	private static void responseMessage(SelectionKey key) throws IOException {
		String senderKey = null;
		SocketChannel socketChannel;
		System.out.println("传输数据");
		socketChannel = (SocketChannel) key.channel();
		ByteBuffer allocate = ByteBuffer.allocate(1024);
		int read = 0;
		read = socketChannel.read(allocate);
		if (read > 0) {
			allocate.flip();
			String mess = String.valueOf(CharsetUtil.UTF_8.decode(allocate).array());
			System.out.println(mess);

			// 获取客户端的id;
			for (Map.Entry<String, SocketChannel> entry : clients.entrySet()) {
				if (socketChannel.equals(entry.getValue())) {
					senderKey = entry.getKey();
				}
			}
			//分发给其他客户端;
			for (Map.Entry<String, SocketChannel> entry : clients.entrySet()) {
				SocketChannel channel = entry.getValue();
				if (channel.isConnected()) {
					ByteBuffer buffer = ByteBuffer.allocate(1024);
					buffer.put(("服务器响应:" + mess).getBytes());
					buffer.flip();
					channel.write(buffer);
				}
			}
		}

	}

	private static void buidConnection(Selector selector, SelectionKey key) throws IOException {
		SocketChannel socketChannel;
		ServerSocketChannel serverchannel = (ServerSocketChannel) key.channel();
		socketChannel = serverchannel.accept();
		socketChannel.configureBlocking(false);
		socketChannel.register(selector, SelectionKey.OP_READ);
		String clid = UUID.randomUUID().toString();
		clients.put(clid, socketChannel);// 存贮客户端连接;
		System.out.println("链接建立:" + clid + socketChannel);
	}
}
