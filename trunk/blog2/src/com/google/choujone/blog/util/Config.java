package com.google.choujone.blog.util;

import java.util.ArrayList;
import java.util.List;

import com.google.choujone.blog.dao.UserDao;
import com.google.choujone.blog.entity.Menu;
import com.google.choujone.blog.entity.User;

public class Config {
	public static String style_url = "/css/style.css";
	public static List<String> style_urls = new ArrayList<String>();
	/**
	 * 网站用户信息
	 * */
	public static User blog_user = null;
	public static String web_url = null;

	public static String getWebUrl() {
		String url = blog_user.getUrl();
		if (url.indexOf("http://") == -1) {
			url = "http://" + url;
		}
		if (url.substring(url.length()-1) != "/") {
			url+="/";
		}
		return url;
	}

	public static List<Menu> menus = new ArrayList<Menu>();
	static {
		style_urls.add("/css/style.css");
		style_urls.add("/css/style_guitar.css");
		style_urls.add("/css/718375968.css");
		style_urls.add("/css/547847084.css");
		style_urls.add("/css/584063249.css");
		style_urls.add("/css/517370554.css");
		UserDao ud = new UserDao();
		blog_user = ud.getUserDetail();
		// System.out.println("加载博客信息成功");
		menus = Tools.split(blog_user.getMenu(), ";", ",");
		// System.out.println("加载博客导航成功");
	}
}
