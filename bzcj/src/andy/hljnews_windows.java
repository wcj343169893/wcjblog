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
 * 河北青年报采集
 * 网址:http://www.hbqnb.com/epaper/epaper_index.asp?epapertype=epaperimages&dateid=2011-6-15
 * 具体第版:http://www.hbqnb.com/epaper/epaper.asp?epapertype=epaper&dateid=2011-6-10&ban=A&banid=1
 */
public class hljnews_windows {

	static Logger logger = Logger.getLogger(hljnews_windows.class);
	final static String nspName = "hljnews";
	//final static String imgPath = "F:/gw/updatesoft/报纸采集/我的微盘/hh/";
	
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

	public static String getTdate() {
		return new SimpleDateFormat("yyyy-MM/dd").format(new Date());
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
		return new SimpleDateFormat("yyyy-MM/dd").format(calendar.getTime());
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
		
		String pattern = "<TD class=\\w+><a href=([^\\s]+) class=\"\\w+\" id=pageLink>([^<]+)<\\/a><\\/TD>";

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

		String pattern = "<a\\s+href=(\\S+)\\s[^>]*><IMG[^>]+alt=\"PDF";

		Pattern p = Pattern.compile(pattern);

		Matcher m = p.matcher(content);

		String tmpImg = "";

		while (m.find()) {

			// Group 1：图片链接
		//tmpImg = "http://epaper.hljnews.cn/shb/" + m.group(1);
		tmpImg = m.group(1);
		}
		return tmpImg;

	}

	public static List<String[]> getNewsList(String content) {

		// String pattern = "(?s)class=\"epapernewstitle\" target=\"_blank\"
		// href=\"([^\"]+)\".*?title=\"([^\"]+)";
		String pattern = "<a href=(content[^>]+)><div[^>]+>([^<]+)</div>";
		Pattern p = Pattern.compile(pattern);
		Matcher m = p.matcher(content);
		List<String[]> list = new ArrayList<String[]>();
		while (m.find()) {
			String tmp[] = new String[2];

			tmp[0] = m.group(1);// url
			tmp[1] = m.group(2);// title		
			list.add(tmp);
		}
		return list;
	}

	public static String[] getNewsDetailList(String content, String titleNm) {

		StringBuffer txt_content = new StringBuffer();
		//a:取得上面的标题		  
		txt_content.append(sushibao_title(content));
		//System.out.println(txt_content.toString());
		//b:取得图片及其下面的标题
		;
		// 二、提取内容中的图片及上下文
		List<String[]> list = new_titleAndImg(content);
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
		txt_content.append(getContent.toString());
		//System.out.println(txt_content.toString());
		
		//c:取得下面的内容
		txt_content.append(sushibao_bottonTitle(content));
		
		String retmp[] = new String[2];
		retmp[1] = getUrl.toString();
		// java如何利用正则表达式去掉文本中的HTML标签			
		//retmp[0] = txt_content.toString().replaceAll("<[^>]*>","");	
		retmp[0] = txt_content.toString();
		//System.out.println(retmp[0]);
		return retmp;
	}

