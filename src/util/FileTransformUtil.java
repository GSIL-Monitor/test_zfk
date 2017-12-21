package util;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 文件转换的工具类
 */
public class FileTransformUtil {

	/**
	 * 输入流转换为文件
	 * 
	 * @param ins
	 * @param file
	 */
	public static void inputStream2File(InputStream ins, File file) {
		OutputStream out = null;
		try {
			out = new BufferedOutputStream(new FileOutputStream(file));
			int len = 0;
			byte[] buffer = new byte[2048];
			while ((len = ins.read(buffer)) != -1) {
				out.write(buffer, 0, len);
			}
			out.flush();
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
	}

	/**
	 * 将字节数组转换为文件
	 * 
	 * @param bytes
	 * @param file
	 */
	public static void byte2File(byte[] bytes, File file) {
		OutputStream out = null;
		try {
			out = new BufferedOutputStream(new FileOutputStream(file));
			out.write(bytes);
			out.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				out.close();
			} catch (IOException e) {
			}
		}
	}

	/**
	 * 输入流转换为字节数组
	 * 
	 * @param ins
	 * @return
	 */
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
	 * 
	 * 文件转换的字节数组
	 * 
	 * @param file
	 * @return
	 */
	public static byte[] file2Byte(File file) {
		FileInputStream ins = null;
		ByteArrayOutputStream out = null;
		try {
			ins = new FileInputStream(file);
			out = new ByteArrayOutputStream();
			byte[] buffer = new byte[2048];
			int len;
			while ((len = ins.read(buffer)) != -1) {
				out.write(buffer, 0, len);
			}
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
	 * 将输入输出
	 * 
	 * @param ins
	 * @param out
	 */
	public static void inputStream2OutStream(InputStream ins, OutputStream out) {
		try {
			byte[] buffer = new byte[2048];
			int len;
			while ((len = ins.read(buffer)) != -1) {
				out.write(buffer, 0, len);
			}
			out.flush();
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
	}

}
