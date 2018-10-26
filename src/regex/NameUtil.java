package regex;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NameUtil {

    private static String[] chinaFirstName = FileUtil.readTxtFileFromSRC("chinaFirstName.txt").split("\\s+");
    private static String secondCodesVector = FileUtil.readTxtFileFromSRC("chinaSecondName.txt");


    /**
     * Hash法脱敏中文全名
     *
     * @param name 中文全名
     * @return 返回完整中文名
     */
    public static String makeName(String name) {
        if (null == name || name.equals("")) return "";
        String result = "";
        long hashCode1 = elfHash(name);
        long hashCode2 = BitInversion(hashCode1);
        result += chinaFirstName[(int) (hashCode1 % chinaFirstName.length)];
        result += secondCodesVector.charAt((int) (hashCode2 % secondCodesVector.length()));
        if ((Math.toIntExact(hashCode1) & 1) == 0) {
            long hashCode3 = BitReverse(hashCode1);
            result += secondCodesVector.charAt((int) (hashCode3 % secondCodesVector.length()));
        }
        return result;
    }

    private static long elfHash(String strUri) {
        if (strUri == null) {
            strUri = "";
        }
        long hash = 0;
        long x = 0;
        for (int i = 0; i < strUri.length(); i++) {
            hash = (hash << 4) + strUri.charAt(i);
            if ((x = hash & 0xF0000000L) != 0) {
                hash ^= (x >> 24);
                hash &= ~x;
            }
        }
        return (hash & 0x7FFFFFFF);
    }

    /**
     * 除符号位其他位按位取反
     *
     * @param hash hash值
     * @return 经按位取反后的hash值
     */
    private static long BitInversion(long hash) {
        return (hash ^ 0x7FFFFFFF);
    }

    /**
     * 整型的前16位bit与后16位bit互换，符号位取正
     *
     * @param hash hash值
     * @return 处理后的hash值
     */
    public static long BitReverse(long hash) {
        long hash1 = 0;
        long hash2 = 0;
        hash1 = hash << 16;
        hash2 = hash >> 16;
        hash = hash1 | hash2;
        return (hash & 0x7FFFFFFF);
    }

    /**
     * 过滤掉字符串中特殊字符
     *
     * @param str
     * @return
     */
    public static String filter(String str) {
        String regEx = "[`~!@#$%^&*()\\-+={}':;,\\[\\].<>/?￥…（）_|【】‘；：”“’。，、？\\s]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.replaceAll("").trim();
    }
}
