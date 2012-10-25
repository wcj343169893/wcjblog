package com.ebillion.mofing.seriesnumber.action;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

import com.sonalb.net.http.cookie.Client;
import com.sonalb.net.http.cookie.CookieJar;

public class testPost {

	/*
	 * public static void main(String[] args) throws
	 * UnsupportedEncodingException { long bc = System.currentTimeMillis(); for
	 * (int i = 0; i < 100; i++) { long b = System.currentTimeMillis(); String
	 * str = "<xml><test>aa 1_ 1 </test><name>姓名</name></xml>" + i 100; str =
	 * URLEncoder.encode(str, "utf-8"); testPost.sendRequest(str); long c =
	 * System.currentTimeMillis(); System.out.println("第------->>" + i + "此" +
	 * "耗时:" + (c - b)); } long cc = System.currentTimeMillis();
	 * System.out.println("耗时:" + (bc - cc));
	 * 
	 * }
	 */

	public static void main(String[] args) throws UnsupportedEncodingException {
		long bc = System.currentTimeMillis();
		StringBuffer sendString = new StringBuffer();
		String str = "ABAP ACG AIR AJAX Apple C C++ CLANNAD CSS Diablo2 Diablo3 Discuz! Fate/Hollow Ataraxia Fate/Stay Night Firefox Flash GFW GalGame Gmail Google Google Adsense Google Analytics Google App Engine Google Calendar Google Chrome Google Cloud SQL Google Reader Google Storage Google Talk HTML Java JavaScript KEY KID/5pb.Games Little Busters! Mac Mac OS X Mac开发 Objective-C PHP PHPWind Python RSS Ruby SAP SEO Shell StarCraft StarCraft2 TYPE-MOON UNIX WarCraft3 WordPress iOS开发 iPad iPhone jQuery 动漫 吉里吉里 性能 搞笑 收藏 智代After 游戏王 百度 设计模式 趣闻";
		String category = "ACG GalGame 动漫 汉化 Apple Mac OS X Mac iPhone Blizzard Diablo2 Diablo3 StarCraft StarCraft2 WarCraft3 Discuz! Google Gmail Google Adsense Google Analytics Google App Engine Google Apps Google Calendar Google Chrome Google Cloud SQL Google Reader Google Storage Google Talk WordPress 日记 游戏王 编程 ABAP C++ CSS Flash HTML JavaScript Mac开发 Objective-C PHP Python Ruby Shell Web iOS开发 吉里吉里 数据库 设计模式 网站建设 SEO 腾讯 QQ QQ中转站 QQ空间 资源 随笔 ";
		String[] arr = category.split(" ");
		int length = arr.length;
		 String url="http://www.keakon.net/search?keywords=";
//		String url = "http://www.keakon.net/category/";
		System.out.println("------------开始发送------------");
		for (int i = 0; i < 10; i++) {
			try {
				int val = (int) (Math.random() * length);
				sendString.append(arr[val]);
				testPost.sendRequest(url + sendString.toString());
				sendString = new StringBuffer();
			} catch (Exception e) {
				// TODO: handle exception
				sendString = new StringBuffer();
			}
		}
		// Math.round(a)
		System.out.println("------------发送结束------------");
		long cc = System.currentTimeMillis();
		System.out.println("耗时:" + (bc - cc));

	}

	public static String sendRequest(String strXml) {
		StringBuffer buffer = null;
		HttpURLConnection c = null;
		try {
			System.out.println("go!");
			// URL url = new
			// URL("http://localhost:8080/ebillion/getPhpServlet");
			// URL url = new
			// URL("http://www.10000-e.com/mreader/getPhpServlet");
			URL url = new URL(strXml);
			// Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(
			// "api.10000-e.com", 80));
			// c = (HttpURLConnection) url.openConnection();
			// String cookieVal = c.getHeaderField("Set-Cookie");
			// String sessionId = "CAKEPHP=e312a70f41521af5f6d304363d8cdace";
			// if (cookieVal != null) {
			// sessionId = cookieVal.substring(0, cookieVal.indexOf(";"));
			// }
			// 发送设置cookie：
			c = (HttpURLConnection) url.openConnection();
			// String cookieVal = c.getHeaderField("Set-Cookie");
			// if (cookieVal != null) {
			// sessionId = cookieVal.substring(0, cookieVal.indexOf(";"));
			// }
			// System.out.println("sessionId:" + sessionId);
			// if (sessionId != null) {
			// c.setRequestProperty("Cookie", sessionId);
			// }

			// Client client = new Client();
			// CookieJar cj = client.getCookies(c);
			// c.disconnect();
			// c = (HttpURLConnection) url.openConnection();
			// client.setCookies(c, cj);

			c.setRequestProperty("User-Agent",
					"Mozilla/4.0 (compatible; MSIE 9.0; Windows 2000)");
			c.setRequestMethod("GET");
			c.setRequestProperty("content-type", "text/html");
			c.setRequestProperty("Accept-Charset", "utf-8");
			c.setDoOutput(true);
			c.setDoInput(true);

			// c.setConnectTimeout(30000);//设置连接主机超时（单位：毫秒）
			// c.setReadTimeout(30000);//设置从主机读取数据超时（单位：毫秒）
			c.connect();

			System.out.println("begin-" + strXml + "");
			// PrintWriter out = new PrintWriter(c.getOutputStream());// 发送数据
			// out.print(strXml);
			// out.flush();
			// out.close();

			// c.geth
			String header;
			for (int i = 0; true; i++) {
				header = c.getHeaderField(i);
				if (header == null)
					break;
				// System.out.println("header:" + header);
				// System.out.println(c.getContentType());
			}
			int res = 0;
			res = c.getResponseCode();
			System.out.println("res=" + res);

			if (res == 200) {

				InputStream u = c.getInputStream();// 获取servlet返回值，接收
				BufferedReader in = new BufferedReader(new InputStreamReader(u));
				buffer = new StringBuffer();
				String line = "";
				String returnStr = "";

				while ((line = in.readLine()) != null) {

					byte[] byteStr = line.getBytes();
					returnStr = new String(byteStr, "utf-8");
					buffer.append(returnStr);
				}
				System.out.println("---------->>" + buffer.toString());
			} else {
				System.out.println("------接收出错了-------");
			}
			c.disconnect();
			System.out.println("-------end--------");

			// System.out.println();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("异常！");
			System.out.println(e.toString());
		}
		return buffer.toString();
	}
}