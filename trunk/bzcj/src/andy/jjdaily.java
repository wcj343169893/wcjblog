package andy;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.log4j.Logger;

public class jjdaily {

	static Logger logger = Logger.getLogger(jjdaily.class);
	
	public static void main(String[] args) throws Exception {
		
		String rq = "";
		//cjjj(rq);
		
		//手动输入日期
		if(args.length>0){
			if(!"".equals(args[0]))
				rq = args[0];
		}
		//手动输入日期
		
		cj(rq);
		
	}
	
	public static String getTdate(){
		return new SimpleDateFormat("yyyyMMdd").format(new Date());
	}
	
	// 取得页面源代码
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
	
	public static List<String[]> getBanMianList(String content){
		String pattern = "<a href='(.+?)' style='color:#000000; font-size:12px; text-decoration:none'>(.+?)</a>";
		Pattern p = Pattern.compile(pattern);
		Matcher m = p.matcher(content);
		List<String[]> list = new ArrayList<String[]>();
		while (m.find()) {
            String tmp[] = new String[2];
            
            tmp[0] = m.group(1);
            tmp[1] = m.group(2);
            
			list.add(tmp);
		}
		return list;
	} 
	

	public static List<String[]> getNewsList(String content){
		String pattern = "<a title='(.+?)' href=\"(.+?)\">(.+?)</a>";
		Pattern p = Pattern.compile(pattern);
		Matcher m = p.matcher(content);
		List<String[]> list = new ArrayList<String[]>();
		while (m.find()) {
            String tmp[] = new String[2];
            
            tmp[0] = m.group(2);
            tmp[1] = m.group(3);
            
			list.add(tmp);
		}
		return list;
	} 
	
	public static void cjjj(String rq){
		
		dptools dt = new dptools();
		/*String index = getpage("http://epaper.nbd.com.cn/shtml/mrjjxw/20101110/index.shtml");
		String title = dt.getSingleContent(index, "<title>(.+?)</title>");//取得首页标题
		List<String[]> BanMianList = getBanMianList(index);//取得版面列表
		for (String[] strings : BanMianList) {
			System.out.println(strings[0]+"        "+strings[1]);
		}*/
		
		/*String index1 = getpage("http://epaper.nbd.com.cn/shtml/mrjjxw/20101110/v11.shtml");
		List<String[]> NewsList = getNewsList(index1);//取得新闻列表
		for (String[] strings : NewsList) {
			System.out.println(strings[0]+"        "+strings[1]);
		}*/
		
		/*String index2 = getpage("http://epaper.nbd.com.cn/shtml/mrjjxw/20101110/2046126.shtml");
		String img = "";
		String content = "";//新闻正文
		if(index2.indexOf("class=\"auto-width\"")>=0){//如果有图片取得图片url
			img = dt.getSingleContent(index2, "<img class=\"auto-width\" src=\"(.+?)\"  />");
			content = dt.getSingleContent(index2, "<div class=\"f-14 height-25\" style=\"width:95%\" align=\"left\">(.+?)</div><br />(.+?)</div>",2);
		}
		else{
			content = dt.getSingleContent(index2, "<div class=\"f-14 height-25\" style=\"width:95%\" align=\"left\">(.+?)</div>");
		}*/

	}
	
