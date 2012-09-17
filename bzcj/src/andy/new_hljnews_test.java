package andy;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;

public class new_hljnews_test {

	public static void main(String[] args) {

		//程序太简单还要为logging加个properties觉得麻烦，就google到了下面的办法，代码中加入下面几行代码就可以了:
		System.setProperty("org.apache.commons.logging.Log",
				"org.apache.commons.logging.impl.SimpleLog");
		System.setProperty("org.apache.commons.logging.simplelog.showdatetime",
				"true");
		System.setProperty("org.apache.commons.logging"
				+ ".simplelog.log.org.apache.commons.httpclient", "error");
		
		//执行
		//1：取得版面及其url
		// sushibao_bmUrl();
		
		//2:再采集文章标题及其url
		// sushibao_titleList();

		//3:取得pdf的url
		// new_PDFUrl();
		
		//4:取得具体的所有内容
		 getNewsDetailList();	 
		
		
	}
	
	public static String[] getNewsDetailList() {

		StringBuffer content = new StringBuffer();
		//a:取得上面的标题		  
		content.append(sushibao_title());
		System.out.println(content.toString());
		//b:取得图片及其下面的标题
		;
		// 二、提取内容中的图片及上下文
		List<String[]> list = new_titleAndImg();
		String gettmp[] = new String[2];
		StringBuffer getContent = new StringBuffer();// 上下文
		StringBuffer getUrl = new StringBuffer();// 图片
		for (int i = 0; i < list.size(); i++) {
			gettmp = list.get(i);
			if (gettmp[1] != null) {
				
				getUrl.append(gettmp[1]);
			}
			getContent.append(gettmp[0]);
			if (gettmp[1] != null && i < list.size() - 1) {
				getUrl.append(";");
			}
		}
		content.append(getContent.toString());
		System.out.println(content.toString());
		
		//c:取得下面的内容
		content.append(sushibao_bottonTitle());
		
		String retmp[] = new String[2];
		retmp[1] = getUrl.toString();
		// java如何利用正则表达式去掉文本中的HTML标签			
		//retmp[0] = content.toString().replaceAll("<[^>]*>","");	
		retmp[0] = content.toString();
		
		 String creatContent = retmp[0].replaceAll("&nbsp;", "").replaceAll("<P>", "").replaceAll("</p>",
		 "\r\n").replaceAll("</P>","\r\n").replaceAll("<BR/>", "\r\n").replaceAll("<br/>",
		 "\r\n").replaceAll("<BR>", "\r\n").replaceAll("<br>",
		 "\r\n\r\n").replaceAll("<p>", "").replaceAll("</P>",
		 "").replaceAll("null","\r\n").replaceAll("<[^>]*>","");
		System.out.println(creatContent);
		return retmp;
	}
		

	private static void sushibao_bmUrl() {
		//http://epaper.hljnews.cn/shb + 后面的
		String homePage = "http://epaper.hljnews.cn/shb";
		String pageUrl =homePage + "/html/" + "2011-08/" + "25/node_22.htm";

		String content = getpage(pageUrl);
		System.out.println("content= " + content);
		//String pattern= "<td.+?epaperimages\">[\\s\\S]+?<script language=\"JavaScript\" type=\"text/javascript\" src=\"([^\"]+)\"></script>";
		String pattern = "<TD class=\\w+><a href=([^\\s]+) class=\"\\w+\" id=pageLink>([^<]+)<\\/a><\\/TD>";
		Pattern p = Pattern.compile(pattern);

		Matcher m = p.matcher(content);

		List<String[]> list = new ArrayList<String[]>();
		while (m.find()) {

			/*Group 1：图片链接
			Group 2：内容*/

			String tmp[] = new String[2];
			tmp[0] = m.group(1);
			tmp[1] = m.group(2);

			//tmp[2] = m.group(3);
			list.add(tmp);
		}
		System.out.println(list.size());
	}

