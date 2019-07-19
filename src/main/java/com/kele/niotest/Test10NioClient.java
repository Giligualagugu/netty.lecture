package com.kele.niotest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @AUTHOR xukele
 * @SINCE 2019/7/19 11:04
 * <p>
 * nio client
 */
public class Test10NioClient {

	public static void main(String[] args) throws Exception {

		SocketChannel socketChannel = SocketChannel.open();

		Selector selector = Selector.open();
		socketChannel.configureBlocking(false);
		socketChannel.register(selector, SelectionKey.OP_CONNECT);
		socketChannel.connect(new InetSocketAddress("localhost", 9022));

		while (true) {
			selector.select();
			Set<SelectionKey> selectionKeys = selector.selectedKeys();

			selectionKeys.forEach(key -> {
				try {
					if (key.isConnectable()) {

						SocketChannel client = (SocketChannel) key.channel();
						if (client.isConnectionPending()) {
							finshedConnection(client);
						}
						client.register(selector, SelectionKey.OP_READ);

					} else if (key.isReadable()) {

						SocketChannel client = (SocketChannel) key.channel();

						ByteBuffer readbuff = ByteBuffer.allocate(1024);

						int count = client.read(readbuff);
						if (count > 0) {
							String mess = new String(readbuff.array(), 0, count);
							System.out.println("收到信息:" + mess);
						}

					}
				} catch (IOException iox) {
					iox.printStackTrace();
				}

			});

			selectionKeys.clear();

		}
	}

	private static void finshedConnection(SocketChannel client) throws IOException {
		client.finishConnect();
		ByteBuffer buffer = ByteBuffer.allocate(512);
		buffer.put((LocalDateTime.now() + "connect!").getBytes());
		buffer.flip();
		client.write(buffer);

		ExecutorService executorService = Executors.newSingleThreadExecutor(Executors.defaultThreadFactory());
		executorService.submit(() -> {
			while (true) {
				buffer.clear();
				InputStreamReader inputStreamReader = new InputStreamReader(System.in);
				BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
				buffer.put(bufferedReader.readLine().getBytes());
				buffer.flip();
				client.write(buffer);
			}
		});
	}
}
