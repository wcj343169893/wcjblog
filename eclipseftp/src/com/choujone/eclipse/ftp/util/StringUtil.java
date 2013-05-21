package com.choujone.eclipse.ftp.util;

public class StringUtil {

	/**
	 * 检查字符串是否以斜线结尾
	 * 
	 * @param str
	 * @return true:存在;false:不存在
	 */
	public static boolean endsWithSlash(String str) {
		return str.endsWith("/") || str.endsWith("\\");
	}
}
