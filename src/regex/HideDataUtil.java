package regex;

import org.apache.commons.lang.StringUtils;

/**
 * 
 * <p>Description: </p>
 * @date 2018年10月26日
 * @author 朱富昆
 * @version 1.0.0
 * <p>Company:workway</p>
 * <p>Copyright:Copyright(c)2017</p>
 */
public class HideDataUtil {

	/**
	 * 
	 * 方法用途: 隐藏文本中间的字符串（使用*号）
	 * @param text
	 * @param beforeLength 前面保留长度
	 * @param afterLength  后面保留长度
	 * @return
	 */
	public static String hideText(String text, int beforeLength, int afterLength) {
		if (StringUtils.isBlank(text)) {
			return text;
		}

		int length = text.length();
		
		while(length <= beforeLength + afterLength){
			if(beforeLength != 0){
				beforeLength --;
			}else{
				afterLength--;
			}
		}
		
		// 替换字符串，当前使用“*”
		String replaceSymbol = "*";
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			if (i < beforeLength || i >= (length - afterLength)) {
				sb.append(text.charAt(i));
			} else {
				sb.append(replaceSymbol);
			}
		}

		return sb.toString();
	}
	
	/**
	 * 
	 * 方法用途:  隐藏姓名中间的字符串（使用*号）
	 * @param name
	 * @return
	 */
	public static String hideName(String name) {
		if (StringUtils.isBlank(name)) {
			return name;
		}
		int length = name.length();
		int beforeLength = 1;
		int afterLength = 1;
		if(length == 2){
			afterLength = 0;
		}
		
		// 替换字符串，当前使用“*”
		String replaceSymbol = "*";
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			if (i < beforeLength || i >= (length - afterLength)) {
				sb.append(name.charAt(i));
			} else {
				sb.append(replaceSymbol);
			}
		}

		return sb.toString();
	}
	
	/**
	 * 
	 * 方法描述 隐藏银行卡号中间的字符串（使用*号）
	 * @param cardNo
	 * @return
	 * 
	 */
	public static String hideCardNo(String cardNo) {
		if (StringUtils.isBlank(cardNo)) {
			return cardNo;
		}
		int length = cardNo.length();
		int beforeLength = 6;
		int afterLength = 4;
		// 替换字符串，当前使用“*”
		String replaceSymbol = "*";
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			if (i < beforeLength || i >= (length - afterLength)) {
				sb.append(cardNo.charAt(i));
			} else {
				sb.append(replaceSymbol);
			}
		}

		return sb.toString();
	}

	/**
	 * 
	 * 方法描述 隐藏手机号中间位置字符
	 * @param phoneNo
	 * @return
	 */
	public static String hidePhoneNo(String phoneNo) {
		if (StringUtils.isBlank(phoneNo)) {
			return phoneNo;
		}

		int length = phoneNo.length();
		int beforeLength = 3;
		int afterLength = 4;
		// 替换字符串，当前使用“*”
		String replaceSymbol = "*";
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			if (i < beforeLength || i >= (length - afterLength)) {
				sb.append(phoneNo.charAt(i));
			} else {
				sb.append(replaceSymbol);
			}
		}

		return sb.toString();
	}
}
