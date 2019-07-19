package com.kele.niotest;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

/**
 * @AUTHOR xukele
 * @SINCE 2019/7/18 16:04
 * <p>
 * 内存映射buffer, 把文件都映射到内存中;
 * <p>
 * 注意文件大小,需要限制映射到内存的量;
 */
public class Test06MappedBuffer {
	public static void main(String[] args) throws IOException {

		RandomAccessFile randomAccessFile = new RandomAccessFile(new File("keke.txt"), "rw");

		FileChannel channel = randomAccessFile.getChannel();

		FileLock lock = channel.lock();

		MappedByteBuffer buffer = channel.map(FileChannel.MapMode.READ_WRITE, 0, 64);

		buffer.put(0, (byte) 'c');

		buffer.put(11, (byte) 'q');

		lock.release();
		
		randomAccessFile.close();

	}
}
