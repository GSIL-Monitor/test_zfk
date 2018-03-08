package ftp;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

/**
 * 
 * <p>
 * Description:
 * </p>
 * 
 * @date 2017年12月21日
 * @author 朱富昆
 * @version 1.0.0 <artifactId>commons-net</artifactId>
 */
public class FtpUtil {

	private static String host = "192.168.223.129";
	private static int port = 4449;
	private static String username = "zhufukun";
	private static String password = "123456";
	private static String basePath = "/home/zhufukun/upload/";

	/**
	 * Description: 向FTP服务器上传文件
	 * 
	 * @param filePath
	 *            FTP服务器文件存放路径。例如分日期存放：/2015/01/01。文件的路径为basePath+filePath
	 * @param fileName
	 *            上传到FTP服务器上的文件名
	 * @param input
	 *            输入流
	 * @return 成功返回true，否则返回false
	 */
	public static String uploadFile(String filePath, String fileName, InputStream input) {
		FTPClient ftp = new FTPClient();
		try {
			int reply;
			ftp.connect(host, port);// 连接FTP服务器
			// 如果采用默认端口，可以使用ftp.connect(host)的方式直接连接FTP服务器
			ftp.login(username, password);// 登录
			reply = ftp.getReplyCode();
			if (!FTPReply.isPositiveCompletion(reply)) {
				ftp.disconnect();
				return null;
			}
			// 切换到上传目录
			if (!ftp.changeWorkingDirectory(basePath + filePath)) {
				// 如果目录不存在创建目录
				String[] dirs = filePath.split("/");
				String tempPath = basePath;
				for (String dir : dirs) {
					if (null == dir || "".equals(dir)){
						continue;
					}
					tempPath += "/" + dir;
					if (!ftp.changeWorkingDirectory(tempPath)) {
						if (!ftp.makeDirectory(tempPath)) {
							return null;
						}
						// else {
						// ftp.changeWorkingDirectory(tempPath);
						// }
					}
				}
			}

			// 切换到上传目录
			if (!ftp.changeWorkingDirectory(basePath + filePath)) {
				return null;
			}

			// 设置文件类型，二进制
			ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
			// 设置缓冲区大小
			ftp.setBufferSize(2048);
			// 设置字符编码
			ftp.setControlEncoding("UTF-8");

			// 上传文件
			if (!ftp.storeFile(fileName, input)) {
				return null;
			}
			return basePath + filePath + fileName;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				ftp.logout();
			} catch (IOException e) {
			}
			if (ftp.isConnected()) {
				try {
					ftp.disconnect();
				} catch (IOException ioe) {
				}
			}
		}
		return null;
	}

	/**
	 * 从FTP服务器下载文件
	 * 
	 * @param filePath
	 *            FTP服务器上的相对路径
	 * @param fileName
	 *            要下载的文件名
	 * @return
	 */
	public static byte[] downloadFile(String filePath, String fileName) {
		FTPClient ftp = new FTPClient();
		try {
			int reply;
			ftp.connect(host, port);
			// 如果采用默认端口，可以使用ftp.connect(host)的方式直接连接FTP服务器
			ftp.login(username, password);// 登录
			reply = ftp.getReplyCode();
			if (!FTPReply.isPositiveCompletion(reply)) {
				ftp.disconnect();
				return null;
			}
			// 切换到上传目录
			if (!ftp.changeWorkingDirectory(basePath + filePath)) {
				return null;
			}
			InputStream ins = ftp.retrieveFileStream(fileName);
			return inputStream2Byte(ins);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				ftp.logout();
			} catch (IOException e) {
			}
			if (ftp.isConnected()) {
				try {
					ftp.disconnect();
				} catch (IOException ioe) {
				}
			}
		}
		return null;
	}

	public static byte[] inputStream2Byte(InputStream ins) {
		ByteArrayOutputStream out = null;
		try {
			out = new ByteArrayOutputStream();
			byte[] buffer = new byte[2048];
			int len;
			while ((len = ins.read(buffer)) != -1) {
				out.write(buffer, 0, len);
			}
			out.flush();
			return out.toByteArray();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				ins.close();
			} catch (IOException e) {
			}
			try {
				out.close();
			} catch (IOException e) {
			}
		}
		return null;
	}

	/**
	 * 从FTP服务器下载文件
	 * 
	 * @param filePath
	 *            FTP服务器上的相对路径
	 * @param fileName
	 *            要下载的文件名
	 * @param out
	 *            输出
	 * @return
	 */
	public static boolean downloadFile(String filePath, String fileName, OutputStream out) {
		FTPClient ftp = new FTPClient();
		boolean result = false;
		try {
			int reply;
			ftp.connect(host, port);
			// 如果采用默认端口，可以使用ftp.connect(host)的方式直接连接FTP服务器
			ftp.login(username, password);// 登录
			reply = ftp.getReplyCode();
			if (!FTPReply.isPositiveCompletion(reply)) {
				ftp.disconnect();
				return result;
			}
			// 切换到上传目录
			if (!ftp.changeWorkingDirectory(basePath + filePath)) {
				return result;
			}
			result = ftp.retrieveFile(fileName, out);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				ftp.logout();
			} catch (IOException e) {
			}
			if (ftp.isConnected()) {
				try {
					ftp.disconnect();
				} catch (IOException ioe) {
				}
			}
		}
		return result;
	}

	public static boolean deleteFile(String filePath, String fileName) {
		FTPClient ftp = new FTPClient();
		boolean result = false;
		try {
			int reply;
			ftp.connect(host, port);
			// 如果采用默认端口，可以使用ftp.connect(host)的方式直接连接FTP服务器
			ftp.login(username, password);// 登录
			reply = ftp.getReplyCode();
			if (!FTPReply.isPositiveCompletion(reply)) {
				ftp.disconnect();
				return result;
			}
			// 切换到上传目录
			if (!ftp.changeWorkingDirectory(basePath + filePath)) {
				return result;
			}
			// 删除文件
			result = ftp.deleteFile(fileName);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				ftp.logout();
			} catch (IOException e) {
			}
			if (ftp.isConnected()) {
				try {
					ftp.disconnect();
				} catch (IOException ioe) {
				}
			}
		}
		return result;
	}

}
