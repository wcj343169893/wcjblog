package andy;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;

public class CopyOftest {
 public static void main(String[] args) {
	 
	 
	 System.out.println(String.valueOf((int)(Math.random()*100000)));
	 
	 String strings ="http://www.hbqnb.com/epaper/epaper.asp?epapertype=epaper&dateid=2011-6-10&ban=A&banid=4";
	
	 String index2 = getpage(strings);	 
	 System.out.println(index2);
	 
	 String pattern = "<a\\s[^>]*href=['\"]?([^'\"\\s]+)[^>]+title=['\"]?([^'\">]+)";
		Pattern p = Pattern.compile(pattern);
		String content ="/epaper/Showepaper.asp?epapertype=epaperimages&dateid=2011-06-10 0:00:00&ban=A&banid=1&SpanId=NS_show_epaper_A01";
		
		content = index2;
		Matcher m = p.matcher(content);
		List<String[]> list = new ArrayList<String[]>();
		while (m.find()) {
         String tmp[] = new String[3];         
         tmp[0] = m.group(1);
         tmp[1] = m.group(2);
         tmp[2] = m.group(3);
		list.add(tmp);
		}
		System.out.println(list.size());
}
 
//取得页面源代码
	public static String getpage(String url) {
		HttpClient client = new HttpClient();
		GetMethod method = new GetMethod(url);
		method.getParams().setContentCharset("gb2312");
		method
				.setRequestHeader(
						"Accept",
						"image/gif, image/x-xbitmap, image/jpeg, image/pjpeg, application/x-shockwave-flash, application/vnd.ms-excel, application/vnd.ms-powerpoint, application/msword, */*");
		method.setRequestHeader("Accept-Language", "zh-cn");
		method.setRequestHeader("Accept-Charset", "GB2312,utf-8;");
		method
				.setRequestHeader("User-Agent",
						"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; .NET CLR 2.0.50727)");
		method.setRequestHeader("Host", "epaper.nbd.com.cn");
		method.setRequestHeader("Connection", "Keep-Alive");
		String responseStr = "";
		try {
			client.executeMethod(method);
			responseStr = method.getResponseBodyAsString();
			method.releaseConnection();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return responseStr;

	}
	
}
