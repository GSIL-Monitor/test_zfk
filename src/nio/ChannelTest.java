package nio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Channel;
import java.nio.channels.FileChannel;
import java.util.Arrays;

/**
 * 二、Channel通道，类似传统流，只是不能直接访问数据，和Buffer进行交互
 * 
 * 
 * @author ZHUFUKUN
 * 
 */
public class ChannelTest {
	public static void main(String[] args) throws IOException {
		// 1.需要一个buffer（买一辆车）
		ByteBuffer buf = ByteBuffer.allocate(1024);

		File fileIn = new File("C:\\Users\\ZHUFUKUN\\Desktop\\111.txt");
		File fileOut = new File("C:\\Users\\ZHUFUKUN\\Desktop\\222.txt");
		FileInputStream fin = new FileInputStream(fileIn);
		FileOutputStream fout = new FileOutputStream(fileOut);

		// 2.有个管道（修一条路）
		FileChannel finChannel = fin.getChannel();
		FileChannel foutChannel = fout.getChannel();

		// 3.从channel中读取数据写入到buffer（上车）
		// int bytesRead = fchannel.read(buf);

		int bytesRead = -1;
		while ((bytesRead = finChannel.read(buf)) != -1) {
			System.out.println(bytesRead + "###" + buf);
			System.out.println(Arrays.toString(buf.array()));
			System.out.println(new String(buf.array()));
			// 4.切换模式，从buffer读取数据（下车）
			buf.flip();
			foutChannel.write(buf);
			// 清空缓存
			buf.clear();
		}

		finChannel.close();
		foutChannel.close();
	}
}
