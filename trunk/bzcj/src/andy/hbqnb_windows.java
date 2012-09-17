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


public class hbqnb_windows {


	static Logger logger = Logger.getLogger(hbqnb_windows.class);
	final static String nspName = "hbqnb";
	//final static String imgPath = "F:/gw/updatesoft/报纸采集/我的微盘/hh/";

	public static void main(String[] args) throws Exception {

		String rq = "2012-4-25";
		// cjjj(rq);

		// 手动输入日期
		if (args.length > 0) {
			if (!"".equals(args[0]))
				rq = args[0];
		}
		// 手动输入日期

		cj(rq);

	}

	public static String getTdate() {
		return new SimpleDateFormat("yyyy-MM-dd").format(new Date());
	}

	/**
	 * 取一个日期之前或之后多少天的日期
	 * 
	 * @param date
	 * @param days
	 * @return
	 */
	public static String addDayToDate(Date date, int days) {
		if (date == null) {
			return null;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_YEAR, days);
		// return calendar.getTime();
		return new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());
	}

	// 取得页面源代码
	public static String getpage(String url) {
		HttpClient client = new HttpClient();
		// client.getHostConfiguration().setProxy("202.129.54.77",80);//
		// client.getHostConfiguration().setProxy("121.12.249.207",3128);
		// client.getHostConfiguration().setProxy("124.254.137.84",808);
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

	public static List<String[]> getBanMianList(String content) {
		// String pattern = "<a href='(.+?)' style='color:#000000;
		// font-size:12px; text-decoration:none'>(.+?)</a>";
		// <label id="([^"]+)">[^<]+?</label><script language="JavaScript"
		// type="text/javascript" src="([^"]+)">
		// (?s)<div class="epaper"[^>]+><label id="([^"]+)">.*?src="([^"]+)"
		System.out.println(content);
		String pattern = "<label id=\"([^\"]+)\">[^<]+?</label><script language=\"JavaScript\" type=\"text/javascript\" src=\"([^\"]+)\">";

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

		String pattern = "<td.+?epaperimages\">[\\s\\S]+?<script language=\"JavaScript\" type=\"text/javascript\" src=\".*?imageslink=(.+?\\.jpg)[^\"]+\"></script>";

		Pattern p = Pattern.compile(pattern);

		Matcher m = p.matcher(content);

		String tmpImg = "";

		while (m.find()) {

			// Group 1：图片链接
			tmpImg = "http://www.hbqnb.com" + m.group(1);
			// epaper/Shownewsimages.asp?imageslink=/epaper/201107/11/b/RB01B711c.jpg&bantitle=2011年7月
			// 取出来之后要加上url头及去掉后面的&bantitle=2011年7月
			// tmpImg = "http://www.hbqnb.com" +
			// bmImgUrl.substring(bmImgUrl.lastIndexOf("/epaper/"),
			// bmImgUrl.indexOf("&bantitle="));
		}
		return tmpImg;

	}

	public static List<String[]> getNewsList(String content) {

		// String pattern = "(?s)class=\"epapernewstitle\" target=\"_blank\"
		// href=\"([^\"]+)\".*?title=\"([^\"]+)";
		System.out.println(content);
		String pattern = "<a[^>]+href=['\"]([^'\">\\s]+)[^>]+>[^<]+</a><a\\s[^>]*href=['\"]?([^'\"\\s]+)[^>]+title=['\"]?([^'\">]+)";
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
		System.out.println(content);
		String pattern = "[\\s\\S]*?NewsTilte[^>]*?>\\s*([\\s\\S]+?)\\s*</div>[\\s\\S]*?CurtTitle[^>]*?>\\s*([\\s\\S]*?)\\s*</div>[\\s\\S]+?NewsContent[^>]*>\\s*((?=(?:(?!<script[^>]*src=)[\\s\\S])*src=\\s*(['\"])?((?<=['\"])[^>]+?(?=\\4)|(?!\\s*['\"])[^\\s>]+))[\\s\\S]+?|[\\s\\S]+?)\\s*</div>[\\s\\S]*?NewsAuthor[^>]*?>([^<]+)";
		Pattern p = Pattern.compile(pattern);
		Matcher m = p.matcher(content);

		String retmp[] = null;

		while (m.find()) {
			String tmp[] = new String[3];

			/*
			 * 【说明】 Group 1：新闻标题 Group 2：副标题 Group 3：内容
			 */
			tmp[0] = m.group(1);
			/*
			 * if("防暑妙招（上）".equals(tmp[0])||"不剪不行".equals(tmp[0])){
			 * logger.info("---------"); }
			 */

			tmp[1] = m.group(2);

			tmp[2] = m.group(3);

			// 二、提取内容中的图片及上下文

			List<String[]> list = getImgContent(tmp[2]);
			String gettmp[] = new String[2];
			StringBuffer getContent = new StringBuffer();// 上下文
			StringBuffer getUrl = new StringBuffer();// 图片
			for (int i = 0; i < list.size(); i++) {
				gettmp = list.get(i);
				if (gettmp[0] != null) {
					if (gettmp[0].indexOf("ttp://www.hbqnb.com") == -1) {
						getUrl.append("http://www.hbqnb.com");
					}
					getUrl.append(gettmp[0]);
				}
				getContent.append(gettmp[1]);
				if (gettmp[0] != null && i < list.size() - 1) {
					getUrl.append(";");
				}
			}
			retmp = new String[2];

			String imgUrl = getUrl.toString();

			if (!"".equals(imgUrl)
					&& imgUrl.lastIndexOf(";") == imgUrl.length() - 1) {
				retmp[1] = imgUrl.substring(0, imgUrl.length() - 1);// 图片
			} else {
				retmp[1] = getUrl.toString();// 图片
			}
			// retmp[1] = getUrl.toString();
			retmp[0] = getContent.toString();

			if (tmp[0] != null && tmp[0].equals(titleNm)) {
				if (retmp[1] != null && "".equals(retmp[1])) {
					retmp[0] = tmp[2];
				}
				break;
			}

		}
		return retmp;
	}

	private static List<String[]> getImgContent(String content) {
		// String pattern =
		// "(?:<p[^>]*>\\s*<img[^>]+?src=['\"]?([^'\"\\s]+)[^>]*></p>)?\\s*((?:<p[^>]*>(?!\\s*<img)[\\s\\S]+?</p>)+)";
		// String pattern =
		// "<img\\b[\\s\\S]*?src\\s*=\\s*(['\"])?((?<=['\"])[^>]*?(?=\1)|(?!['\"])[^\\s>]*)[^>]*>";
		// String pattern =
		// "(?:<p[^>]*>\\s*<img[^>]+?src=['\"]?([^'\"\\s]+)[^>]+></p>)|((?:<p[^>]*>(?!\\s*<img)[\\s\\S]+?</p>)+)";
		String pattern = "(?:<img[^>]+?src=['\"]?([^'\"\\s]+)[^>]+>)|((?:(?:<(?:p|font)[^>]*>)?(?:(?!<img|</p>|<div|</?font)[\\s\\S])+</(?:p|font)>)+)";
		Pattern p = Pattern.compile(pattern);
		Matcher m = p.matcher(content);

		List<String[]> list = new ArrayList<String[]>();
		while (m.find()) {

			/*
			 * Group 1：图片链接 Group 2：内容
			 */

			String tmp[] = new String[2];
			tmp[0] = m.group(1);
			// java如何利用正则表达式去掉文本中的HTML标签
			/*
			 * String noHtmlContent = tmp[0].replaceAll("<[^>]*>","");
			 * System.out.println(noHtmlContent.toString());
			 */

			tmp[1] = m.group(2);
			// tmp[2] = m.group(3);
			list.add(tmp);
		}
		System.out.println(list.size());
		return list;
	}

	public static void cjjj(String rq) {

		dptoolsForWindows dt = new dptoolsForWindows();
		/*
		 * String index =
		 * getpage("http://epaper.nbd.com.cn/shtml/mrjjxw/20101110/index.shtml");
		 * String title = dt.getSingleContent(index, "<title>(.+?)</title>");//取得首页标题
		 * List<String[]> BanMianList = getBanMianList(index);//取得版面列表 for
		 * (String[] strings : BanMianList) { System.out.println(strings[0]+"
		 * "+strings[1]); }
		 */

		/*
		 * String index1 =
		 * getpage("http://epaper.nbd.com.cn/shtml/mrjjxw/20101110/v11.shtml");
		 * List<String[]> NewsList = getNewsList(index1);//取得新闻列表 for (String[]
		 * strings : NewsList) { System.out.println(strings[0]+" "+strings[1]); }
		 */

		/*
		 * String index2 =
		 * getpage("http://epaper.nbd.com.cn/shtml/mrjjxw/20101110/2046126.shtml");
		 * String img = ""; String content = "";//新闻正文
		 * if(index2.indexOf("class=\"auto-width\"")>=0){//如果有图片取得图片url img =
		 * dt.getSingleContent(index2, "<img class=\"auto-width\" src=\"(.+?)\"
		 * />"); content = dt.getSingleContent(index2, "<div class=\"f-14
		 * height-25\" style=\"width:95%\" align=\"left\">(.+?)</div><br />(.+?)</div>",2); }
		 * else{ content = dt.getSingleContent(index2, "<div class=\"f-14
		 * height-25\" style=\"width:95%\" align=\"left\">(.+?)</div>"); }
		 */

	}

	public static void cj(String rq) throws Exception {

		try {
			// 取得当前日期
			 String ycjrq = getTdate();

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
				logger.info("---" + ycjrq + " ---- 该日期--河北青年报--已采集过---------");
				System.exit(0);

			}

			dptoolsForWindows dt = new dptoolsForWindows();
			String url = "http://www.hbqnb.com/epaper/epaper_index.asp?epapertype=epaperimages&dateid=";
			String bmUrlDomain = "http://www.hbqnb.com/epaper/epaper.asp?epapertype=epaper&dateid="
					+ ycjrq;
			// String domain = "http://epaper.nbd.com.cn";

			// 打开首页
			String gourl = url + ycjrq;
			String index = getpage(gourl);
			/*
			 * String title = dt.getSingleContent(index, "<title>(.+?)</title>");//取得首页标题
			 * if(title.indexOf(ycjrq)==-1){//判断当日的内容是否发行
			 * logger.info("该日期还没有发行"); System.exit(0);
			 *  }
			 */

			// 采集版面列表
			if (!db.ifGetThisDayIndex(ycjrq, nspName)) {
				logger.info("开始采集--河北青年报--" + ycjrq + "的版面......");
				List<String[]> BanMianList = getBanMianList(index);
				// 用来标志今天的报纸是否发布
				if (BanMianList.isEmpty()
						|| (BanMianList != null && BanMianList.size() == 0)) {
					logger.info("----------------" + ycjrq
							+ "---该日期--河北青年报--尚未发布---------");
					System.exit(0);
				}
				for (String[] strings : BanMianList) {
					String pattern = "(?s).*?&ban=([\\w]+)&banid=([\\d]+)";
					String[] banBanids = getContent(strings[1], pattern);
					String banBanid = "&ban=" + banBanids[0] + "&banid="
							+ banBanids[1];
					String url2 = bmUrlDomain + banBanid;

					StringBuffer sql = new StringBuffer();
					sql
							.append(
									"INSERT INTO banmian (content,url,lx,bmdate) VALUES (")
							.append("'" + strings[0].replaceAll(" ", ""))
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
				logger.info("--河北青年报--开始采集版面:" + strings[1] + " url:"
						+ strings[2]);
				index2 = getpage(strings[2]);

				// 保存版面的所有PDF图片
				String bmImgUrl = hbqnb_bmImg(index2);

				/*
				 * String dirPath = imgPath + "河北青年报/河北青年报("+ycjrq+")/版面图片/";
				 * dt.wgetpic(bmImgUrl, strings[1].replaceAll("/", "-"),
				 * dirPath);
				 */

				tmp = getNewsList(index2);
				for (String s[] : tmp) {
					sql = "INSERT INTO newslist (bmid ,content ,url) VALUES ('"
							+ strings[0] + "', '" + s[2].replaceAll("<BR/>", "").replaceAll("/", "-").replace("?", "").trim() + "', '" + strings[2]
							+ s[0] + "')";
					db.execInsertSql(sql);
					logger.info("采集标题:" + s[1]);
				}
				sql = "update banmian set getdone=1,pdf_url='" + bmImgUrl
						+ "' where id=" + strings[0];
				db.execInsertSql(sql);
				logger.info(sql);
				logger.info("版面:" + strings[1] + " url:" + strings[2] + "采集完成");
			}

			// 采集新闻详细页面
			logger.info("--河北青年报--开始采集详细内容");
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
				// content = tmpDet[0];//取得文章内容
				// content = tmpDet[0].replaceAll("&nbsp;", "
				// ").replaceAll("<P>", "").replaceAll("</p>",
				// "\r\n").replaceAll("<BR/>", "\r\n").replaceAll("<br/>",
				// "\r\n").replaceAll("<BR>", "\r\n").replaceAll("<br>",
				// "\r\n").replaceAll("<p>", "").replaceAll("</P>",
				// "").replaceAll("<[^>]*>","").replaceAll("null","\r\n");
				if (tmpDet[0] != null && !"".equals(tmpDet[0])) {
					content = tmpDet[0].replaceAll("<[^>]*>", "").replaceAll(
							"(\n|\r\n)\\s+", "$1").replaceAll("&nbsp;", " ")
							.replaceAll("null", "").replaceAll("/p>", "");
				}

				img = tmpDet[1];// 取得图片url
				/*
				 * if(index3.indexOf("/adminfiles/fanlixin/")>=0){ img =
				 * domain+dt.getSingleContent(index3, "<img border=\"0\"
				 * alt=\"\" src=\"(.+?)\" />"); content =
				 * dt.getSingleContent(index3, "<div class=\"f-14 height-25\"
				 * style=\"width:95%\" align=\"left\">(.+?)</div><br />(.+?)</div>",2); }
				 * else{ content = dt.getSingleContent(index3, "<div
				 * class=\"f-14 height-25\" style=\"width:95%\"
				 * align=\"left\">(.+?)</div>"); }
				 */
				String titleContent = content.replaceAll("<[^>]*>","").replaceAll("<br>", "\r\n").replaceAll("・", "·");
				
				sql = "INSERT INTO detaillist (newsid ,content ,imgurl) VALUES ('"
						+ strings[0] + "', '" + titleContent + "', '" + img + "')";
				db.execInsertSql(sql);
				db.execInsertSql("update newslist set getdone=1 where id = "
						+ strings[0]);
				logger.info("采集 ------------ " + strings[1] + " --------"
						+ strings[2] + " ----------" + img);
			}
			logger.info("--河北青年报--详细内容采集完成");
			logger.info("所有采集工作已经完成");

			// 开始生成文件
			logger.info("--河北青年报--开始生成文件......");
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
						.writefile(strings[2].replaceAll("/", "-").replaceAll(":", "-").replaceAll("・", "·"), strings[3],
								"河北青年报", "河北青年报(" + ycjrq + ")", strings[1],
								strings[0],ycjrq);// 生成txt
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

						/*dt.wgetpic(imgFiles[i], imgFileNm.replaceAll("/", "-").replaceAll(":", "-"),
								imgPath + "河北青年报/河北青年报(" + ycjrq + ")/"
										+ strings[1].replaceAll("·", "-"));*/
						dt.wgetpic(imgFiles[i], imgFileNm.replaceAll("/", "-").replaceAll(":", "-"),
								"河北青年报/河北青年报(" + ycjrq + ")/"
										+ strings[1].replaceAll("·", "-"));
					}
				}
				db.execInsertSql("update detaillist set createdone=1 where id="
						+ strings[0]);
			}

			logger.info("---河北青年报----开始生成pdf图片......");

			List<String[]> bmPdfList = db.getBanMianPdfList(ycjrq, nspName);
			for (String[] strings : bmPdfList) {
				try {
					// 保存版面的所有PDF图片
					/*String dirPath = imgPath + "河北青年报/河北青年报(" + ycjrq
							+ ")/版面图片/";*/
					String dirPath = "河北青年报/河北青年报(" + ycjrq + ")/版面图片/";
					dt.wgetpic(strings[1], strings[0].replaceAll("/", "-").replaceAll("・", "·"),
							dirPath);
				} catch (Exception e) {
					logger.info("---生成pdf图片失败----" + e.getMessage());
				}
			}

			logger.info("---河北青年报----生成pdf图片已完成");

			// 如果发布了,就更新报纸的状态
			db.execInsertSql("update cjzt set bmcjzt=1 where cjlx='" + nspName
					+ "' and cjrq='" + ycjrq + "'");

			logger.info("--河北青年报--生成文件已完成");
			logger.info("--河北青年报--全部采集工作已完成");

			// 清空采集数据表
			// 测试时先不删除
			
			 db.execInsertSql("TRUNCATE TABLE `detaillist`");
			 db.execInsertSql("TRUNCATE TABLE `newslist`");
			 db.execInsertSql("TRUNCATE TABLE `banmian`");
			 
			logger.info("--河北青年报--采集数据表已清空");
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
