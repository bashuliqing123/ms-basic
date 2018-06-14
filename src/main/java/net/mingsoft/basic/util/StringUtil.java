package net.mingsoft.basic.util;

import java.io.UnsupportedEncodingException;
import java.util.Random;

import com.alibaba.druid.util.StringUtils;

public class StringUtil {
	/**
	 * 验证长度
	 * 
	 * @param str 需验证的字符串
	 * @param minLength 字符串的最小长度
	 * @param maxLength 字符串的最大长度
	 * @return 如果验证通过，则返回true，否则返回false
	 */
	public static boolean checkLength(String str, int minLength, int maxLength) {
		if (str != null) {
			int len = str.length();
			if (minLength == 0)
				return len <= maxLength;
			else if (maxLength == 0)
				return len >= minLength;
			else
				return (len >= minLength && len <= maxLength);
		}
		return false;
	}
	/**
	 * 判断数字数组
	 * 
	 * @param str String数组
	 * @return 如果是数字，则返回true，否则返回false
	 */
	public static boolean isIntegers(String str[]) {
		try {
			for (int i = 0; i < str.length; i++)
				Integer.parseInt(str[i]);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	/**
	 * 字符串转数字型数组
	 * 
	 * @param str String字符串
	 * @return 返回数字型数组
	 */
	public static Integer[] stringsToIntegers(String str[]) {
		Integer array[] = new Integer[str.length];
		for (int i = 0; i < array.length; i++)
			array[i] = Integer.parseInt(str[i]);
		return array;
	}
	/**
	 * 字符串转数字型数组
	 * 
	 * @param str String字符串
	 * @return 返回数字型数组
	 */
	public static int[] stringsToInts(String str[]) {
		int array[] = new int[str.length];
		for (int i = 0; i < array.length; i++)
			array[i] = Integer.parseInt(str[i]);
		return array;
	}
	/**
	 * 截取字符长度
	 * 
	 * @param str 需截取的字符串
	 * @param length 截取长度
	 * @return 返回截取后的字符串
	 */
	public static String subString(String str, int length) {
		if (StringUtils.isEmpty(str))
			return "";
		if (str.getBytes().length <= length)
			return str;
		char ch[] = null;
		if (str.length() >= length)
			ch = str.substring(0, length).toCharArray();
		else
			ch = str.toCharArray();
		int readLen = 0;
		StringBuffer sb = new StringBuffer("");
		for (int i = 0; i < ch.length; i++) {
			String c = String.valueOf(ch[i]);
			readLen += c.getBytes().length;
			if (readLen > length)
				return sb.toString();
			sb.append(c);
		}
		return sb.toString();
	}

	/**
	 * 程序内部字符串转码，将ISO-8859-1转换成utf-8
	 * 
	 * @param str 需要转码的字符串
	 * @return 返回utf8编码字符串
	 */
	public static String isoToUTF8(String str) {
		if (StringUtils.isEmpty(str))
			return "";
		try {
			return new String(str.getBytes("ISO-8859-1"), "UTF-8");
		} catch (UnsupportedEncodingException e) {
		}
		return "";
	}
	/**
	 * 将String型转换成Int型并判断String是否是NULL
	 * 
	 * @param str 字符串
	 * @return 返回数字
	 */
	public static int string2Int(String str) {
		int valueInt = 0;
		if (!StringUtils.isEmpty(str)) {
			valueInt = Integer.parseInt(str);
		}
		return valueInt;
	}
	/**
	 * 字符串转double型数组
	 * 
	 * @param str String字符串
	 * @return 返回数字型数组
	 */
	public static double[] stringsToDoubles(String str[]) {
		double array[] = new double[str.length];
		for (int i = 0; i < array.length; i++)
			array[i] = Double.parseDouble(str[i]);
		return array;
	}
	/**
	 * 判断数字数组
	 * 
	 * @param str String数组
	 * @return 如果是数组，则返回true，否则返回false
	 */
	public static boolean isDoubles(String str[]) {
		try {
			for (int i = 0; i < str.length; i++)
				Double.parseDouble(str[i]);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	/**
	 * 获取时间戳
	 * 
	 * @return 返回获取当前系统时间字符串
	 */
	public static String getDateSimpleStr() {
		return String.valueOf(System.currentTimeMillis());
	}
	/**
	 * 生成随机数
	 * 
	 * @param len 随机数长度
	 * @return 返回随机数
	 */
	public static String randomNumber(int len) {
		StringBuffer sb = new StringBuffer();
		Random random = new Random();
		for (int i = 0; i < len; i++) {
			sb.append(Math.abs(random.nextInt()) % 10);
		}
		return sb.toString();
	}
	/**
	 * 变量形态转换 int型转为String型
	 * 
	 * @param comment 整型数字
	 * @return 返回字符串
	 */
	public static String int2String(int comment) {
		String srt = "";
		srt = Integer.toString(comment);
		return srt;
	}
	/**
	 * 除去字符窜中重复的字符
	 * 
	 * @param content
	 *            　原始内容
	 * @param target
	 *            　重复内容
	 * @return　返回除去后的字符串
	 */
	public static String removeRepeatStr(String content, String target) {
		// int index = content.indexOf(target);
		// content = checkRepeatStr( content,target,index);
		StringBuffer sb = new StringBuffer(content);
		for (int i = 0; i < sb.length()-1; i++) {

			if (sb.substring(i, i + target.length()).equals(target) && sb.substring(i, i + target.length()).equals(sb.substring(i + 1, i + target.length() + 1))) {
				sb.delete(i, i + target.length());
				if (i + target.length() + 1 > sb.length()) {
					break;
				} else {
					i--;
				}
			}

		}
		return sb.toString();
	}


}
