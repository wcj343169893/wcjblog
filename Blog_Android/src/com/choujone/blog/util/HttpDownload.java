package com.choujone.blog.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class HttpDownload {
	/**
	 * 根据URL下载文件,前提是这个文件当中的内容是文本,函数的返回值就是文本当中的内容 1.创建一个URL对象
	 * 2.通过URL对象,创建一个HttpURLConnection对象 3.得到InputStream 4.从InputStream当中读取数据
	 * 
	 * @param urlStr
	 * @return
	 */
	public static String downFile(String urlstr) {
		StringBuffer sb = new StringBuffer();
		try {
			URL url = new URL(urlstr);
			URLConnection connect = url.openConnection();
			String line = "";
			BufferedReader buffer = new BufferedReader(new InputStreamReader(
					connect.getInputStream()));
			while ((line = buffer.readLine()) != null) {
				sb.append(line);
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sb.toString();
	}

	/**
	 * 根据URL得到输入流
	 * 
	 * @param urlStr
	 * @return
	 */
	public static InputStream getInputStreamFromURL(String urlStr) {
		HttpURLConnection urlConn = null;
		InputStream inputStream = null;
		try {
			URL url = new URL(urlStr);
			urlConn = (HttpURLConnection) url.openConnection();
			inputStream = urlConn.getInputStream();

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return inputStream;
	}

	public static Map<String, String> json2Map(String json) {
		Map<String, String> map = new HashMap<String, String>();
		JsonParser jp = new JsonParser();
		JsonElement je = jp.parse(json);
		JsonObject jo = je.getAsJsonObject();
		Set<Map.Entry<String, JsonElement>> keys = jo.entrySet();
		for (Map.Entry<String, JsonElement> key : keys) {
//			System.out.println(key.getKey()+" = "+jo.get(key.getKey()).toString());
			map.put(key.getKey(), jo.get(key.getKey()).toString());
		}
		return map;
	}

	public static void main(String[] args) {
		String json = HttpDownload
				.downFile("http://www.choujone.com/blogType?opera=lists");
		HttpDownload.json2Map(json);
	}

}
