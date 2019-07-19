package com.kele.niotest;

import java.nio.ByteBuffer;

/**
 * @AUTHOR xukele
 * @SINCE 2019/7/18 14:38
 */
public class Test05ByteBuffer {

	public static void main(String[] args) {

		ByteBuffer buffer = ByteBuffer.allocate(8);

		buffer.putInt(10);  //4个byte
		buffer.putInt(12);// 4个byte;

		buffer.flip();

		System.out.println(buffer.getInt());
		System.out.println(buffer.getInt());

		ByteBuffer byteBuffer = ByteBuffer.allocateDirect(64);

	}
}
