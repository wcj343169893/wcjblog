package com.google.choujone.blog.util;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.Blob;

public class Tools {
	/**
	 * @功能 转换字符串值为int型值
	 * @参数 value为要转换的字符串
	 * @返回值 int型值
	 */
	public static String changeHTML(String value) {
		/*
		 * value=value.replace("'", "''"); value=value.replace("&","&amp;");
		 * value=value.replace(" ","&nbsp;"); value=value.replace("<","&lt;");
		 * value=value.replace(">","&gt;"); value=value.replace("\r\n","<br>");
		 */
		return value;
	}

	public static String FilterHTML(String value) {
		value = value.replace("'", "''");
		value = value.replace("&", "&amp;");
		value = value.replace(" ", "&nbsp;");
		value = value.replace("<", "&lt;");
		value = value.replace(">", "&gt;");
		value = value.replace("\r\n", "<br>");
		return value;
	}

	/**
	 * @功能 转换字符串值为int型值
	 * @参数 value为要转换的字符串
	 * @返回值 int型值
	 */
	public static int strToint(String value) {
		int i = -1;
		if (value == null || value.equals(""))
			return i;
		try {
			i = Integer.parseInt(value);
		} catch (NumberFormatException e) {
			i = -1;
			e.printStackTrace();
		}
		return i;
	}

	/**
	 * string转化为long
	 * 
	 * @param value
	 * @return
	 */
	public static Long strTolong(String value) {
		Long i = -1L;
		if (value == null || value.equals(""))
			return i;
		try {
			i = Long.valueOf(value.trim());
		} catch (NumberFormatException e) {
			i = -1L;
		}
		return i;
	}

	/**
	 * @功能 解决通过提交表单产生的中文乱码
	 * @参数 value为要转换的字符串
	 * @返回值 String型值
	 */
	public static String toChinese(String value) {
		return value;
		/*
		 * if (value == null) return ""; try { value = new
		 * String(value.getBytes("ISO-8859-1"), "UTF-8"); return value; } catch
		 * (Exception e) { return ""; }
		 */
	}

	/**
	 * @功能 将Date型日期转换成指定格式的字符串形式，如“yyyy年MM月dd日 HH:mm:ss”
	 * @参数 date为要被转换的Date型日期
	 * @返回值 String型值
	 */
	public static String changeTime(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
		return format.format(date);
	}

	public static void blob2Img(Blob blob, HttpServletResponse resp) {
		resp.setContentType("image/jpeg");
		try {
			resp.getOutputStream().write(blob.getBytes());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}