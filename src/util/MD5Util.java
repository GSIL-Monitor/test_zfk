package util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 * @author ZHUFUKUN
 * 
 */
public class MD5Util
{
    // private static final Logger log=Logger.getLogger(MD5Util.class);
    private static final Log log = LogFactory.getLog(MD5Util.class);
    /**
     * 123456加密后是：123456:E10ADC3949BA59ABBE56E057F20F883E
     */

    /** * 16进制字符集 */
    private static final char HEX_DIGITS[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D',
            'E', 'F' };

    /** * 指定算法为MD5的MessageDigest */
    private static MessageDigest messageDigest = null;

    /** * 初始化messageDigest的加密算法为MD5 */
    static
    {
        try
        {
            messageDigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e)
        {
            log.error("error.", e);
        }
    }

    /**
     * * 获取文件的MD5值
     * 
     * @param file
     *            目标文件
     * @return MD5字符串
     */
    public static String getFileMD5String(String path)
    {
        File file = new File(path);
        String ret = "";
        FileInputStream in = null;
        FileChannel ch = null;
        try
        {
            in = new FileInputStream(file);
            ch = in.getChannel();
            ByteBuffer byteBuffer = ch.map(FileChannel.MapMode.READ_ONLY, 0, file.length());

            messageDigest.update(byteBuffer);
            ret = bytesToHex(messageDigest.digest());
        } catch (IOException e)
        {
            log.error("error.", e);

        } finally
        {
            if (in != null)
            {
                try
                {
                    in.close();
                } catch (IOException e)
                {
                    log.error("error.", e);
                }
            }
            if (ch != null)
            {
                try
                {
                    ch.close();
                } catch (IOException e)
                {
                    log.error("error.", e);
                }
            }
        }
        return ret;
    }

    /**
     * 获取文件的MD5值
     * 
     * @param fis
     *            文件流
     * @return
     * @throws IOException
     */
    public static String getFileMD5String(InputStream fis) throws IOException
    {
        byte[] buffer = new byte[1024];
        int numRead = 0;
        fis.mark(0);
        while ((numRead = fis.read(buffer)) > 0)
        {
            messageDigest.update(buffer, 0, numRead);
        }
        fis.reset();
        return bytesToHex(messageDigest.digest());
    }

    /**
     * * MD5加密字符串
     * 
     * @param str
     *            目标字符串
     * @return MD5加密后的字符串
     */

    public static String getMD5String(String str)
    {

        return getMD5String(str.getBytes());
    }

    /**
     * * MD5加密以byte数组表示的字符串
     * 
     * @param bytes
     *            目标byte数组
     * @return MD5加密后的字符串
     */

    public static String getMD5String(byte[] bytes)
    {
        messageDigest.update(bytes);
        return bytesToHex(messageDigest.digest());
    }

    /**
     * * 校验密码与其MD5是否一致
     * 
     * @param pwd
     *            密码字符串
     * @param md5
     *            基准MD5值
     * @return 检验结果
     */
    public static boolean checkPassword(String pwd, String md5)
    {
        return getMD5String(pwd).equalsIgnoreCase(md5);
    }

    /**
     * * 校验密码与其MD5是否一致
     * 
     * @param pwd
     *            以字符数组表示的密码
     * @param md5
     *            基准MD5值
     * @return 检验结果
     */
    public static boolean checkPassword(char[] pwd, String md5)
    {
        return checkPassword(new String(pwd), md5);

    }

    /**
     * * 将字节数组转换成16进制字符串
     * 
     * @param bytes
     *            目标字节数组
     * @return 转换结果
     */
    public static String bytesToHex(byte bytes[])
    {
        return bytesToHex(bytes, 0, bytes.length);

    }

    /**
     * * 将字节数组中指定区间的子数组转换成16进制字符串
     * 
     * @param bytes
     *            目标字节数组
     * @param start
     *            起始位置（包括该位置）
     * @param end
     *            结束位置（不包括该位置）
     * @return 转换结果
     */
    public static String bytesToHex(byte bytes[], int start, int end)
    {
        StringBuilder sb = new StringBuilder();
        for (int i = start; i < start + end; i++)
        {
            sb.append(byteToHex(bytes[i]));
        }
        return sb.toString();

    }

    /**
     * * 将单个字节码转换成16进制字符串
     * 
     * @param bt
     *            目标字节
     * @return 转换结果
     */
    public static String byteToHex(byte bt)
    {
        return HEX_DIGITS[(bt & 0xf0) >> 4] + "" + HEX_DIGITS[bt & 0xf];

    }

    // 可逆的加密算法
    public static String getEncryption(String inStr)
    {

        char[] a = inStr.toCharArray();
        for (int i = 0; i < a.length; i++)
        {
            a[i] = (char) (a[i] ^ 't');
        }
        String s = new String(a);
        return s;

    }

    // 可逆的解密算法
    public static String getDeciphering(String inStr)
    {

        char[] a = inStr.toCharArray();
        for (int i = 0; i < a.length; i++)
        {
            a[i] = (char) (a[i] ^ 't');
        }
        String k = new String(a);
        return k;

    }

    public static String getStreamHash(InputStream stream)
    {
        String hash = null;
        byte[] buffer = new byte[1024];
        BufferedInputStream in = null;
        try
        {
            in = new BufferedInputStream(stream);
            int numRead;

            while ((numRead = in.read(buffer, 0, buffer.length)) > 0)
            {
                messageDigest.update(buffer, 0, numRead);
            }
            in.close();
            hash = toHexString(messageDigest.digest());
        } catch (Exception e)
        {
            log.error("error.", e);
            if (in != null)
                try
                {
                    in.close();
                } catch (Exception ex)
                {
                    log.error("error.", ex);
                }
        }
        return hash;
    }

    private static String toHexString(byte[] b)
    {
        StringBuilder sb = new StringBuilder(b.length * 2);
        for (int i = 0; i < b.length; i++)
        {
            sb.append(hexChar[(b[i] & 0xf0) >>> 4]);
            sb.append(hexChar[b[i] & 0x0f]);
        }
        return sb.toString();
    }

    private static char[] hexChar = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

}
