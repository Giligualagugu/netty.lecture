package com.kele.niotest;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @AUTHOR xukele
 * @SINCE 2019/7/9 16:07
 */
public class Test02ReadFile {

	public static void main(String[] args) throws IOException {

		FileInputStream fileInputStream = new FileInputStream("keke.txt");

		FileChannel channel = fileInputStream.getChannel();

		ByteBuffer buffer = ByteBuffer.allocate(512);

		/*
		文件内容读入buffer里; 此时buffer是写入状态,需要切换才能读出数据;
		 */
		channel.read(buffer);

		buffer.flip();

		while (buffer.hasRemaining()) {
			System.out.println((char) buffer.get());
		}

	}
}