	public static void cj(String rq) throws Exception{
		//取得当前日期
		String ycjrq = getTdate();
		if(!"".equals(rq)){
			ycjrq = rq;
		}
		//查看该日期是否采集
		DbBean1 db = new DbBean1();
		if(db.ifGetThisDay(ycjrq)){
			logger.info("该日期已采集过");
			System.exit(0);
			
		}
		
		dptools dt = new dptools();
		String url = "http://epaper.nbd.com.cn/shtml/mrjjxw/";
		String domain = "http://epaper.nbd.com.cn";
		
		//打开首页
		String gourl = url+ycjrq;
		String index = getpage(gourl);
		logger.info("-----------------"+ index +"-----------------");
		String title = dt.getSingleContent(index, "<title>(.+?)</title>");//取得首页标题
		if(title.indexOf(ycjrq)==-1){//判断当日的内容是否发行
			logger.info("该日期还没有发行");
			System.exit(0);
			
		}
		
		//采集版面列表
		if(!db.ifGetThisDayIndex(ycjrq)){
			logger.info("开始采集"+ycjrq+"的版面......");
			List<String[]> BanMianList = getBanMianList(index);
			for (String[] strings : BanMianList) {
				String url2 = gourl+"/"+strings[0];
				String sql = "INSERT INTO banmian (content,url,lx,bmdate) VALUES ('"+strings[1].replaceAll(" ", "")+"', '"+url2+"', 'mrjj', '"+ycjrq+"')";
				db.execInsertSql(sql);
			}
			logger.info("采集"+ycjrq+"的版面结束");
			db.execInsertSql("INSERT INTO cjzt (cjrq,indexcjzt,cjlx) VALUES ('"+ycjrq+"', '1', 'mrjj')");
			logger.info("更新索引采集状态");
		}
		
		//采集新闻列表
		List<String[]> BanMianList = db.getWaitingBanMianList(ycjrq);
		String sql = "";
		String index2 = "";
		List<String[]> tmp;
		for (String[] strings : BanMianList) {
			sql = "delete from newslist where bmid="+strings[0];
			db.execInsertSql(sql);//既然没有标志该版面为采集完状态，肯定有未采集完成的新闻，先del在重新采集该版面
			logger.info(sql);
			logger.info("开始采集版面:"+strings[1]+" url:"+strings[2]);
			index2 = getpage(strings[2]);
			tmp = getNewsList(index2);
			for (String s[] : tmp) {
				sql = "INSERT INTO newslist (bmid ,content ,url) VALUES ('"+strings[0]+"', '"+s[1]+"', '"+gourl+"/"+s[0]+"')";
				db.execInsertSql(sql);
				logger.info("采集标题:"+s[1]);
			}
			sql = "update banmian set getdone=1 where id="+strings[0];
			db.execInsertSql(sql);
			logger.info(sql);
			logger.info("版面:"+strings[1]+" url:"+strings[2]+"采集完成");
		}
		
		//采集新闻详细页面
		logger.info("开始采集详细内容");
		List<String[]> NewsList = db.getWaitingNewsList(ycjrq);
		String index3 = "";
		String img = "";
		String content = "";
		for (String[] strings : NewsList) {
			index3 = getpage(strings[2]);
			img = "";
			content = "";
			if(index3.indexOf("class=\"auto-width\"")>=0){
				img = domain+dt.getSingleContent(index3, "<img class=\"auto-width\" src=\"(.+?)\"  />");
				content = dt.getSingleContent(index3, "<div class=\"f-14 height-25\" style=\"width:95%\" align=\"left\">(.+?)</div><br />(.+?)</div>",2);
			}
			else{
				content = dt.getSingleContent(index3, "<div class=\"f-14 height-25\" style=\"width:95%\" align=\"left\">(.+?)</div>");
			}
			sql = "INSERT INTO detaillist (newsid ,content ,imgurl) VALUES ('"+strings[0]+"', '"+content+"', '"+img+"')";
			db.execInsertSql(sql);
			db.execInsertSql("update newslist set getdone=1 where id = "+strings[0]);
			logger.info("采集  "+strings[1]+" "+strings[2]+" "+img);
		}
		logger.info("详细内容采集完成");
		logger.info("所有采集工作已经完成");
		
		//开始生成文件
		logger.info("开始生成文件......");
		List<String[]> ContentList = db.getNewsByDate(ycjrq);
		for (String[] strings : ContentList) {
			if("".equals(strings[3])) continue;
			dt.writefile(strings[2].replaceAll("/", "-"), strings[3].replaceAll("&nbsp;", " ").replaceAll("<P>", "").replaceAll("</p>", "\r\n").replaceAll("<BR/>", "\r\n").replaceAll("<br/>", "\r\n").replaceAll("<BR>", "\r\n").replaceAll("<br>", "\r\n\r\n").replaceAll("<p>", "").replaceAll("</P>", ""), "每日经济新闻", "每日经济新闻("+ycjrq+")", strings[1], strings[0]);//生成txt
			if(!"".equals(strings[4])){
				dt.wgetpic(strings[4], strings[2], "/usr/local/webserver/nginx/html/ewj/gw/updatesoft/报纸采集/每日经济新闻/每日经济新闻("+ycjrq+")/"+strings[1].replaceAll("·", "-"));
			}
			db.execInsertSql("update detaillist set createdone=1 where id="+strings[0]);
		}
		db.execInsertSql("update cjzt set bmcjzt=1 where cjlx='mrjj' and cjrq='"+ycjrq+"'");
		logger.info("生成文件已完成");
		logger.info("全部采集工作已完成");
		
		//清空采集数据表
		db.execInsertSql("TRUNCATE TABLE `detaillist`");
		db.execInsertSql("TRUNCATE TABLE `newslist`");
		db.execInsertSql("TRUNCATE TABLE `banmian`");
		logger.info("采集数据表已清空");
	}

}
