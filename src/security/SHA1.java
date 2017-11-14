package security;

import java.security.DigestException;
import java.security.MessageDigest;

public class SHA1 {
	private static final char[] HEX_DIGITS = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd',
			'e', 'f' };

	public static void main(String[] args) throws DigestException {
			System.out.println(encode("你好"));
	}

	/**
	 * SHA1 安全加密算法
	 * 
	 * @param maps
	 *            参数key-value map集合
	 * @return
	 * @throws DigestException
	 */
	public static String encode(String str) throws DigestException {
		if (str == null) {
			return null;
		}
		try {
			MessageDigest messageDigest = MessageDigest.getInstance("SHA1");
			messageDigest.update(str.getBytes());
			return getFormattedText(messageDigest.digest());
		} catch (Exception e) {
			throw new DigestException("签名错误！");
		}
	}

	/**
	 * Takes the raw bytes from the digest and formats them correct.
	 * 
	 * @param bytes
	 *            the raw bytes from the digest.
	 * @return the formatted bytes.
	 */
	private static String getFormattedText(byte[] bytes) {
		int len = bytes.length;
		// 把密文转换成十六进制的字符串形式
//		StringBuilder buf = new StringBuilder(len * 2);
//		for (int j = 0; j < len; j++) {
//			buf.append(HEX_DIGITS[(bytes[j] >> 4) & 0x0f]);
//			buf.append(HEX_DIGITS[bytes[j] & 0x0f]);
//		}
//		return buf.toString();

		StringBuffer hexString = new StringBuffer();
		for (int i = 0; i < bytes.length; i++) {
			String shaHex = Integer.toHexString(bytes[i] & 0xFF);
			if (shaHex.length() < 2) {
				hexString.append(0);
			}
			hexString.append(shaHex);
		}
		return hexString.toString().toUpperCase();
	}

}
