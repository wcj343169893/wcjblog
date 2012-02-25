package com.google.choujone.blog.util;

import java.util.ArrayList;
import java.util.List;

import com.google.choujone.blog.entity.Menu;

/**
 * choujone'blog<br>
 * 功能描述：配置 2012-2-13
 */
public class Config {
	private static String style_url = "/css/style.css";
	private static List<String> style_urls = new ArrayList<String>();
	/**
	 * 网站用户信息
	 * */
//	private static User blog_user = null;
	private static String web_url = null;
	private static boolean isClose = false;// 网站是否关闭

	/**
	 * 统计
	 */
	// public static Statistics statistics = null;

	/**
	 * 博客类型下的博客数量
	 */
	// public static Map<Long, Integer> blogType_blog_size_map = new
	// HashMap<Long, Integer>();
	/**
	 * 博客下面的回复数量
	 */
	// public static Map<Long, Integer> blog_reply_size = new HashMap<Long,
	// Integer>();

//	public static String getWebUrl() {
//		String url = blog_user.getUrl();
//		if (url.indexOf("http://") == -1) {
//			url = "http://" + url;
//		}
//		if (url.substring(url.length() - 1) != "/") {
//			url += "/";
//		}
//		return url;
//	}

	private static List<Menu> menus = new ArrayList<Menu>();
	static {
		style_urls.add("/css/style.css");
		style_urls.add("/css/style_guitar.css");
		style_urls.add("/css/718375968.css");
		style_urls.add("/css/547847084.css");
		style_urls.add("/css/584063249.css");
		style_urls.add("/css/517370554.css");
//		UserDao ud = new UserDao();
//		blog_user = ud.getUserDetail();
//		// System.out.println("加载博客信息成功");
//		menus = Tools.split(blog_user.getMenu(), ";", ",");
//		// System.out.println("加载博客导航成功");
//		// statistics = ud.getStatistics();
//		isClose = blog_user.getCloseweb() == null
//				|| blog_user.getCloseweb().equals(1);
	}

	public static String getStyle_url() {
		return style_url;
	}

	public static void setStyle_url(String styleUrl) {
		style_url = styleUrl;
	}

	public static List<String> getStyle_urls() {
		return style_urls;
	}

	public static void setStyle_urls(List<String> styleUrls) {
		style_urls = styleUrls;
	}

//	public static User getBlog_user() {
//		if (blog_user == null) {
//			UserDao ud = new UserDao();
//			blog_user = ud.getUserDetail();
//			menus = Tools.split(blog_user.getMenu(), ";", ",");
//		}
//		return blog_user;
//	}

//	public static void setBlog_user(User blogUser) {
//		blog_user = blogUser;
//	}

//	public static String getWeb_url() {
//		return web_url;
//	}

	public static void setWeb_url(String webUrl) {
		web_url = webUrl;
	}

	public static List<Menu> getMenus() {
		return menus;
	}

	public static void setMenus(List<Menu> menus) {
		Config.menus = menus;
	}

	public static boolean isClose() {
		return isClose;
	}

	public static void setClose(boolean isClose) {
		Config.isClose = isClose;
	}

}
