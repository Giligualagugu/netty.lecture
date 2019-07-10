package com.kele.niotest;

import java.nio.IntBuffer;
import java.security.SecureRandom;

/**
 * @AUTHOR xukele
 * @SINCE 2019/7/9 15:42
 */
public class Test01 {
	public static void main(String[] args) {
		IntBuffer intBuffer = IntBuffer.allocate(10);

		SecureRandom secureRandom = new SecureRandom();

		for (int i = 0; i < intBuffer.capacity(); i++) {
			intBuffer.put(secureRandom.nextInt(50));
		}

		intBuffer.flip(); //改变状态值,实现读写切换;

		while (intBuffer.hasRemaining()) {
			System.out.println(intBuffer.get());
		}
	}
}