	//再采集pdf
	private static void new_PDFUrl() {

		String pageUrl = "http://epaper.hljnews.cn/shb/html/2011-08/25/node_27.htm";

		String content = getpage(pageUrl);
		System.out.println("content= " + content);
		//先采集pdf
		//String pattern = "../../../page([^\\s]+)";
		String pattern = "<a\\s+href=(\\S+)\\s[^>]*><IMG[^>]+alt=\"PDF";
		Pattern p = Pattern.compile(pattern);

		Matcher m = p.matcher(content);

		List<String[]> list = new ArrayList<String[]>();
		while (m.find()) {

			/*Group 1：图片链接
			Group 2：内容*/

			String tmp[] = new String[1];
			tmp[0] = m.group(1);
			//tmp[1] = m.group(2);

			//tmp[2] = m.group(3);
			list.add(tmp);
		}
		System.out.println(list.size());
	}
	
	
	
	
	//再采集文章标题及其url
	private static void sushibao_titleList() {

		String pageUrl = "http://epaper.hljnews.cn/shb/html/2011-08/25/node_27.htm";

		String content = getpage(pageUrl);
		System.out.println("content= " + content);
		//采集文章标题及其url
		String pattern = "<a href=(content[^>]+)><div[^>]+>([^<]+)</div>";
		Pattern p = Pattern.compile(pattern);

		Matcher m = p.matcher(content);

		List<String[]> list = new ArrayList<String[]>();
		while (m.find()) {

			/*Group 1：图片链接
			Group 2：内容*/

			String tmp[] = new String[2];
			tmp[0] = m.group(1);
			tmp[1] = m.group(2);

			//tmp[2] = m.group(3);
			list.add(tmp);
		}
		System.out.println(list.size());

		

	}
	
	
	//再采集文章标题及其url
	private static List<String[]> new_titleAndImg() {
		//有多张图片的
		//String pageUrl = "http://epaper.hljnews.cn/shb/html/2011-08/25/content_719808.htm";		
		String pageUrl = "http://epaper.hljnews.cn/shb/html/2011-08/27/content_721032.htm";
		//没有图片的
		//String pageUrl = "http://epaper.hljnews.cn/shb/html/2011-08/25/content_719815.htm";	
		//String pageUrl = "http://epaper.hljnews.cn/shb/html/2011-08/25/content_719818.htm";
		//有1张图片的
		//String pageUrl = "http://epaper.hljnews.cn/shb/html/2011-08/25/content_719816.htm";
			
		String content = getpage(pageUrl);
		System.out.println("content= " + content);
		//先采集pdf
		//分开采集
		
		//String pattern = "<table width=\"96%\"[^>]*>[\\s\\S]+?</founder-content>[\\s\\S]+?</table>";
		
		String pattern = "<IMG\\s+src=\"([^\"]+)\">[\\s\\S]+?<TD>\\s*([^<]*)";
		//<TABLE><TBODY><TR><TD><IMG src=(.+?)></TD></TR></TBODY></TABLE>
		Pattern p = Pattern.compile(pattern);

		Matcher m = p.matcher(content);

		List<String[]> list = new ArrayList<String[]>();
		while (m.find()) {

			/*Group 1：图片链接
			Group 2：内容*/

			String tmp[] = new String[2];
			// java如何利用正则表达式去掉文本中的HTML标签			
			tmp[0] = m.group(0).replaceAll("<[^>]*>","");				
			tmp[1] = m.group(1);

			//tmp[2] = m.group(3);
			list.add(tmp);
		}
		System.out.println(list.size());
		
		return list;
	}
	
	
	
	//再采集文章标题及其url
	private static String sushibao_bottonTitle() {
		//有多张图片的
		//String pageUrl = "http://epaper.hljnews.cn/shb/html/2011-08/25/content_719808.htm";	
		String pageUrl = "http://epaper.hljnews.cn/shb/html/2011-08/27/content_721032.htm";
		//没有图片的
		//String pageUrl = "http://epaper.hljnews.cn/shb/html/2011-08/25/content_719815.htm";	
		//String pageUrl = "http://epaper.hljnews.cn/shb/html/2011-08/25/content_719818.htm";
		//有1张图片的
		//String pageUrl = "http://epaper.hljnews.cn/shb/html/2011-08/25/content_719816.htm";
			
		String content = getpage(pageUrl);
		System.out.println("content= " + content);
		
		String bottonCont = "";
		//先采集pdf
		//分开采集
		
		//下面的可用
		String pattern = "<founder-content>[\\s\\S]+?</founder-content>";		
		
		Pattern p = Pattern.compile(pattern);

		Matcher m = p.matcher(content);

		//List<String[]> list = new ArrayList<String[]>();
		while (m.find()) {

			/*Group 1：图片链接
			Group 2：内容*/

			String tmp[] = new String[1];
			tmp[0] = m.group(0);
			bottonCont = tmp[0];
			//tmp[1] = m.group(1);

			//tmp[2] = m.group(3);
			//list.add(tmp);
		}
		//System.out.println(list.size());	
		return bottonCont;

	}
	
	
	//取得文章内容最上面的几个标题
	private static String sushibao_title() {
		//有多张图片的
		//String pageUrl = "http://epaper.hljnews.cn/shb/html/2011-08/25/content_719808.htm";	
		String pageUrl = "http://epaper.hljnews.cn/shb/html/2011-08/27/content_721032.htm";
		//没有图片的
		//String pageUrl = "http://epaper.hljnews.cn/shb/html/2011-08/25/content_719815.htm";	
		//String pageUrl = "http://epaper.hljnews.cn/shb/html/2011-08/25/content_719818.htm";
		//有1张图片的
		//String pageUrl = "http://epaper.hljnews.cn/shb/html/2011-08/25/content_719816.htm";
			
		String content = getpage(pageUrl);
		System.out.println("content= " + content);
		
		//分开采集
		String pattern = "<span style=.*?>([^<]+)</span><br>\\s<strong style=.*?>([^<]+)</strong><br>\\s<span style=.*?>([^<]*)</span><br>";
		/*String pattern = "<span style=.*?>([^<]+)</span><br>\\s<strong style=.*?>([^<]+)</strong><br>\\s<span style=.*?>([^<]*)</span><br>" +
				".*((?:<founder-content><p>[\\s\\S]+?</p></founder-content>)+)";*/
		
		
		Pattern p = Pattern.compile(pattern);

		Matcher m = p.matcher(content);

		List<String[]> list = new ArrayList<String[]>();
		
		String title ="";
		while (m.find()) {

			/*Group 1：图片链接
			Group 2：内容*/

			String tmp[] = new String[2];
			tmp[0] = m.group(0);
			
			title = tmp[0];
		}
		return title;

		

	}
	
	


	public static List<String> getSingleContent(String str, String pattern) {
		Pattern p = Pattern.compile(pattern);
		Matcher m = p.matcher(str);
		String rs = "";
		int i = 1;
		List<String> list = new ArrayList<String>();
		while (m.find()) {

			rs = m.group(1);
			System.out.println("img [" + i + "] =" + rs);
			i++;
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
		method.getParams().setContentCharset("utf-8");
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
