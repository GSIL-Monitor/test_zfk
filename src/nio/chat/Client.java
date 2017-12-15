package nio.chat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Date;
import java.util.Iterator;
import java.util.Scanner;

/**
 * 使用NIO完成网络通信的三个核心
 * 
 * Buffer，负责数据的存储 Channel，负责连接 Selector，多路复用器，监控SelectableChannel的IO状态
 * 
 */
public class Client {
	public static void main(String[] args) throws IOException {
		// 1.获取通道
		SocketChannel sChannel = SocketChannel.open(new InetSocketAddress(
				"127.0.0.1", 8000));

		// 2.通道默认是阻塞的，切换通道为非阻塞
		sChannel.configureBlocking(false);

		// 3.分配指定大小的缓冲区
		ByteBuffer buffer = ByteBuffer.allocate(1024);// 1kb

		/*
		 * 启动接收服务器消息的线程
		 */
		Runnable getmessagehandler = new GetMessageHandler(sChannel);
		Thread t = new Thread(getmessagehandler);
		t.start();

		// 4.准备接受键盘输入
		Scanner scan = new Scanner(System.in);
		System.out.println("############请输入姓名##############");
		String name = scan.nextLine();
		System.out.println("############请输入内容##############");
		// scan.hasNext()会阻塞
		while (scan.hasNext()) {
			String str = scan.nextLine();
			// 上车
			buffer.put((new Date().toString() + "\n(" + name + ")" + str)
					.getBytes());
			// 下车
			buffer.flip();
			sChannel.write(buffer);
			// buffer.rewind();
			// sChannel.write(buffer);
			buffer.clear();
		}

	}

}

/**
 * 该线程的作用是并发运行来接收服务器发送过来的信息， 并输出到控制台
 */
class GetMessageHandler implements Runnable {
	SocketChannel sChannel = null;

	public GetMessageHandler(SocketChannel sChannel) {
		this.sChannel = sChannel;
	}

	public void run() {
		try {

			// 4.获取选择器
			Selector selector = Selector.open();

			// 5.将通道注册到选择器上并且指定监听事务，有人进来事叫我
			sChannel.register(selector, SelectionKey.OP_READ);

			// 6.轮巡式获取选择器上已经准备就绪的事件,selector.select()会阻塞
			while (selector.select() > 0) {
				// 7.获取当前选择器上所有的注册键迭代器
				Iterator<SelectionKey> it = selector.selectedKeys().iterator();

				while (it.hasNext()) {
					// 8.获取注册键
					SelectionKey key = it.next();

					// 9.判断具体是什么事件准备就绪
					if (key.isReadable()) {
						SocketChannel channel = (SocketChannel) key.channel();

						ByteBuffer buffer = ByteBuffer.allocate(1024);

						int len = 0;
						while ((len = channel.read(buffer)) > 0) {
							buffer.flip();
							System.out.println(new String(buffer.array(), 0,
									len));
							buffer.clear();
						}
					} else if (key.isWritable()) {

					} else if (key.isConnectable()) {
						System.out.println("connectable");
					} else if (key.isValid()) {
						System.out.println("valid");
					}
				}
				// 已经处理过了移除
				it.remove();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				sChannel.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
