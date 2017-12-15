package nio.chat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Server {
	static int clientNum = 0;
	static List<SocketChannel> allSocketChannel = new ArrayList<SocketChannel>();

	public static void main(String[] args) throws IOException {
		// 1.获取通道
		ServerSocketChannel ssChannel = ServerSocketChannel.open();

		// 2.指定端口号
		ssChannel.bind(new InetSocketAddress(8000));

		// 3.通道默认是阻塞的，切换通道为非阻塞
		ssChannel.configureBlocking(false);

		System.out.println("############服务器启动##############");

		// 4.传统，阻塞
		// while (true) {
		// // 阻塞
		// SocketChannel sChannel = ssChannel.accept();
		// }

		/*
		 * nio非阻塞，代码实现太麻烦，nio框架netty
		 */
		// 4.获取选择器
		Selector selector = Selector.open();

		// 5.将通道注册到选择器上并且指定监听事务，有人进来事叫我
		ssChannel.register(selector, SelectionKey.OP_ACCEPT);

		// 6.轮巡式获取选择器上已经准备就绪的事件,selector.select()会阻塞
		while (selector.select() > 0) {
			// 7.获取当前选择器上所有的注册键迭代器
			Iterator<SelectionKey> it = selector.selectedKeys().iterator();

			while (it.hasNext()) {
				// 8.获取注册键
				SelectionKey key = it.next();

				// 9.判断具体是什么事件准备就绪
				if (key.isAcceptable()) {
					SocketChannel channel = ssChannel.accept();
					// 3.通道默认是阻塞的，切换通道为非阻塞
					channel.configureBlocking(false);
					System.out.println("##########客户端连接##########当前客户端数："
							+ ++clientNum);
					channel.register(selector, SelectionKey.OP_READ);
					// channel.register(selector, SelectionKey.OP_CONNECT);
					// 加入集合，用于广播
					allSocketChannel.add(channel);
				} else if (key.isReadable()) {
					SocketChannel channel = (SocketChannel) key.channel();

					ByteBuffer buffer = ByteBuffer.allocate(1024);

					int len = 0;
					while ((len = channel.read(buffer)) > 0) {
						buffer.flip();
						System.out.println(new String(buffer.array(), 0, len));
						// channel.write(buffer);
						for (SocketChannel ch : allSocketChannel) {
							ch.write(buffer);
							buffer.rewind();
						}
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

		System.out.println("#########################");

	}
}
