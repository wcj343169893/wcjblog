package com.google.choujone.blog.util;

import java.util.ArrayList;
import java.util.List;

import com.google.choujone.blog.dao.UserDao;
import com.google.choujone.blog.entity.User;

public class Config {
	public static String style_url = "/css/style.css";
	public static List<String> style_urls = new ArrayList<String>();
	/**
	 * 网站用户信息
	 * */
	public static User blog_user = null;
	static {
		style_urls.add("/css/style.css");
		style_urls.add("/css/style_guitar.css");
		style_urls.add("/css/718375968.css");
		style_urls.add("/css/547847084.css");
		style_urls.add("/css/584063249.css");
		style_urls.add("/css/517370554.css");
		UserDao ud = new UserDao();
		blog_user = ud.getUserDetail();
	}
}
