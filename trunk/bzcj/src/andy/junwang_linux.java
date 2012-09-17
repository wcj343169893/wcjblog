package andy;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.log4j.Logger;

/**
 * 军网数字报采集
 * 网址:http://www.hbqnb.com/epaper/epaper_index.asp?epapertype=epaperimages&dateid=2011-6-15
 * 具体第版:http://www.hbqnb.com/epaper/epaper.asp?epapertype=epaper&dateid=2011-6-10&ban=A&banid=1
 */
public class junwang_linux {

	static Logger logger = Logger.getLogger(junwang_linux.class);
	final static String nspName = "junwangbao";	
	final static String imgPath = "/usr/local/webserver/nginx/html/ewj/gw/updatesoft/报纸采集/";

	public static void main(String[] args) throws Exception {

		String rq = "";
		// cjjj(rq);

		// 手动输入日期
		if (args.length > 0) {
			if (!"".equals(args[0]))
				rq = args[0];
		}
		// 手动输入日期

		cj(rq);

	}

	public static String getTdate(String type) {
		if(!"".equals(type)){
			return new SimpleDateFormat(type).format(new Date());
		}
		return new SimpleDateFormat("yyyy-MM/dd").format(new Date());
	}

	/**
	 * 取一个日期之前或之后多少天的日期
	 * 
	 * @param date
	 * @param days
	 * @return
	 */
	public static String addDayToDate(Date date, int days,String type) {
		if (date == null) {
			return null;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_YEAR, days);
		// return calendar.getTime();
		
		if(!"".equals(type)){
			return new SimpleDateFormat(type).format(calendar.getTime());
		}
		
		return new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());
	}

	// 取得页面源代码
	public static String getpage(String url) {
		HttpClient client = new HttpClient();
		// client.getHostConfiguration().setProxy("202.129.54.77",80);//
		// client.getHostConfiguration().setProxy("121.12.249.207",3128);
		// client.getHostConfiguration().setProxy("124.254.137.84",808);
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

	public static List<String[]> getBanMianList(String content) {
		
		//String pattern = "<label id=\"([^\"]+)\">[^<]+?</label><script language=\"JavaScript\" type=\"text/javascript\" src=\"([^\"]+)\">";
		String pattern = "<li><a id=pageLink href=(.+?)>(.+?)</a></li>";
		Pattern p = Pattern.compile(pattern);
		Matcher m = p.matcher(content);
		List<String[]> list = new ArrayList<String[]>();
		while (m.find()) {
			String tmp[] = new String[2];

			tmp[0] = m.group(1);// 面板名字

			tmp[1] = m.group(2);// URL

			list.add(tmp);
		}
		return list;
	}

	private static String hbqnb_bmImg(String content) {

		//String pattern = "<td.+?epaperimages\">[\\s\\S]+?<script language=\"JavaScript\" type=\"text/javascript\" src=\".*?imageslink=(.+?\\.jpg)[^\"]+\"></script>";
		String pattern = "<a href=../../(.+?)><img";
		Pattern p = Pattern.compile(pattern);

		Matcher m = p.matcher(content);

		String tmpImg = "";
		String url ="http://www.chinamil.com.cn/jfjbmap/content/";
		while (m.find()) {
			// Group 1：图片链接
			tmpImg = url + m.group(1);			
		}
		return tmpImg;

	}

	public static List<String[]> getNewsList(String content) {

		// String pattern = "(?s)class=\"epapernewstitle\" target=\"_blank\"
		// href=\"([^\"]+)\".*?title=\"([^\"]+)";
		//String pattern = "<a[^>]+href=['\"]([^'\">\\s]+)[^>]+>[^<]+</a><a\\s[^>]*href=['\"]?([^'\"\\s]+)[^>]+title=['\"]?([^'\">]+)";
		 String pattern = "<a href=(.+?)><div style=\"display:inline\" id=(.+?)>(.+?)</div></a";
		Pattern p = Pattern.compile(pattern);
		Matcher m = p.matcher(content);
		List<String[]> list = new ArrayList<String[]>();
		while (m.find()) {
			String tmp[] = new String[3];

			tmp[0] = m.group(1);// url
			tmp[1] = m.group(2);// url2
			tmp[2] = m.group(3);// title
			list.add(tmp);
		}
		return list;
	}

	public static String[] getNewsDetailList(String content, String titleNm) {

		// String pattern = "(?s)id=\"NewsContent\" class=\"content\">(.*)<div
		// id=\"NewsAuthor\">";
		//String pattern = "<founder-content>\\s*([\\s\\S]+?)\\s*<</founder-content>>[\\s\\S]*?CurtTitle[^>]*?>\\s*([\\s\\S]*?)\\s*</div>[\\s\\S]+?NewsContent[^>]*>\\s*((?=(?:(?!<script[^>]*src=)[\\s\\S])*src=\\s*(['\"])?((?<=['\"])[^>]+?(?=\\4)|(?!\\s*['\"])[^\\s>]+))[\\s\\S]+?|[\\s\\S]+?)\\s*</div>[\\s\\S]*?NewsAuthor[^>]*?>([^<]+)";
		String tmp[] = new String[3];
		 StringBuffer articleContent = new StringBuffer();
		//1:头部的文章
		  articleContent.append(getHeadContent(content));
		//2:内容中间的图片			 
		List<String[]> list = getImgContent(content);
		String gettmp[] = new String[2];
		
		StringBuffer getUrl = new StringBuffer();// 图片
		for (int i = 0; i < list.size(); i++) {
			gettmp = list.get(i);
			if (gettmp[0] != null) {
				/*if (gettmp[0].indexOf("ttp://www.hbqnb.com") == -1) {
					getUrl.append("http://www.hbqnb.com");
				}*/
				getUrl.append(gettmp[0]);
			}
			//getContent.append(gettmp[1]);
			if (gettmp[0] != null && i < list.size() - 1) {
				getUrl.append(";");
			}
		}
		
		String imgUrl = getUrl.toString();

		if (!"".equals(imgUrl)
				&& imgUrl.lastIndexOf(";") == imgUrl.length() - 1) {
			tmp[1] = imgUrl.substring(0, imgUrl.length() - 1);// 图片
		} else {
			tmp[1] = getUrl.toString();// 图片
		}
	 
		//底部的文章
	  articleContent.append(getContent(content));
	  tmp[0] = articleContent.toString();		
		return tmp;
	}
	
	/**
	 * 文章头部的内容
	 */
	public static String getHeadContent(String content) {		
	  
		String pattern = "<h1 style=\"line-height:140%;\">\\s*([\\s\\S]+?)\\s*</h1>";
		Pattern p = Pattern.compile(pattern);
		Matcher m = p.matcher(content);

		String retmp = null;

		while (m.find()) {
			
			/*
			 * 底部的内容
			 */
			retmp = m.group(1).replaceAll("<[^>]*>","\r\n");		

		}
		return retmp;
	}


	/**
	 * 底部的内容
	 */
	public static String getContent(String content) {		
	  
		String pattern = "<founder-content>\\s*([\\s\\S]+?)\\s*</founder-content>";
		Pattern p = Pattern.compile(pattern);
		Matcher m = p.matcher(content);

		String retmp = null;

		while (m.find()) {
			
			/*
			 * 底部的内容
			 */
			retmp = m.group(1);			

		}
		return retmp;
	}

	
	private static List<String[]> getImgContent(String content) {
		
		String baseUrl = "http://www.chinamil.com.cn/jfjbmap/";
		String pattern = "<TABLE><TBODY><TR><TD><IMG src=\"../../../(.+?)\"></TD></TR>";
		Pattern p = Pattern.compile(pattern);
		Matcher m = p.matcher(content);

		List<String[]> list = new ArrayList<String[]>();
		while (m.find()) {

			/*
			 * Group 1：图片链接 Group 2：内容
			 */

			String tmp[] = new String[2];
			tmp[0] = baseUrl + m.group(1);			
			list.add(tmp);
		}
		System.out.println(list.size());
		return list;
	}

	public static void cj(String rq) throws Exception {

		try {
			// 取得当前日期
			 String ycjrq = getTdate("");//
			 String ycjrqD = getTdate("yyyy-MM-dd");//
			// 取得昨天
			//String ycjrq = addDayToDate(new Date(),-1);

			// 取得明天
			// String ycjrq = addDayToDate(new Date(),1);

			if (!"".equals(rq)) {
				ycjrq = rq;
			}

			// 查看该日期是否采集
			DbBeanCommon db = new DbBeanCommon();
			if (db.ifGetThisDay(ycjrq, nspName)) {
				logger.info("---" + ycjrq + " ---- 该日期--军网数字报--已采集过---------");
				System.exit(0);

			}

			dptools dt = new dptools();
			//http://www.chinamil.com.cn/jfjbmap/content/2011-09/16/node_2.htm
			String baseUrl = "http://www.chinamil.com.cn/jfjbmap/";
			String url = baseUrl + "content/";
			//String url = "http://www.hbqnb.com/epaper/epaper_index.asp?epapertype=epaperimages&dateid=";
			String bmUrlDomain = url + ycjrq;
					
			// String domain = "http://epaper.nbd.com.cn";

			// 打开首页
			String gourl = url + ycjrq + "/node_2.htm" ;
			String index = getpage(gourl);
			/*
			 * String title = dt.getSingleContent(index, "<title>(.+?)</title>");//取得首页标题
			 * if(title.indexOf(ycjrq)==-1){//判断当日的内容是否发行
			 * logger.info("该日期还没有发行"); System.exit(0);
			 *  }
			 */

			// 采集版面列表
			if (!db.ifGetThisDayIndex(ycjrq, nspName)) {
				logger.info("开始采集--军网数字报--" + ycjrq + "的版面......");
				List<String[]> BanMianList = getBanMianList(index);
				// 用来标志今天的报纸是否发布
				if (BanMianList.isEmpty()
						|| (BanMianList != null && BanMianList.size() == 0)) {
					logger.info("----------------" + ycjrq
							+ "---该日期--军网数字报--尚未发布---------");
					System.exit(0);
				}
				for (String[] strings : BanMianList) {
					
					String url2 = bmUrlDomain +"/"+ strings[0].replaceAll("./", "");
					
					StringBuffer sql = new StringBuffer();
					sql
							.append(
									"INSERT INTO banmian (content,url,lx,bmdate) VALUES (")
							.append("'" + strings[1].replaceAll("/", "-").replaceAll(" ", ""))
							.append("','").append(url2).append("','").append(
									nspName).append("','").append(ycjrq)
							.append("')");
					db.execInsertSql(sql.toString());
				}
				logger.info("采集" + ycjrq + "的版面结束");
				db
						.execInsertSql("INSERT INTO cjzt (cjrq,indexcjzt,cjlx) VALUES ('"
								+ ycjrq + "', '1','" + nspName + "')");
				logger.info("更新索引采集状态");
			}

			// 采集新闻列表
			List<String[]> BanMianList = db.getWaitingBanMianList(ycjrq,
					nspName);

			String sql = "";
			String index2 = "";
			List<String[]> tmp;
			for (String[] strings : BanMianList) {
				sql = "delete from newslist where bmid=" + strings[0];
				db.execInsertSql(sql);// 既然没有标志该版面为采集完状态，肯定有未采集完成的新闻，先del在重新采集该版面
				logger.info(sql);
				logger.info("--军网数字报--开始采集版面:" + strings[1] + " url:"
						+ strings[2]);
				index2 = getpage(strings[2]);

				// 取得PDF图片的链接
				String bmImgUrl = hbqnb_bmImg(index2);

				/*
				 * 	// 保存版面的所有PDF图片
				 * String dirPath = imgPath + "军网数字报/军网数字报("+ycjrq+")/版面图片/";
				 * dt.wgetpic(bmImgUrl, strings[1].replaceAll("/", "-"),
				 * dirPath);
				 */

				tmp = getNewsList(index2);
				//多个相同名字的标题时
				int i = 1;
				for (String s[] : tmp) {
					String newsUrl = bmUrlDomain + "/" + s[0];					
					String newsName =  s[2].replaceAll("<BR/>", "").replaceAll("/", "-").replace("?", "").trim();
					//当标题名字有相同时,后面加数字来区别
					if(db.ifIsExist(strings[0],newsName)){
						newsName = newsName + i;
						i ++;
					}
					sql = "INSERT INTO newslist (bmid ,content ,url) VALUES ('"
							+ strings[0] + "', '" + newsName + "', '" + newsUrl + "')";
					db.execInsertSql(sql);
					logger.info("采集标题:" + s[2]);
				}
				sql = "update banmian set getdone=1,pdf_url='" + bmImgUrl
						+ "' where id=" + strings[0];
				db.execInsertSql(sql);
				logger.info(sql);
				logger.info("版面:" + strings[1] + " url:" + strings[2] + "采集完成");
			}

			// 采集新闻详细页面
			logger.info("--军网数字报--开始采集详细内容");
			List<String[]> NewsList = db.getWaitingNewsList(ycjrq);
			String index3 = "";
			String img = "";
			String content = "";
			for (String[] strings : NewsList) {
				index3 = getpage(strings[2]);
				img = "";
				content = "";
				logger
						.info("开始采集-----------" + strings[1]
								+ "-----------的详细内容");
				/*
				 * if(strings[1].contains("防暑妙招")||
				 * "防暑妙招（上）".equals(strings[1])){
				 * logger.info("问题点的-------------" + strings[1] +"的详细内容"); }
				 */
				String[] tmpDet = getNewsDetailList(index3, strings[1]);
				
				if (tmpDet[0] != null && !"".equals(tmpDet[0])) {
					content = tmpDet[0].replaceAll("</P>", "\r\n").replaceAll("<[^>]*>","").replaceAll("&nbsp;", "");
					
				}
				if (tmpDet[1] != null && !"".equals(tmpDet[1])) {
					img = tmpDet[1];// 取得图片url
				}
				
				sql = "INSERT INTO detaillist (newsid ,content ,imgurl) VALUES ('"
						+ strings[0] + "', '" + content + "', '" + img + "')";
				db.execInsertSql(sql);
				db.execInsertSql("update newslist set getdone=1 where id = "
						+ strings[0]);
				logger.info("采集 ------------ " + strings[1] + " --------"
						+ strings[2] + " ----------" + img);
			}
			logger.info("--军网数字报--详细内容采集完成");
			logger.info("所有采集工作已经完成");

			// 开始生成文件
			logger.info("--军网数字报--开始生成文件......");
			List<String[]> ContentList = db.getNewsByDate(ycjrq, nspName);
			for (String[] strings : ContentList) {
				if ("".equals(strings[3]))
					continue;
				// 清空html标签
				// String creatContent = strings[3].replaceAll("&nbsp;", "
				// ").replaceAll("<P>", "").replaceAll("</p>",
				// "\r\n").replaceAll("<BR/>", "\r\n").replaceAll("<br/>",
				// "\r\n").replaceAll("<BR>", "\r\n").replaceAll("<br>",
				// "\r\n\r\n").replaceAll("<p>", "").replaceAll("</P>",
				// "").replaceAll("<[^>]*>","").replaceAll("null","\r\n");

				dt
						.writefile(strings[2].replaceAll("/", "-"), strings[3],
								"军网数字报", "军网数字报(" + ycjrqD + ")", strings[1],
								strings[0]);// 生成txt
				if (strings[4] != null && !"".equals(strings[4])
						&& !"null".equals(strings[4])) {
					// 保存1或多个文件
					String[] imgFiles = strings[4].split(";");
					for (int i = 0; i < imgFiles.length; i++) {
						String imgFileNm = "";
						if (i < imgFiles.length - 1) {
							imgFileNm = strings[2] + "_" + (i + 1);
						} else {
							imgFileNm = strings[2];
						}

						dt.wgetpic(imgFiles[i], imgFileNm.replaceAll("/", "-"),
								imgPath + "军网数字报/军网数字报(" + ycjrqD + ")/"
										+ strings[1].replaceAll("·", "-"));
					}
				}
				db.execInsertSql("update detaillist set createdone=1 where id="
						+ strings[0]);
			}

			logger.info("---军网数字报----开始生成pdf图片......");

			List<String[]> bmPdfList = db.getBanMianPdfList(ycjrq, nspName);
			for (String[] strings : bmPdfList) {
				try {
					// 保存版面的所有PDF图片
					String dirPath = imgPath + "军网数字报/军网数字报(" + ycjrqD
							+ ")/版面图片/";
					dt.wgetpic(strings[1], strings[0].replaceAll("/", "-"),
							dirPath);
				} catch (Exception e) {
					logger.info("---生成pdf图片失败----" + e.getMessage());
				}
			}

			logger.info("---军网数字报----生成pdf图片已完成");

			// 如果发布了,就更新报纸的状态
			db.execInsertSql("update cjzt set bmcjzt=1 where cjlx='" + nspName
					+ "' and cjrq='" + ycjrq + "'");

			logger.info("--军网数字报--生成文件已完成");
			logger.info("--军网数字报--全部采集工作已完成");

			// 清空采集数据表
			// 测试时先不删除
			
			 db.execInsertSql("TRUNCATE TABLE `detaillist`");
			 db.execInsertSql("TRUNCATE TABLE `newslist`");
			 db.execInsertSql("TRUNCATE TABLE `banmian`");
			 
			logger.info("--军网数字报--采集数据表已清空");
		} catch (Exception e) {
			logger.info("-------采集过程中有问题--------" + e.getMessage());
		}
	}

	public static String[] getContent(String str, String pattern) {
		Pattern p = Pattern.compile(pattern);
		Matcher m = p.matcher(str);
		String tmp[] = new String[2];

		while (m.find()) {
			tmp[0] = m.group(1);
			tmp[1] = m.group(2);

		}

		return tmp;
	}

}
