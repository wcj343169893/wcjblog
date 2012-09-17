package andy;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;

public class fw_bm_test {

	public static void main(String[] args) {
		
		//程序太简单还要为logging加个properties觉得麻烦，就google到了下面的办法，代码中加入下面几行代码就可以了:
			System.setProperty(
			"org.apache.commons.logging.Log",
			"org.apache.commons.logging.impl.SimpleLog");
			System.setProperty(
			"org.apache.commons.logging.simplelog.showdatetime",
			"true");
			System.setProperty(
			"org.apache.commons.logging" +
			".simplelog.log.org.apache.commons.httpclient",
			"error");

			
		//hbqnbTest();

		fzwb_banMain_Img();
	}

	public static void fzwb_banMain_Img() {
		
		String url = "http://www.fawan.com.cn";
		String imgurl = "http://www.fawan.com.cn/res/";
		String content = getpage(url);
		String gourl = getIndexGoto(content);
		String ycjrq = cjrq(gourl);//要跳转的日期
		
		String qz = url+"/html/"+ycjrq;//url前缀
		
		String dateurl = qz+"/node_2.htm";
		String index1 = getpage(dateurl);
		List<String[]> BanMianList = getBanMianList(index1);
		System.out.println(BanMianList.size());
	}
	
	//取得法制晚报版面文字及url
	public static List<String[]> getBanMianList(String content){
		//String pattern = "<a id=pageLink href=(.+?) class=mulu>(.+?)</a>";
		//String pattern = "<a id=pageLink href=(.+?) class=mulu>(.+?)</a></td>\\s<td width=\"16\"><a href=(.+?)>+?</a>";
		String pattern = "href=(\\S+)[^>]*>([^>]+)</a></td>\\s*<td width=\"16\"><a\\s*href=([^>]+)><img[^>]+?src=\"([^\"]+)";
		Pattern p = Pattern.compile(pattern);
		Matcher m = p.matcher(content);
		List<String[]> list = new ArrayList<String[]>();
		while (m.find()) {
            String tmp[] = new String[4];
            
            tmp[0] = m.group(1);
            tmp[1] = m.group(2);
            tmp[2] = m.group(3);
            tmp[3] = m.group(4);
            
			list.add(tmp);
		}
		return list;
	} 
	
	//解析采集日期
	public static String cjrq(String content){

		String[] tmp = content.split("/");
		 
		 return tmp[1]+"/"+tmp[2];
	}
	
	//取得法制晚报页面跳转url
	public static String getIndexGoto(String content){
		String p1 = "CONTENT=\"0; URL=(.+?)\"";
		String rs = getSingleContent_fw(content,p1);
		
		return rs;
	}
	
	public static String getSingleContent_fw(String str, String pattern) {
		Pattern p = Pattern.compile(pattern);
		Matcher m = p.matcher(str);
		String rs = "";
		while (m.find()) {

			rs = m.group(1);
		}
		return rs;
	}
	
	

	public static List<String> getSingleContent(String str, String pattern) {
		Pattern p = Pattern.compile(pattern);
		Matcher m = p.matcher(str);
		String rs = "";
		int i = 1;
		List<String> list = new ArrayList<String>();
		while (m.find()) {

			rs = m.group(1);
			System.out.println("img ["+i+"] =" + rs);
			i ++;
			list.add(rs);
		}
		return list;
	}

	//取得页面源代码
	public static String getpage(String url) {
		HttpClient client = new HttpClient();
		//client.getHostConfiguration().setProxy("202.129.54.77",80);//
		//client.getHostConfiguration().setProxy("121.12.249.207",3128);
		//client.getHostConfiguration().setProxy("124.254.137.84",808);
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
