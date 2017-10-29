package hadoop.hdfs;

import java.io.IOException;
import java.io.InputStream;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.hdfs.DistributedFileSystem;

public class HdfsTest {
	public static void main(String[] args) throws IOException {
		DistributedFileSystem dfs = HDFSFileSystemLoader.getInstance().getDfs();
		System.out.println(dfs);

		InputStream ins = HDFSUtil.downLoad(dfs, "2016-08-18/1471492741790国际电力同业概况.doc");

		Configuration conf = new Configuration();
		conf.set("fs.default.name", "hdfs://192.168.30.173:9000");
		DistributedFileSystem dfs2 = HDFSFileSystemLoader.getInstance().getDfs(conf);
		System.out.println(dfs2);

		HDFSUtil.upload(dfs2, "国际电力同业概况.doc", ins);
		ins.close();

		return;
		// hdfs://192.168.123.120:9000/user/hadoop/mytest.txt
		// String fileName = "mytest.txt";
		// DistributedFileSystem dfs =
		// HDFSFileSystemLoader.getInstance().getDfs();
		// Path path = new Path(fileName);
		// FSDataInputStream fis = dfs.open(path);
		//
		// String addStr = "wojishi";
		// System.out.println("追加...");
		// FSDataOutputStream fos = dfs.append(path);
		// write(fos, addStr);
		//
		// System.out.println("追加完成");
		// byte[] temp = new byte[1024];
		// System.out.println("读取...");
		// FSDataInputStream fis1 = dfs.open(path);
		// System.out.println("--------------------------------------");
		// read(fis1);
		// System.out.println("--------------------------------------");
	}

	public static void write(FSDataOutputStream fos, String str) throws IOException {
		fos.write(str.getBytes());
		fos.flush();
		fos.close();
	}

	public static void read(FSDataInputStream fis) throws IOException {
		String s = null;
		do {
			s = fis.readLine();
			if (null != s) {
				System.out.println(s);
			}
		} while (null != s || "".equals(s));
		fis.close();
	}
}