	//取得文章内容最上面的几个标题
	private static String sushibao_title(String content) {
				
		//分开采集
		String pattern = "<span style=.*?>([^<]+)</span><br>\\s<strong style=.*?>([^<]+)</strong><br>\\s<span style=.*?>([^<]*)</span><br>";
				
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
	//再采集文章标题及其url
	private static List<String[]> new_titleAndImg(String content) {
		
		//先采集pdf
		//分开采集		
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
	
	
	//再采集文章底部内容
	private static String sushibao_bottonTitle(String content) {
		//有多张图片的
		
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
		
		return bottonCont;

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
				logger.info("---" + ycjrq + " ---- 该日期--黑龙江新闻报-数字报刊--已采集过---------");
				System.exit(0);

			}

			dptoolsForWindows dt = new dptoolsForWindows();
			//http://epaper.hljnews.cn/shb/ + html/ + 2011-08/26/ + node_22.htm
			String homeUrl = "http://epaper.hljnews.cn/shb/";
			String bmUrlDomain = homeUrl + "html/" + ycjrq;
			// String domain = "http://epaper.nbd.com.cn";

			// 打开首页
			String gourl = homeUrl + "html/" + ycjrq + "/node_22.htm";
			String index = getpage(gourl);
			/*
			 * String title = dt.getSingleContent(index, "<title>(.+?)</title>");//取得首页标题
			 * if(title.indexOf(ycjrq)==-1){//判断当日的内容是否发行
			 * logger.info("该日期还没有发行"); System.exit(0);
			 *  }
			 */

			// 采集版面列表
			if (!db.ifGetThisDayIndex(ycjrq, nspName)) {
				logger.info("开始采集--黑龙江新闻报-数字报刊--" + ycjrq + "的版面......");
				List<String[]> BanMianList = getBanMianList(index);
				// 用来标志今天的报纸是否发布
				if (BanMianList.isEmpty()
						|| (BanMianList != null && BanMianList.size() == 0)) {
					logger.info("----------------" + ycjrq
							+ "---该日期--黑龙江新闻报-数字报刊--尚未发布---------");
					System.exit(0);
				}
				for (String[] strings : BanMianList) {
					
					String url2 = bmUrlDomain + "/" + strings[0];

					StringBuffer sql = new StringBuffer();
					sql
							.append(
									"INSERT INTO banmian (content,url,lx,bmdate) VALUES (")
							.append("'" + strings[1].replaceAll(" ", ""))
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
				logger.info("--黑龙江新闻报-数字报刊--开始采集版面:" + strings[1] + " url:"
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
							+ strings[0] + "', '" + s[1].replaceAll("<BR/>", "").replaceAll("/", "-").replace("?", "").trim() + "', '" + bmUrlDomain +"/"+ s[0] + "')";
					db.execInsertSql(sql);
					logger.info("采集标题:" + s[1]);
				}
				sql = "update banmian set getdone=1,pdf_url='" + bmImgUrl.replace("../../../", homeUrl)
						+ "' where id=" + strings[0];
				db.execInsertSql(sql);
				logger.info(sql);
				logger.info("版面:" + strings[1] + " url:" + strings[2] + "采集完成");
			}

			// 采集新闻详细页面
			logger.info("--黑龙江新闻报-数字报刊--开始采集详细内容");
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
					/*content = tmpDet[0].replaceAll("<[^>]*>", "").replaceAll(
							"(\n|\r\n)\\s+", "$1").replaceAll("&nbsp;", " ")
							.replaceAll("null", "").replaceAll("/p>", "");*/
					content = tmpDet[0].replaceAll("&nbsp;", "").replaceAll("<P>", "").replaceAll("</p>",
					 "\r\n").replaceAll("</P>","\r\n").replaceAll("<BR/>", "\r\n").replaceAll("<br/>",
					 "\r\n").replaceAll("<BR>", "\r\n").replaceAll("<br>",
					 "\r\n\r\n").replaceAll("<p>", "").replaceAll("</P>",
					 "").replaceAll("null","\r\n").replaceAll("<[^>]*>","");
				}

				img = tmpDet[1];// 取得图片url
				
				sql = "INSERT INTO detaillist (newsid ,content ,imgurl) VALUES ('"
						+ strings[0] + "', '" + content + "', '" + img.replace("../../../", homeUrl) + "')";
				db.execInsertSql(sql);
				db.execInsertSql("update newslist set getdone=1 where id = "
						+ strings[0]);
				logger.info("采集 ------------ " + strings[1] + " --------"
						+ strings[2] + " ----------" + img);
			}
			logger.info("--黑龙江新闻报-数字报刊--详细内容采集完成");
			logger.info("所有采集工作已经完成");

			// 开始生成文件
			logger.info("--黑龙江新闻报-数字报刊--开始生成文件......");
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
								"黑龙江新闻报", "黑龙江新闻报(" + ycjrq.replaceAll("/", "-") + ")", dt.replaceChar(strings[1].replaceAll("·", "-")),
								strings[0],ycjrq.replaceAll("/", "-"));// 生成txt
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
								 "黑龙江新闻报/黑龙江新闻报(" + ycjrq.replaceAll("/", "-") + ")/"
										+ dt.replaceChar(strings[1]).replaceAll("·", "-"));
					}
				}
				db.execInsertSql("update detaillist set createdone=1 where id="
						+ strings[0]);
			}

			logger.info("---黑龙江新闻报-数字报刊----开始生成pdf图片......");

			List<String[]> bmPdfList = db.getBanMianPdfList(ycjrq, nspName);
			for (String[] strings : bmPdfList) {
				try {
					// 保存版面的所有PDF图片
					String dirPath = "黑龙江新闻报/黑龙江新闻报(" + ycjrq.replaceAll("/", "-")
							+ ")/版面图片/";
					dt.wgetpic(strings[1], strings[0].replaceAll("/", "-"),
							dirPath);
				} catch (Exception e) {
					logger.info("---生成pdf图片失败----" + e.getMessage());
				}
			}

			logger.info("---黑龙江新闻报-数字报刊----生成pdf图片已完成");

			// 如果发布了,就更新报纸的状态
			db.execInsertSql("update cjzt set bmcjzt=1 where cjlx='" + nspName
					+ "' and cjrq='" + ycjrq + "'");

			logger.info("--黑龙江新闻报-数字报刊--生成文件已完成");
			logger.info("--黑龙江新闻报-数字报刊--全部采集工作已完成");

			// 清空采集数据表
			// 测试时先不删除
			
			 db.execInsertSql("TRUNCATE TABLE `detaillist`");
			 db.execInsertSql("TRUNCATE TABLE `newslist`");
			 db.execInsertSql("TRUNCATE TABLE `banmian`");
			 
			logger.info("--黑龙江新闻报-数字报刊--采集数据表已清空");
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
