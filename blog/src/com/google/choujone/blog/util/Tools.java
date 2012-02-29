package com.google.choujone.blog.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.Blob;
import com.google.appengine.api.datastore.Text;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.choujone.blog.entity.BlogType;
import com.google.choujone.blog.entity.Menu;
import com.google.choujone.blog.entity.User;

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
		return changeTime(date, "yyyy年MM月dd日 HH:mm:ss");
	}

	public static String changeTime(Date date, String format) {
		SimpleDateFormat simpleformat = new SimpleDateFormat(format);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.HOUR_OF_DAY, 8);
		return simpleformat.format(calendar.getTime());
	}

	public static Date changeTime(String date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
		Date d = new Date();
		try {
			d = format.parse(date);
		} catch (ParseException e) {
		}
		return d;
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

	/**
	 * 把map数组转换成下拉列表
	 * 
	 * @param blogTypeList
	 *            文章类型所有列表
	 * @param id
	 *            默认需要选中的编号
	 * @return
	 */
	public static String blogTypeList2Str(List<BlogType> blogTypeList, Long id) {
		StringBuffer sb = new StringBuffer();
		for (BlogType blogType : blogTypeList) {
			if (blogType.getParentId() == null || blogType.getParentId() == 0) {
				String selected = "";
				if (id != null && blogType.getId().equals(id)) {
					selected = "selected";
				}
				sb.append("<option value=\"" + blogType.getId() + "\" "
						+ selected + ">" + blogType.getName() + "</option>");
				for (BlogType bt_c : blogTypeList) {
					// System.out.println(bt_c.getParentId()+"  "+
					// blogType.getId());
					if (bt_c.getParentId() != null
							&& bt_c.getParentId().equals(blogType.getId())) {
						selected = "";
						if (id != null && bt_c.getId().equals(id)) {
							selected = "selected";
						}
						sb.append("<option value=\"" + bt_c.getId() + "\" "
								+ selected + "> ├" + bt_c.getName()
								+ "</option>");
					}
				}
			}
		}
		return sb.toString();
	}

	/**
	 * @param blogTypeList
	 * @return
	 */
	public static String blogTypeList2Str(List<BlogType> blogTypeList) {
		StringBuffer sb = new StringBuffer();
		for (BlogType blogType : blogTypeList) {
			// <li title="<%=bt.getInfo() %>"><a
			// href="/?tid=<%=bt.getId() %>"><%=bt.getName()%>(<%=bd.getCount(bt.getId())
			// %>)</a></li>
			if (blogType.getParentId() == null || blogType.getParentId() == 0) {
				sb.append("<li ><a href='javascript:void(0)'>"
						+ blogType.getName() + "</a>");
				StringBuffer sb_sub = new StringBuffer();
				for (BlogType bt_c : blogTypeList) {
					// System.out.println(bt_c.getParentId()+"  "+
					// blogType.getId());
					if (bt_c.getParentId() != null
							&& bt_c.getParentId().equals(blogType.getId())) {
						sb_sub.append("<li ><a href=/?tid=" + bt_c.getId()
								+ ">" + bt_c.getName() + "</a></li>");
					}
				}
				if (sb_sub != null && !sb_sub.toString().equals("")) {
					sb.append("<ul>");
					sb.append(sb_sub);
					sb.append("</ul>");
				}
				sb.append("</li>");
			}
		}
		return sb.toString();
	}

	public static boolean isLogin(ServletRequest request) {
		boolean isLogin = false;
		try {
			// UserService userService = UserServiceFactory.getUserService();
			// if (userService != null && userService.isUserLoggedIn()
			// && userService.isUserAdmin()) {
			// isLogin = true;
			// } else {
			// isLogin = false;
			// }

			HttpServletRequest req = (HttpServletRequest) request;
			User user = (User) req.getSession().getAttribute("login_user");
			if (user != null) {// 判断是不是网站用户登陆
				// UserService us = UserServiceFactory.getUserService();
				// if (!us.isUserLoggedIn() || !us.isUserAdmin()) {
				// isLogin = false;
				// } else {
				// isLogin = true;
				// }
				isLogin = true;
			}
		} catch (Exception e) {
		}
		return isLogin;
	}

	/**
	 * 获取客户端IP地址
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

	/**
	 * 根据IP地址获取地理位置，如：北京市
	 */
	public static String getAddressByIP(String ip) {
		String addressStr = "";
		try {
			URL url = new URL("http://www.ip138.com/ips.asp?ip=" + ip);
			URLConnection conn = url.openConnection();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					conn.getInputStream(), "GBK"));
			String line = null;
			StringBuffer result = new StringBuffer();
			while ((line = reader.readLine()) != null) {
				result.append(line);
			}
			reader.close();
			addressStr = result.toString();
			addressStr = addressStr.substring(addressStr.indexOf("主数据：") + 4,
					addressStr.indexOf("</li><li>参考数据"));
			char[] address = addressStr.toCharArray();
			for (int i = 0; i < address.length; i++) {
				char c = address[i];
				if (i != 0 && !Character.isLetter(c)) { // 判断是否汉字，不含中午特殊符号
					addressStr = addressStr.substring(0, i);
					break;
				}
			}
		} catch (StringIndexOutOfBoundsException indexOut) {
			System.out.println("下标越界：" + ip);
			addressStr = ip; // 获取失败，返回IP
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return addressStr;
	}

	/**
	 * 字符串转为数组
	 * 
	 * @param menus
	 *            源字符串
	 * @param str1
	 *            分割字符串1
	 * @param str2
	 *            分割字符串2
	 * @return
	 */
	public static List<Menu> split(Text str, String str1, String str2) {
		List<Menu> list = new ArrayList<Menu>();
		Menu menu = new Menu();
		if (str != null) {
			String menus = str.getValue();
			if (Tools.isNotNull(menus)) {
				String[] strs = menus.split(str1);
				for (String string : strs) {
					if (Tools.isNotNull(string)) {
						menu = new Menu();
						String[] str2s = string.split(str2);
						menu.setTitle(str2s[0]);
						menu.setUrl(str2s[1]);
						list.add(menu);
					}
				}
			}
		}
		return list;
	}

	/**
	 * 判断字符串是否为空
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNotNull(Object str) {
		return str != null && !"".equals(str.toString().trim());
	}

	public static boolean isNotNull(Map map) {
		return map != null && !map.isEmpty();
	}

	public static String escape(String s) {
		if (!isNotNull(s)) {
			return "";
		}
		StringBuffer stringbuffer = new StringBuffer();
		int i = 0;
		for (int j = s.length(); i < j; i++) {
			char c = s.charAt(i);
			switch (c) {
			case 38: // '&'
				stringbuffer.append("&amp;");
				break;

			case 60: // '<'
				stringbuffer.append("&lt;");
				break;

			case 62: // '>'
				stringbuffer.append("&gt;");
				break;

			case 34: // '"'
				stringbuffer.append("&quot;");
				break;

			default:
				stringbuffer.append(c);
				break;
			}
		}
		return stringbuffer.toString();
	}

	public static Map<Object, Object> str2map(String input) {
		String regex = ",";
		String regex2 = "=";
		Map<Object, Object> map = new HashMap<Object, Object>();
		if (isNotNull(input)) {
			String[] strs = input.split(regex);
			for (int i = 0; i < strs.length; i++) {
				if (isNotNull(strs[i])) {
					String[] strs2 = strs[i].split(regex2);
					map.put(strs2[0], strs2[1]);
				}
			}
		}
		return map;
	}

	public static String map2str(Map<Object, Object> map) {
		String regex = ",";
		String regex2 = "=";
		StringBuffer sb = new StringBuffer();
		if (isNotNull(map)) {
			for (Object obj : map.keySet()) {
				if (isNotNull(map.get(obj))) {
					sb.append(obj + regex2 + map.get(obj));
					sb.append(regex);
				}
			}
		}
		return sb.substring(0, sb.lastIndexOf(regex));
	}

	public static String map2str2(Map<Long, Integer> map) {
		String regex = ",";
		String regex2 = "=";
		StringBuffer sb = new StringBuffer();
		if (isNotNull(map)) {
			for (Object obj : map.keySet()) {
				if (isNotNull(map.get(obj))) {
					sb.append(obj + regex2 + map.get(obj));
					sb.append(regex);
				}
			}
		}
		return sb.substring(0, sb.lastIndexOf(regex));
	}

	public static Map<Long, Integer> map2map(Map<Object, Object> obj) {
		Map<Long, Integer> map = new HashMap<Long, Integer>();
		try {
			for (Object key : obj.keySet()) {
				map.put(Long.valueOf(key.toString()), Integer.parseInt(obj.get(
						key).toString()));
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return map;
	}

	public static Map<Object, Object> map2map2(Map<Long, Integer> obj) {
		Map<Object, Object> map = new HashMap<Object, Object>();
		try {
			for (Object key : obj.keySet()) {
				map.put(key.toString(), obj.get(key).toString());
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return map;
	}

	/**
	 * 修改統計map
	 * 
	 * @param map
	 * @param tid
	 * @return
	 */
	public static Map<Long, Integer> modifyMayOfStatis(Map<Long, Integer> map,
			Long tid) {
		try {
			for (Long key : map.keySet()) {
				if (key.equals(tid)) {
					if (isNotNull(map.get(key))) {
						map.put(key,
								map.get(key) != null && map.get(key) > 0 ? map
										.get(key) - 1 : 0);
					}
				}
			}
		} catch (Exception e) {
		}
		return map;
	}

	public static void main(String[] args) {
		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("1328885416494", "4");
		map.put("1328885416433", "67");
		map.put("1328885413494", "54");
		map.put("1328885416294", "55");
		String str = Tools.map2str(map);
		System.out.println("str:" + str);
		map = Tools.str2map(str);
		for (Object obj : map.keySet()) {
			System.out.println("key:" + obj + "  value:" + map.get(obj));
		}
	}
}