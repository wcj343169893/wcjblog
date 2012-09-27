package com.google.choujone.blog.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test {
	public static String myurl = "http://localhost:8888/blogType?opera=lists";

	public static void main(String[] args) {
		try {
			// URL urlmy = new URL(myurl);
			// HttpURLConnection con = (HttpURLConnection)
			// urlmy.openConnection();
			// con.setFollowRedirects(true);
			// con.setInstanceFollowRedirects(false);
			// con.connect();
			// BufferedReader br = new BufferedReader(new InputStreamReader(
			// con.getInputStream(), "utf-8"));
			// String s = "";
			// StringBuffer sb = new StringBuffer("");
			// while ((s = br.readLine()) != null) {
			// sb.append(s + "\r\n");
			// }
			// JSONObject json = new JSONObject();
			// System.out.println(sb);
			// <table cellpadding="0" cellspacing="0" class="result"
			// id="2"><tr><td class=f><a
			// onmousedown="return c({'fm':'as','F':'770317EA','F1':'9D73F1E4','F2':'4CA63D6B','F3':'54E5243F','T':'1295162879','title':this.innerHTML,'url':this.href,'p1':2,'y':'FEF8FF47'})"
			// href="http://www.java.com/" target="_blank" ><font
			// size="3"><em>java</em>.com: <em>Java</em> 与您</font></a><br><font
			// size=-1> 立即下载适用于您的桌面计算机的 <em>Java</em> 软件！<br><font
			// color="#008000">www.<b>java</b>.com/ 2011-1-6 </font>- <a
			// href="http://cache.baidu.com/c?m=9d78d513d99b12eb0bf9d33e53198d205f1697624fcacd503a918448e43c08051471e3cc767f4f19&p=aa759a43d39812a05ef68c315605&user=baidu&fm=sc&query=java&qid=f5d3b67f05b74f56&p1=2"
			// target="_blank" class="m">百度快照</a>
			// <br></font></td></tr></table><br>
			// System.out.println(sb.toString());
			// String http_regexp = "(\\w+)://([^/:]+)(:\\d*)?([^#\\s]*)";
			// String regexpForFontAttrib = "([a-z]+)\\s*=\\s*\"([^\"]+)\"";
			// List list = Regexp.getSoftRegexpArray(sb.toString(),
			// "http://.*?htm");
			// // System.out.println(list);
			// for (int i = 0; i < list.size(); i++) {
			// System.out.println(list.get(i));
			// }
			// SpiderThread st = new SpiderThread();
			// st.start();

			Pattern pattern = Pattern
					.compile("^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$");
			Matcher matcher = pattern.matcher("adfsdlfjlk@addda.ff.gg.comd");
			System.out.println(matcher.matches());
			System.out.println(Tools.isEmail(""));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
