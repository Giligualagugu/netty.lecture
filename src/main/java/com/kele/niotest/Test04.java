package com.kele.niotest;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @AUTHOR xukele
 * @SINCE 2019/7/18 14:18
 * <p>
 * <p>
 * 从一个文件读取字节,写入另一个文件中;
 * <p>
 * 注意读写的状态切换;
 * 注意每次读取前清空缓存buffer;
 */
public class Test04 {
	public static void main(String[] args) throws IOException {

		FileInputStream inputStream = new FileInputStream(new File("keke.txt"));

		FileOutputStream outputStream = new FileOutputStream(new File("jiao.txt"));

		FileChannel read = inputStream.getChannel();

		FileChannel write = outputStream.getChannel();

		ByteBuffer byteBuffer = ByteBuffer.allocate(128);

		while (true) {
			byteBuffer.clear();// 每次读取前清空缓存;
			int count = read.read(byteBuffer);

			if (count == -1) break;

			byteBuffer.flip();

			write.write(byteBuffer);

		}

		inputStream.close();

		outputStream.close();

	}
}
