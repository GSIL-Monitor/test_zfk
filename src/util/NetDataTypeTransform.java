package util;

import java.io.UnsupportedEncodingException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 数据类型转换工具类
 * 
 * @author ZHUFUKUN
 * 
 */
public class NetDataTypeTransform {
	final static Log log = LogFactory.getLog(NetDataTypeTransform.class);

	private NetDataTypeTransform() {

	}

	/**
	 * 将int转为低字节在前，高字节在后的byte数组
	 */
	public static byte[] intToByteArray(int n) {
		byte[] b = new byte[4];
		try {
			b[0] = (byte) (n & 0xff);
			b[1] = (byte) (n >> 8 & 0xff);
			b[2] = (byte) (n >> 16 & 0xff);
			b[3] = (byte) (n >> 24 & 0xff);
		} catch (Exception e) {
			log.error("方法异常", e);
		}
		return b;
	}

	/**
	 * byte数组转化为int 将低字节在前转为int，高字节在后的byte数组
	 */
	public static int byteArrayToInt(byte[] b) {
		// b[0]是高字节，现在在后
		return b[0] & 0xff | (b[1] & 0xff) << 8 | (b[2] & 0xff) << 16 | (b[3] & 0xff) << 24;
	}

	/**
	 * 将int转为低字节在后，高字节在前的byte数组,网络字节序
	 */
	public static byte[] int2ByteArrayHtoNs(int n) {
		byte[] b = new byte[4];
		try {
			b[3] = (byte) (n & 0xff);
			b[2] = (byte) (n >> 8 & 0xff);
			b[1] = (byte) (n >> 16 & 0xff);
			b[0] = (byte) (n >> 24 & 0xff);
		} catch (Exception e) {
			log.error("方法异常", e);
		}
		return b;
	}

	/**
	 * 字节类型转成int类型 将高字节在前转为int，低字节在后的byte数组
	 **/
	public static int byteArray2IntHtoNs(byte b[]) {
		return b[3] & 0xff | (b[2] & 0xff) << 8 | (b[1] & 0xff) << 16 | (b[0] & 0xff) << 24;
	}

	/**
	 * byte数组转化为short 将低字节在前转为int，高字节在后的byte数组
	 */
	public static short byteArrayToShort(byte b[]) {
		return (short) (b[0] & 0xff | (b[1] & 0xff) << 8);
	}

	/**
	 * byte数组转化为short 将高字节在前转为int，低字节在后的byte数组
	 */
	public static short byte2short(byte b[]) {
		return (short) (b[1] & 0xff | (b[0] & 0xff) << 8);
	}

	/**
	 * short2byte
	 */
	public static byte[] short2byte(int n) {
		byte b[] = new byte[2];
		b[0] = (byte) (n >> 8);
		b[1] = (byte) n;
		return b;
	}

	/**
	 * long到byte的转换
	 */
	public static byte[] long2byte(long n) {
		byte b[] = new byte[8];
		try {
			b[0] = (byte) (int) (n >> 56);
			b[1] = (byte) (int) (n >> 48);
			b[2] = (byte) (int) (n >> 40);
			b[3] = (byte) (int) (n >> 32);
			b[4] = (byte) (int) (n >> 24);
			b[5] = (byte) (int) (n >> 16);
			b[6] = (byte) (int) (n >> 8);
			b[7] = (byte) (int) n;
		} catch (Exception e) {
			log.error("方法异常", e);
		}
		return b;
	}

	/**
	 * 将byte数组转化成String
	 */
	public static String byteArraytoString(byte[] valArr, int maxLen) {
		String result = null;
		int index = 0;
		while (index < valArr.length && index < maxLen) {
			if (valArr[index] == 0) {
				break;
			}
			index++;
		}
		byte[] temp = new byte[index];
		System.arraycopy(valArr, 0, temp, 0, index);
		try {
			result = new String(temp, "GBK");
		} catch (UnsupportedEncodingException e) {
			log.error("方法异常", e);
		}
		return result;
	}

	private static String hexString = "0123456789ABCDEF";

	/**
	 * 将String转化成byte数组
	 */
	public static byte[] stringToByteArray(String str) {
		byte[] temp = null;
		try {
			temp = str.getBytes("GBK");
			StringBuilder sb = new StringBuilder(temp.length * 2);
			// 将字节数组中每个字节拆解成2位16进制整数
			for (int i = 0; i < temp.length; i++) {
				sb.append(hexString.charAt((temp[i] & 0xf0) >> 4));
				sb.append(hexString.charAt((temp[i] & 0x0f) >> 0));
			}
		} catch (UnsupportedEncodingException e) {
			log.error("方法异常", e);
		}
		return temp;
	}

	/**
	 * 字节转换为浮点
	 * 
	 * @param b
	 *            字节（至少4个字节）
	 * @param index
	 *            开始位置
	 * @return
	 */
	public static float byte2float(byte[] b, int index) {
		int l;
		l = b[index + 0];
		l &= 0xff;
		l |= ((long) b[index + 1] << 8);
		l &= 0xffff;
		l |= ((long) b[index + 2] << 16);
		l &= 0xffffff;
		l |= ((long) b[index + 3] << 24);
		return Float.intBitsToFloat(l);
	}

	/**
	 * 浮点转换为字节
	 * 
	 * @param f
	 * @return
	 */
	public static byte[] float2byte(float f) {

		// 把float转换为byte[]
		int fbit = Float.floatToIntBits(f);

		byte[] b = new byte[4];
		for (int i = 0; i < 4; i++) {
			b[i] = (byte) (fbit >> (24 - i * 8));
		}

		// 翻转数组
		int len = b.length;
		// 建立一个与源数组元素类型相同的数组
		byte[] dest = new byte[len];
		// 为了防止修改源数组，将源数组拷贝一份副本
		System.arraycopy(b, 0, dest, 0, len);
		byte temp;
		// 将顺位第i个与倒数第i个交换
		for (int i = 0; i < len / 2; ++i) {
			temp = dest[i];
			dest[i] = dest[len - i - 1];
			dest[len - i - 1] = temp;
		}
		return dest;
	}

	/**
	 * 浮点到字节转换
	 **/
	public static byte[] doubleToByteArray(double d) {
		byte[] b = new byte[8];
		long l = Double.doubleToLongBits(d);
		for (int i = 0; i < 8; i++) {
			// b[i] = new Long(l).byteValue();
			b[i] = Long.valueOf(l).byteValue();
			l = l >> 8;
		}
		return b;
	}

	/**
	 * 字节到浮点转换
	 * 
	 */
	public static double byteArrayToDouble(byte[] b) {
		long l;
		l = b[0];
		l &= 0xff;
		l |= ((long) b[1] << 8);
		l &= 0xffff;
		l |= ((long) b[2] << 16);
		l &= 0xffffff;
		l |= ((long) b[3] << 24);
		l &= 0xffffffffL;
		l |= ((long) b[4] << 32);
		l &= 0xffffffffffL;
		l |= ((long) b[5] << 40);
		l &= 0xffffffffffffL;
		l |= ((long) b[6] << 48);
		l &= 0xffffffffffffffL;// ///
		l |= ((long) b[7] << 56);
		return Double.longBitsToDouble(l);
	}

}
