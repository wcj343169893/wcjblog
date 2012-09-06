package com.snssly.sms.commons;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.ResourceBundle;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.log4j.Logger;

public class Env {
	private static Logger logger = Logger.getLogger(Env.class);

	/**
	 * -- 配置文件
	 */
	public static String CONFIG = null;

	public static String PACKAGE = "com.snssly.sms.";

	/**
	 * -- 读取属性文件.静态方法
	 * 
	 * @param baseName
	 *            -- 属性文件名称
	 * @param key
	 *            -- 键，要读取的内容的键
	 * @return -- 返回根据键读取到的值
	 */
	public static String read(String baseName, String key) {
		if (baseName == null || baseName.equals(""))
			return null;
		ResourceBundle bundle = ResourceBundle.getBundle(baseName);
		String value = null;
		try {
			value = bundle.getString(key);
		} catch (Exception e) {
			value = null;
			logger.error("不能从属性文件[" + baseName + "]中获得值[" + key + "]");
		}
		return value;
	}

	/**
	 * -- 读取属性文件.静态方法（CONFIG）
	 * 
	 * @param key
	 *            -- 键，要读取的内容的键
	 * @return-- 返回根据键读取到的值
	 */
	public static String read(String key) {
		if (CONFIG != null) {
			return Env.read(CONFIG, key);
		}
		return null;
	}

	/**
	 * -- 类---反射
	 * 
	 * @param className
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Object forName(String className) {
		try {
			Class<Object> c = (Class<Object>) Class.forName(className);
			return c.newInstance();
		} catch (ClassNotFoundException e) {
			logger.error("Cannot find class:" + className);
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			logger.error("Cannot Instantiation class:" + className);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			logger.error("Illegal access in class:" + className);
		}
		return null;
	}

	/**
	 * -- 生成随机激活码(8位)
	 * 
	 * @return
	 */
	public static String activate() {
		char[] temp = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();
		StringBuffer str = new StringBuffer();
		Random rand = new Random(new Date().getTime());
		for (int i = 0; i < 8; i++) {
			int a = rand.nextInt(36);
			str.append(temp[a]);
			rand = new Random(new Date().getTime() + i);
		}
		return str.toString();
	}

	/**
	 * -- 采用JNDI方式获得数据库连接,默认数据库
	 * 
	 * @return DataSource
	 */
	public static DataSource createDataSource() {
		String jndi = Env.read(Env.read("DATABASE") + ".JNDI");
		return Env.createDataSource(jndi);
	}

