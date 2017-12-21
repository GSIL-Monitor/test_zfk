package ftp;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.SocketException;

import util.FileTransformUtil;

public class Test {
	public static void main(String[] args) throws SocketException, IOException {

		// 上传
		// InputStream in = new BufferedInputStream(
		// new
		// FileInputStream("C:\\Users\\zhufukun\\Desktop\\和美\\images\\朱富昆.JPG"));
		// System.out.println("upload----------->"+FtpUtil.uploadFile("/images/2017-12-21/",
		// "zfk.jpg", in));
		// in.close();

		// 删除
		//System.out.println("delete--------->" + FtpUtil.deleteFile("/images/2017-12-21/", "zfk2.jpg"));

		// 下载
		// byte[] bytes = FtpUtil.downloadFile("/images/", "img91.png");
		// FileTransformUtil.byte2File(bytes, new
		// File("C:\\Users\\zhufukun\\Desktop\\91.png"));

		// System.out.println("download---------->" +
		// FtpUtil.downloadFile("/images/", "img91.png",
		// new FileOutputStream("C:\\Users\\zhufukun\\Desktop\\92.png")));

	}
}
