package andy;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;

public class hljnews_test {

	public static void main(String[] args) {

		//程序太简单还要为logging加个properties觉得麻烦，就google到了下面的办法，代码中加入下面几行代码就可以了:
		System.setProperty("org.apache.commons.logging.Log",
				"org.apache.commons.logging.impl.SimpleLog");
		System.setProperty("org.apache.commons.logging.simplelog.showdatetime",
				"true");
		System.setProperty("org.apache.commons.logging"
				+ ".simplelog.log.org.apache.commons.httpclient", "error");

		//hbqnbTest();

		//fzwb();
		//1：取得版面及其url
		//sushibao_bmUrl();
		//2:采集pdf
		//sushibao_PDFUrl();
		//3:文章标题及其url
		//sushibao_titleList();
		//4:采集文章内容及其图片
		//http://epaper.hljnews.cn/shb/html/2011-08/25/content_719807.htm
		//sushibao_titleAndImg();
		
		//sushibao_title();
		
		//标哥做的
		//1:取得pdf的url
		//new_PDFUrl();
		//2:取得所有内容
		new_titleAndImg();
		//2:所有内容与图片
	}
		

	private static void sushibao_bmUrl() {

		String pageUrl = "http://epaper.hljnews.cn/shb/html/2011-08/25/node_27.htm";

		String content = getpage(pageUrl);
		System.out.println("content= " + content);
		//String pattern= "<td.+?epaperimages\">[\\s\\S]+?<script language=\"JavaScript\" type=\"text/javascript\" src=\"([^\"]+)\"></script>";
		//正面2条都可以用
		//String pattern = "<TD class=\\w+><a href=([^\\s]+) class=\"\\w+\" id=pageLink>([^<]+)</a></TD>";
		String pattern = "<TD class=.*?><a href=([^\\s]+) class=.*? id=pageLink>([^<]+)</a></TD>";
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
	
	
	//再采集pdf
	private static void sushibao_PDFUrl() {

		String pageUrl = "http://epaper.hljnews.cn/shb/html/2011-08/25/node_27.htm";

		String content = getpage(pageUrl);
		System.out.println("content= " + content);
		//先采集pdf
		String pattern = "../../../page([^\\s]+)";
		Pattern p = Pattern.compile(pattern);

		Matcher m = p.matcher(content);

		List<String[]> list = new ArrayList<String[]>();
		while (m.find()) {

			/*Group 1：图片链接
			Group 2：内容*/

			String tmp[] = new String[2];
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
	private static void new_titleAndImg() {
		//有多张图片的
		 String pageUrl = "http://epaper.hljnews.cn/shb/html/2011-08/25/content_719808.htm";		
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
		//String pattern = "<IMG src=\"([^\"]+)\">[\\s\\S]+?<td>\\s*([^<]*)";
		String pattern = "<IMG src=\"../../../(.+?)\">[\\s\\S]+?<TD>\\s*([^<]*)";
		
		//<TABLE><TBODY><TR><TD><IMG src=(.+?)></TD></TR></TBODY></TABLE>
		Pattern p = Pattern.compile(pattern);

		Matcher m = p.matcher(content);

		String allCont = "";
		List<String[]> list = new ArrayList<String[]>();
		while (m.find()) {

			/*Group 1：图片链接
			Group 2：内容*/

			String tmp[] = new String[2];
			tmp[0] = m.group(0);
			allCont = tmp[0];
						
			tmp[1] = m.group(1);

			//tmp[2] = m.group(3);
			list.add(tmp);
		}
		System.out.println(list.size());

		
		
		

	}
	
	//再采集文章标题及其url
	private static void sushibao_titleAndImg() {
		//有多张图片的
		//String pageUrl = "http://epaper.hljnews.cn/shb/html/2011-08/25/content_719808.htm";		
		//没有图片的
		//String pageUrl = "http://epaper.hljnews.cn/shb/html/2011-08/25/content_719815.htm";	
		String pageUrl = "http://epaper.hljnews.cn/shb/html/2011-08/25/content_719818.htm";
		//有1张图片的
		//String pageUrl = "http://epaper.hljnews.cn/shb/html/2011-08/25/content_719816.htm";
			
		String content = getpage(pageUrl);
		System.out.println("content= " + content);
		//先采集pdf
		//分开采集
		
		//下面的可用
		//String pattern = "<founder-content>[\\s\\S]+?</founder-content>";		
		String pattern = "<TABLE><TBODY><TR><TD><IMG src=(.+?)></TD></TR></TBODY></TABLE>";
		//String pattern = "<TR><TD>[^<]*</TD></TR>";
		//String pattern = "<TR><TD><IMG src=(.+?)></TD></TR><TR><TD>([^<].+?)<\\/TD><\\/TR>";
		
		Pattern p = Pattern.compile(pattern);

		Matcher m = p.matcher(content);

		List<String[]> list = new ArrayList<String[]>();
		while (m.find()) {

			/*Group 1：图片链接
			Group 2：内容*/

			String tmp[] = new String[2];
			tmp[0] = m.group(0);
			tmp[1] = m.group(1);

			//tmp[2] = m.group(3);
			list.add(tmp);
		}
		System.out.println(list.size());

		

	}
	
	
	//取得文章内容最上面的几个标题
	private static String sushibao_title() {
		//有多张图片的
		//String pageUrl = "http://epaper.hljnews.cn/shb/html/2011-08/25/content_719808.htm";		
		//没有图片的
		String pageUrl = "http://epaper.hljnews.cn/shb/html/2011-08/25/content_719815.htm";	
		//String pageUrl = "http://epaper.hljnews.cn/shb/html/2011-08/25/content_719818.htm";
		//有1张图片的
		//String pageUrl = "http://epaper.hljnews.cn/shb/html/2011-08/25/content_719816.htm";
			
		String content = getpage(pageUrl);
		System.out.println("content= " + content);
		
		//分开采集
		//String pattern = "<span style=.*?>([^<]+)</span><br>\\s<strong style=.*?>([^<]+)</strong><br>\\s<span style=.*?>([^<]*)</span><br>";
		String pattern = "<span style=.*?>([^<]+)</span><br>\\s<strong style=.*?>([^<]+)</strong><br>\\s<span style=.*?>([^<]*)</span><br>" +
				".*((?:<founder-content><p>[\\s\\S]+?</p></founder-content>)+)";
		
		
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
	
	private static void hbqnbTest() {
		// http://www.hbqnb.com/epaper/epaper.asp?epapertype=epaper&dateid=2011-6-13&ban=A&banid=13
		// http://www.hbqnb.com/news/Files/adminfiles/jiaodou/20110613/2011061302395129448.jpg
		//http://www.hbqnb.com/news/Files/adminfiles/jiaodou/20110613/2011061302402781229.jpg
		// http://www.hbqnb.com/news/Files/adminfiles/lutao/20110613/2011061320080694015.jpg
		String strings = "http://www.hbqnb.com/epaper/epaper.asp?epapertype=epaper&dateid=2011-7-9&ban=A&banid=4";

		String content = getpage(strings);

		// String pattern = "(?s)class=\"epapernewstitle\" target=\"_blank\" href=\"([^\"]+)\".*?title=\"([^\"]+)";	
		// String pattern = "<a[^>]+href=['\"]([^'\">\\s]+)[^>]+>[^<]+</a><a\\s[^>]*href=['\"]?([^'\"\\s]+)[^>]+title=['\"]?([^'\">]+)";
		//String pattern = "<script language=JavaScript type=text/javascript src=(.+?)>";
		//(?s)<div class="epaper"[^>]+><label id="([^"]+)">.*?src="([^"]+)"

		// String pattern = "(?s)id=\"NewsContent\" class=\"content\">(.*)<div id=\"NewsAuthor\">";
		String pattern = "(?:<p>\\s*<img[^>]+?src=['\"]?([^'\"\\s]+)[^>]+></p>)?\\s*((?:<p>(?!\\s*<img)[\\s\\S]+?</p>)+)";
		
		
		Pattern p = Pattern.compile(pattern);

		Matcher m = p.matcher(content);

		List<String[]> list = new ArrayList<String[]>();
		while (m.find()) {

			/*Group 1：图片链接
			Group 2：内容*/

			String tmp[] = new String[2];
			tmp[0] = m.group(1);
			//java如何利用正则表达式去掉文本中的HTML标签
			/*String noHtmlContent = tmp[0].replaceAll("<[^>]*>",""); 
			System.out.println(noHtmlContent.toString());*/

			tmp[1] = m.group(2);
			// tmp[2] = m.group(3);
			list.add(tmp);
		}
		System.out.println(list.size());
	}

	public static void fzwb() {

		//多张图片时
		//String url = "http://www.fawan.com.cn/html/2011-07/06/content_316019.htm";
		//http://www.fawan.com.cn/res/1/1/2011-07/06/A41/res01_attpic_brief.jpg
		//http://www.fawan.com.cn/res/1/1/2011-07/06/A41/res04_attpic_brief.jpg
		String url = "http://www.fawan.com.cn/html/2011-07/06/content_316020.htm";
		//http://www.fawan.com.cn/res/1/1/2011-07/06/A41/res07_attpic_brief.jpg
		//http://www.fawan.com.cn/res/1/1/2011-07/06/A41/res10_attpic_brief.jpg
		//http://www.fawan.com.cn/res/1/1/2011-07/06/A41/res13_attpic_brief.jpg

		//1张图片时
		//String url = "http://www.fawan.com.cn/html/2011-07/06/content_315951.htm";

		//没有图片时
		//String url = "http://www.fawan.com.cn/html/2011-07/06/content_315955.htm";

		String urlContent = getpage(url);
		System.out.println("urlContent = " + urlContent);
		List imgUrl = null;

		if (urlContent.indexOf("res/") >= 0) {
			imgUrl = getPic(urlContent);
		}
		System.out.println("imgUrl = " + imgUrl.toString());

	}

	//取得法制晚报详细页图片
	public static List getPic(String content) {
		//以下两个表达式都可以取到1到多张图片
		String p1 = "<IMG src=\"../../../res/(.+?)\">";
		//String p1 = "<IMG src=\"(../../../res/[^>]+)\">";//<IMG src=\"(\.\.\/\.\.\/\.\.\/res\/[^>]+)">
		List imgList = getSingleContent(content, p1);

		return imgList;
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
