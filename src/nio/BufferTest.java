package nio;

import java.nio.ByteBuffer;
import java.util.Arrays;

/**
 * 一、Buffer缓冲区，负责数据的存取，缓冲区就是一个数组 根据数据类型（基本类型，boolean除外）不同，提供相对应的缓存区 byte ByteBuffer char
 * short int long float double
 * 
 * @author ZHUFUKUN
 * 
 */
public class BufferTest {
	public static void main(String[] args) {
		/*
		 * 方法：allocate(int size)，创建一个指定大小的缓冲区 属性：capacity，容量，表示缓冲区的最大数据容量
		 * limit：限制，第一不应该读取或者写入数组的下标 position：位置，下一个读取或者写入数组的下标
		 */
		ByteBuffer buf = ByteBuffer.allocate(8);
		System.out.println("position=" + buf.position()); // 0
		System.out.println("limit=" + buf.limit()); // 8
		System.out.println("capacity=" + buf.capacity()); // 8
		System.out.println("==============================");
		// 向缓冲区中写入数据
		buf.put((byte) 10);
		buf.put((byte) 20);
		buf.put((byte) 30);
		buf.put((byte) 40);
		buf.put(new byte[] { 50, 60 });
		System.out.println("position=" + buf.position()); // 6
		System.out.println("limit=" + buf.limit()); // 8
		System.out.println("capacity=" + buf.capacity()); // 8
		System.out.println("==============================");

		// 反转，切换读写数据模式，以便取数据
		buf.flip();
		System.out.println("position=" + buf.position()); // 0
		System.out.println("limit=" + buf.limit()); // 6
		System.out.println("capacity=" + buf.capacity()); // 8
		System.out.println("==============================");
		// 当前位置与限制之间是否有数据
		if (buf.hasRemaining()) {
			for (int i = 0; i < buf.remaining(); i++) {
				byte b = buf.get(i);
				System.out.print(b + " ");
			}
			System.out.println();
		}
		System.out.println("==============================");
		System.out.println("position=" + buf.position()); // 0
		System.out.println("limit=" + buf.limit()); // 6
		System.out.println("capacity=" + buf.capacity()); // 8
		byte[] bs = new byte[buf.limit()];
		buf.get(bs);
		System.out.println(Arrays.toString(bs));
		System.out.println("position=" + buf.position()); // 0
		System.out.println("limit=" + buf.limit()); // 6
		System.out.println("capacity=" + buf.capacity()); // 8

		System.out.println("==============================");
		buf.flip();
		System.out.println("position=" + buf.position());
		System.out.println("limit=" + buf.limit());
		System.out.println("capacity=" + buf.capacity());
		byte[] bs2 = new byte[buf.limit()];
		buf.get(bs2);
		System.out.println(Arrays.toString(bs2));
	}
}