	/**
	 * -- 采用JNDI方式获得数据库连接
	 * 
	 * @return DataSource
	 */
	public static DataSource createDataSource(String jndi) {
		DataSource ds = null;
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup(jndi);
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			logger.error("NamingException: " + jndi);
		}
		return ds;
	}

	/**
	 * -- 生成MD5密码
	 * 
	 * @param key
	 * @return
	 */
	public static String encode(String key) {
		String sr = null;
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update((key + "A*+C").getBytes());
			byte b[] = md.digest();
			int i;
			StringBuffer buf = new StringBuffer("");
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0)
					i += 256;
				if (i < 16)
					buf.append("0");
				buf.append(Integer.toHexString(i));
			}
			sr = buf.toString();
			sr = sr.substring(8, 16) + sr.substring(24, 32);
		} catch (NoSuchAlgorithmException e) {
			logger.error("Cannot get MD5 encode: " + key);
		}
		return sr;
	}

	public static String format(Date date, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		String s = null;
		try {
			s = sdf.format(date);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("Cannot format date to: " + format);
		}
		return s;
	}

	public static Date format(String source, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Date s = null;
		try {
			s = sdf.parse(source);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("Cannot format date to: " + format);
		}
		return s;
	}

	public static String format(Date date) {
		return Env.format(date, "yyyy-MM-dd HH:mm:ss.SSS");
	}

	public static Date format(String source) {
		return Env.format(source, "yyyy-MM-dd HH:mm:ss.SSS");
	}

	/**
	 * -- 格式化日期为:yyyy-mm-dd hh:MM
	 * 
	 * @param format
	 *            -- 参数要求格式{yyyy-mm-dd hh:MM:ss.0}
	 * @return
	 */
	public static String formatDate(String format) {
		String key = format;
		if (key != null
				&& !key.equals("")
				&& key
						.matches("^\\d{4}\\-\\d{2}\\-\\d{2}\\s\\d{2}:\\d{2}:\\d{2}(\\.\\d+)?$")) {
			key = key.substring(2, 16);
		}
		return key;
	}

	/**
	 * 转换HTML符号
	 * 
	 * @param paramMap
	 * @return
	 */
	public static String replaceHTML(String param) {
		if (param == null) {
			return "";
		}
		param = param.replaceAll("&", "&amp;");
		param = param.replaceAll("<", "&lt;");
		param = param.replaceAll(">", "&gt;");
		param = param.replaceAll("\n", "<br>");
		param = param.replaceAll("'", "`");
		return param;
	}

	/**
	 * 转成原来的字符
	 * 
	 * @param param
	 * @return
	 */
	public static String replaceHTMLRe(String param) {
		if (param == null) {
			return "";
		}
		param = param.replaceAll("&lt;", "<");
		param = param.replaceAll("&gt;", ">");
		param = param.replaceAll("<br>", "\n");
		param = param.replaceAll("&amp;", "&");
		param = param.replaceAll("`", "'");
		return param;
	}

	/**
	 * 获取html中纯文本
	 * 
	 * @param param
	 * @param length
	 *            获取到的总长度
	 * @return
	 */
	public static String replace(String param, Integer length) {
		if (param != null && !"".equals(param.trim())) {
			param = param.replaceAll("<.*?>", "");
			param = param.replaceAll("&nbsp;", "");
			param = param.replaceAll(" ", "");
			Integer le = param.length();
			if (length != null && length > 0 && le > 0) {
				param = param.substring(0, le > length ? length : le);
			}
		} else {
			param = "";
		}
		return param;
	}

	/**
	 * 获取html中纯文本
	 * 
	 * @param param
	 * @return
	 */
	public static String replace(String param) {
		param = param.replaceAll("<.*?>", "");
		param = param.replaceAll("&nbsp;", "");
		param = param.replaceAll(" ", "");
		return param;
	}

	/**
	 * -- 获取IP
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws ServletException
	 */
	public static String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	public static void main(String[] args) {
//		String str = replace(
//				"<p><span style=\"line-height:22px;font-family:Arial, 宋体;font-size:15px;\" class=\"Apple-style-span\">&nbsp;&nbsp;&nbsp; 重庆市人民小学，1945年创建于河北邯郸，原名晋冀鲁豫军区干部子弟校，随刘邓大军南下落户在重庆市上清寺。刘伯承元帅为学校命名并题写校训，邓小平同志的夫人卓琳女士任首任校长，贺龙元帅任首任董事长。学校现为重庆市教委直属小学、重庆市首批示范学校。学校渝中校园区占地46亩，教学班53个；学前幼儿部占地9亩，教学班12个；与融侨地产集团联合创办的人民融侨小学，占地50亩，教学班20个；由沙区政府举办，发挥区建市管的体制优势、为大学城社会经济高速发展服务的大学城人民小学，占地50亩，设有36个教学班。全校共有师生近4000人，是一所具有光荣革命传统、规模较大的历史名校。<br style=\"padding-bottom:0px;margin:0px;padding-left:0px;padding-right:0px;padding-top:0px;\" />",
//				10);
//		System.out.println(str);
		String str = encode("");
		System.out.println(str);
	}

	/**
	 * 判断日期
	 * 
	 * @param endTime
	 * @param date2
	 * @return
	 */
	public static boolean compareDate(Date endTime, Date date2) {
		if (endTime == null || date2 == null) {
			return false;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String met = sdf.format(endTime);
		String now = sdf.format(date2);
		return met.compareTo(now) < 0;
	}
}
