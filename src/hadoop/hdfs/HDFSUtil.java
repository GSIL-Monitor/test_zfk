package hadoop.hdfs;

import java.io.IOException;
import java.io.InputStream;

import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hdfs.DistributedFileSystem;


public class HDFSUtil {

	public static String upload(DistributedFileSystem dfs,
			String document_name, InputStream inputStream) {
		FSDataOutputStream outputStream = null;
		try {
			String hdfs_url = System.currentTimeMillis() + "_" + document_name;
			Path path = new Path(hdfs_url);
			outputStream = dfs.create(path);
			byte[] buffer = new byte[1024 * 1024];
			int len = 0;
			while ((len = inputStream.read(buffer)) != -1) {
				outputStream.write(buffer, 0, len);
			}
			outputStream.flush();
			return hdfs_url;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				if (inputStream != null)
					inputStream.close();
				if (outputStream != null)
					outputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 上传到HDFS
	 * 
	 * @param document_name
	 * @param fileBytes
	 * @return
	 */
	public static String upload(DistributedFileSystem dfs,
			String document_name, byte[] fileBytes) {
		FSDataOutputStream outputStream = null;
		try {
			String hdfs_url = System.currentTimeMillis() + "_" + document_name;
			Path path = new Path(hdfs_url);
			outputStream = dfs.create(path);
			outputStream.write(fileBytes);
			outputStream.flush();
			return hdfs_url;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				if (outputStream != null)
					outputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 删除文件
	 * 
	 * @param hdfs_url
	 * @return
	 */
	public synchronized static boolean delete(DistributedFileSystem dfs,
			String hdfs_url) {
		try {
			Path path = new Path(hdfs_url);
			// 检查是否存在文件
			if (dfs.exists(path)) {
				// 有则删除
				dfs.delete(path, true);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 下载到文件流（其他地方调的时候一定要记得关闭流）
	 * 
	 * @param hdfs_url
	 * @return
	 */
	public static InputStream downLoad(DistributedFileSystem dfs,
			String hdfs_url) {
		try {
			Path path = new Path(hdfs_url);
			FSDataInputStream inputStream = dfs.open(path);
			return inputStream;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {

		}
	}

}
