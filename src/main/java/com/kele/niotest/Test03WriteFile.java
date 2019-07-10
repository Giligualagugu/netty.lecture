package com.kele.niotest;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @AUTHOR xukele
 * @SINCE 2019/7/9 16:13
 */
public class Test03WriteFile {
	public static void main(String[] args) throws IOException {

		FileOutputStream fileOutputStream = new FileOutputStream("keke.txt");

		FileChannel channel = fileOutputStream.getChannel();

		ByteBuffer buffer = ByteBuffer.allocate(512);

		byte[] bytes = "are you ok".getBytes();

		for (byte bb : bytes) {
			buffer.put(bb);
		}

		buffer.flip();

		channel.write(buffer);

		fileOutputStream.close();
		/*
		写入文件,通过buffer,写入到channel里面;
		 */
	}
}
