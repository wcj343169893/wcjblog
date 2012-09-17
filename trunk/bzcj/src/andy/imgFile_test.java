package andy;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;

public class imgFile_test {
	
	
	public static void main(String[] args) {
		//有图片的
		String strings = "http://www.hbqnb.com/epaper/epaper.asp?epapertype=epaper&dateid=2011-6-15&ban=B&banid=2#2";
		//有图片的,连续间隔的
		//String strings = "http://www.hbqnb.com/epaper/epaper.asp?epapertype=epaper&dateid=2011-6-13&ban=A&banid=3#1";
		//有图片的,连续的
		//String strings = "http://www.hbqnb.com/epaper/epaper.asp?epapertype=epaper&dateid=2011-6-15&ban=A&banid=3#1";
		//http://www.hbqnb.com/news/Files/adminfiles/lutao/20110613/2011061319295388088.jpg
		//没有图片的
		//String strings = "http://www.hbqnb.com/epaper/epaper.asp?epapertype=epaper&dateid=2011-6-13&ban=A&banid=2#1";
		
		//生成图片为空的，如下：
		//http://www.hbqnb.com/epaper/epaper.asp?epapertype=epaper&dateid=2011-6-14&ban=B&banid=2#6
		//http://www.hbqnb.com/epaper/epaper.asp#1
		
		/*	 【说明】
		 Group 1：新闻标题
		 Group 2：副标题
		 Group 3：内容
		 */
		//String allPattern = "[\\s\\S]*?NewsTilte[^>]*?>\\s*([\\s\\S]+?)\\s*</div>[\\s\\S]*?CurtTitle[^>]*?>\\s*([\\s\\S]*?)\\s*</div>[\\s\\S]+?NewsContent[^>]*>\\s*((?=(?:(?!src=)(?!NewsAuthor)[\\s\\S])*src=(\\s*['\"])([^>]*?)(?=\\4))[\\s\\S]+?|[\\s\\S]+?)\\s*</div>[\\s\\S]*?NewsAuthor[^>]*?>([^<]+)";
		//String allPattern =  "<div \\s+id=\"NewsTilte\">\\s*((?:(?!</div>)[\\s\\S])+?)\\s*</div>\\s*<div\\s+id=\"CurtTitle\">\\s*((?:(?!</div>)[\\s\\S])*?)\\s*</div>[\\s\\S]+?<div\\s+id=\"NewsContent\"[^>]+>\\s*([\\s\\S]+?)</div>";
		//String allPattern =  "[\\s\\S]*?NewsTilte[^>]*?>\\s*([\\s\\S]+?)\\s*</div>[\\s\\S]*?CurtTitle[^>]*?>\\s*([\\s\\S]*?)\\s*</div>[\\s\\S]+?NewsContent[^>]*>\\s*((?=(?:(?!src=)(?!NewsAuthor)[\\s\\S])*src=(\\s*['\"])([^>]*?)(?=\4))[\\s\\S]+?|[\\s\\S]+?)\\s*</div>[\\s\\S]*?NewsAuthor[^>]*?>([^<]+)";
		
		String content = getpage(strings);
		String allPattern = "[\\s\\S]*?NewsTilte[^>]*?>\\s*([\\s\\S]+?)\\s*</div>[\\s\\S]*?CurtTitle[^>]*?>\\s*([\\s\\S]*?)\\s*</div>[\\s\\S]+?NewsContent[^>]*>\\s*((?=(?:(?!<script[^>]*src=)[\\s\\S])*src=\\s*(['\"])?((?<=['\"])[^>]+?(?=\\4)|(?!\\s*['\"])[^\\s>]+))[\\s\\S]+?|[\\s\\S]+?)\\s*</div>[\\s\\S]*?NewsAuthor[^>]*?>([^<]+)";
		Pattern p1 = Pattern.compile(allPattern);
		Matcher m1 = p1.matcher(content);

		String retmp[] = null;
		String tmp[] = new String[3];
		List<String[]> list = new ArrayList<String[]>();
		while (m1.find()) {

			/*	 【说明】
			 Group 1：新闻标题
			 Group 2：副标题
			 Group 3：内容
			 */
			
			tmp[0] = m1.group(1);
			System.out.println("标题-----"+ tmp[0]);
			tmp[1] = m1.group(2);			
			tmp[2] = m1.group(3);
			
			//二、提取内容中的图片及上下文
			System.out.println(tmp[2]);
			list = getImgContent(tmp[2]);
			
			String gettmp[] = new String[2];
			StringBuffer getContent = new StringBuffer();//上下文
			StringBuffer getUrl = new StringBuffer();//图片
			for (int i = 0; i < list.size(); i++) {
				gettmp = list.get(i);
				if(gettmp[0] != null){	
					if(gettmp[0].indexOf("ttp://www.hbqnb.com") == -1){
						getUrl.append("ttp://www.hbqnb.com");
					}
					getUrl.append(gettmp[0]);
				}
				getContent.append(gettmp[1]);
				if(gettmp[0] != null && i< list.size()-1){
					getUrl.append(";");
				}
			}
			retmp = new String[2]; 
			
			String imgUrl = getUrl.toString();
			if(imgUrl.lastIndexOf(";") > 0){
				retmp[0] = imgUrl.substring(0, imgUrl.length()-1);//图片
			} else {				
				retmp[0] = getUrl.toString();//图片
			}
			
			if(retmp[0] != null && !"".equals(retmp[0])){
				dptoolsForWindows dt = new dptoolsForWindows();
				//dt.writefile(strings[2].replaceAll("/", "-"), retmp[1], "河北青年报", "河北青年报("+ycjrq+")", strings[1], strings[0]);//生成txt
				if(retmp[0] != null && !"".equals(retmp[0]) && !"null".equals(retmp[0])){
					//保存1或多个文件
					String[] imgFiles = retmp[0].split(";");
					for (int i = 0; i < imgFiles.length; i++) {
						String imgFileNm = "";
						if(i < imgFiles.length -1){
							imgFileNm = tmp[0]+"_"+(i+1);
						} else {
							imgFileNm = tmp[0];
						}
						String imgPath = "F:/gw/updatesoft/报纸采集/";
						try {
							dt.wgetpic(imgFiles[i], imgFileNm, "河北青年报/河北青年报(aaa)");
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}
			retmp[1] = getContent.toString();
			
			//break;
			//list1.add(tmp);
		}
		System.out.println(retmp[0]);
		System.out.println(retmp[1].replaceAll("&nbsp;", " ").replaceAll("<P>", "").replaceAll("</p>", "\r\n").replaceAll("<BR/>", "\r\n").replaceAll("<br/>", "\r\n").replaceAll("<BR>", "\r\n").replaceAll("<br>", "\r\n").replaceAll("<p>", "").replaceAll("</P>", "").replaceAll("<[^>]*>","").replaceAll("null","\r\n"));
		
		
		
	}

	private static  List<String[]> getImgContent(String content) {
		
		//String pattern = "<img\\b[\\s\\S]*?src\\s*=\\s*(['\"])?((?<=['\"])[^>]*?(?=\1)|(?!['\"])[^\\s>]*)[^>]*>";
		//String pattern = "(?:<p>\\s*<img[^>]+?src=['\"]?([^'\"\\s]+)[^>]+></p>)?\\s*((?:<p>(?!\\s*<img)[\\s\\S]+?</p>)+)";
		//String pattern = "(?:<p[^>]*>\\s*<img[^>]+?src=['\"]?([^'\"\\s]+)[^>]*></p>)?\\s*((?:<p[^>]*>(?!\\s*<img)[\\s\\S]+?</p>)+)";
		String pattern = "(?:<p[^>]*>\\s*<img[^>]+?src=['\"]?([^'\"\\s]+)[^>]+></p>)|((?:<p[^>]*>(?!\\s*<img)[\\s\\S]+?</p>)+)";
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

			tmp[1] = m.group(2);//url
			// tmp[2] = m.group(3);
			list.add(tmp);
		}
		System.out.println(list.size());
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
