package com.google.choujone.blog.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * choujone'blog<br>
 * 功能描述：采集模块，最好弄个定时采集
 * 2011-12-7
 */
public class Spider {
	public static String getUrl() {
		String line = "";
		StringBuffer sb=new StringBuffer();
		try {
			URL url = new URL("http://www.williamlong.info/archives/2904.html");
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					url.openStream()));
			while ((line = reader.readLine()) != null) {
				sb.append(line+"\n");
			}
			reader.close();
			System.out.println(sb.toString());
		} catch (MalformedURLException e) {
			// ...
		} catch (IOException e) {
			// ...
		}
		return line;
	}
}
