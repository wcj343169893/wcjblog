package com.choujone.eclipse.ftp.l10n;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * 国际化插件
 * 
 * @author choujone
 * 
 */
public class Language {

	private static ResourceBundle res = null;
	private static Locale locale = null;
	static {
		try {
			locale = Locale.getDefault();
			res = ResourceBundle.getBundle("Plugin", locale);
		} catch (Exception e) {
			locale=new Locale("zh","CN");
			res = ResourceBundle.getBundle("Plugin", locale);
		}
	}

	public static void main(String[] args) {
		System.out.println(Language.names("name"));
	}

	/**
	 * 国际化
	 * 
	 * @param key
	 * @return
	 */
	public static String names(String key) {
		String name = "";
		try {
			name = res.getString(key);
		} catch (Exception e) {
		}
		//如果没有找到对应的内容，则默认寻找中文内容
		if (null == name || name.trim().equals("")) {
			name = "";
		}
		return name;
	}
}
