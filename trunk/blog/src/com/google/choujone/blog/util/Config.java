package com.google.choujone.blog.util;

import java.util.ArrayList;
import java.util.List;

public class Config {
	public static String style_url = "/css/style.css";
	public static List<String> style_urls = new ArrayList<String>();
	static{
		style_urls.add("/css/style.css");
		style_urls.add("/css/style_guitar.css");
	}
}
