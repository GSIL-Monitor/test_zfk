package zip;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import org.apache.commons.lang.RandomStringUtils;

import util.DateUtils;

/**
 * <p>
 * Description:
 * </p>
 *
 * @author evan
 * @version 1.0.0
 * @date 2018/11/30
 */
public class ZipUtils {
	private ZipUtils() {
	}

	public static void doCompress(String srcFile, String zipFile) throws IOException {
		doCompress(new File(srcFile), new File(zipFile));
	}

	/**
	 * 文件压缩
	 *
	 * @param srcFile
	 *            目录或者单个文件
	 * @param zipFile
	 *            压缩后的ZIP文件
	 */
	public static void doCompress(File srcFile, File zipFile) throws IOException {
		ZipOutputStream out = null;
		try {
			out = new ZipOutputStream(new FileOutputStream(zipFile));
			doCompress(srcFile, out);
		} catch (Exception e) {
			throw e;
		} finally {
			out.close();// 记得关闭资源
		}
	}

	public static void doCompress(String fileName, ZipOutputStream out) throws IOException {
		doCompress(new File(fileName), out);
	}

	public static void doCompress(File file, ZipOutputStream out) throws IOException {
		doCompress(file, out, "");
	}

	public static void doCompress(File inFile, ZipOutputStream out, String dir) throws IOException {
		if (inFile.isDirectory()) {
			File[] files = inFile.listFiles();
			if (files != null && files.length > 0) {
				for (File file : files) {
					String name = inFile.getName();
					if (!"".equals(dir)) {
						name = dir + "/" + name;
					}
					ZipUtils.doCompress(file, out, name);
				}
			}
		} else {
			ZipUtils.doZip(inFile, out, dir);
		}
	}

	public static void doZip(File inFile, ZipOutputStream out, String dir) throws IOException {
		String entryName = null;
		if (!"".equals(dir)) {
			entryName = dir + "/" + inFile.getName();
		} else {
			entryName = inFile.getName();
		}
		ZipEntry entry = new ZipEntry(entryName);
		out.putNextEntry(entry);

		int len = 0;
		byte[] buffer = new byte[1024];
		FileInputStream fis = new FileInputStream(inFile);
		while ((len = fis.read(buffer)) > 0) {
			out.write(buffer, 0, len);
			out.flush();
		}
		out.closeEntry();
		fis.close();
	}

	public static void unZip(String path, String toPath) {
		unZip(new File(path), toPath);
	}

	public static void unZip(File file, String toPath) {
		long startTime = System.currentTimeMillis();

		// 输入源zip路径
		ZipInputStream Zin = null;
		BufferedInputStream Bin = null;
		try {
			Zin = new ZipInputStream(new FileInputStream(file));
			Bin = new BufferedInputStream(Zin);
			File Fout = null;
			ZipEntry entry;
			while ((entry = Zin.getNextEntry()) != null && !entry.isDirectory()) {
				BufferedOutputStream Bout = null;
				////循环每个文件的流打开后，都得关闭
				try {
					Fout = new File(toPath, entry.getName());
					if (!Fout.exists()) {
						(new File(Fout.getParent())).mkdirs();
					}
					Bout = new BufferedOutputStream(new FileOutputStream(Fout));
					int len;
					byte [] buffer = new byte[1024*1024];
					while ((len = Bin.read(buffer)) != -1) {
						Bout.write(buffer, 0 , len);
					}
					Bout.flush();
					System.out.println(Fout + "解压成功");
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					try {
						if (Bout != null) {
							Bout.close();
						}
					} catch (Exception e2) {
						e2.printStackTrace();
					}
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != Bin) {
					Bin.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
			try {
				if (null != Zin) {
					Zin.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		long endTime = System.currentTimeMillis();
		System.out.println("耗费时间： " + (endTime - startTime) + " ms");
	}

	public static void main(String[] args) throws IOException {

		/**
		 * 指定路径打包
		 */
		// doCompress("D:\\data", "D:/java.zip");

//		String path = "D:\\data\\nfs\\files\\process\\content\\";
//		String zipName = DateUtils.format(DateUtils.SECOND_N) + ".zip";
//
//		ZipOutputStream out = null;
//		try {
//			out = new ZipOutputStream(new FileOutputStream("D:\\" + zipName));
//
//			File souceFile = new File(path);
//			File[] tempList = souceFile.listFiles();
//			for (int i = 0; i < tempList.length; i++) {
//				if (tempList[i].isFile()) {
//					ZipUtils.doCompress(new File(tempList[i].getPath()), out);
//				}
//			}
//		} catch (Exception e) {
//		} finally {
//			try {
//				if (null != out)
//					out.close();
//			} catch (IOException e2) {
//				e2.printStackTrace();
//			}
//		}

		/**
		 * 解压文件
		 */
		ZipUtils.unZip("D:\\attachment20190118112847.zip", "D:\\unzip");

		/**
		 * 选择文件打包
		 */
		/*
		 * String fileNameArr =
		 * "D:\\data\\node_root_template.xlsx,D:\\data\\node_root_template1.xlsx,D:\\data\\node_sentences.xlsx,D:\\data\\node_template.xlsx,D:\\data\\sentences_template34.xlsx";
		 * String[] files = fileNameArr.split(","); String zipName =
		 * RandomStringUtils.random(4, "0123456789") + ".zip";
		 * 
		 * ZipOutputStream out = null; try { out = new ZipOutputStream(new
		 * FileOutputStream("D:\\" + zipName)); for (String fileName : files) {
		 * ZipUtils.doCompress(new File(fileName), out); } } catch (Exception e)
		 * { } finally { try { if (null != out) out.close(); } catch
		 * (IOException ex) {
		 * 
		 * } }
		 */

	}
}
