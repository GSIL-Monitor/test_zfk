package util;

import java.io.IOException;

public class Base64Util {
	/**
	 * 编码
	 * 
	 * @param bstr
	 * @return String
	 */
	public static String encode(byte[] bstr) {
		return new sun.misc.BASE64Encoder().encode(bstr);
	}

	/**
	 * 解码
	 * 
	 * @param str
	 * @return string
	 */
	public static byte[] decode(String str) {
		byte[] bt = null;
		try {
			sun.misc.BASE64Decoder decoder = new sun.misc.BASE64Decoder();
			bt = decoder.decodeBuffer(str);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bt;
	}

	/**
	 * 将字节数组转换为16进制字符串
	 * 
	 * @param bs
	 * @return
	 */
	public static String byte2HexString(byte[] bs) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < bs.length; i++) {
			sb.append(Integer.toHexString(256 + (bs[i] & 0xff)).substring(1));
		}
		return sb.toString();
	}

	/**
	 * 将16进制字符串转换为字节数组
	 * 
	 * @param str
	 * @return
	 */
	public static byte[] hexString2Byte(String str) {
		byte[] bs = new byte[str.length() / 2];
		for (int i = 0; i < bs.length; i++) {
			bs[i] = (byte) Integer.parseInt(str.substring(2 * i, 2 * i + 2), 16);
		}
		return bs;
	}
}
