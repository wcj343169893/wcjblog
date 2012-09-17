package andy;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.ParseException;
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

public class dptoolsForWindows {
	
	static Logger logger = Logger.getLogger(dptoolsForWindows.class);
	static String fileDirPath = "F:/gw/updatesoft/报纸采集/";
	//用这个来表示是否设置 采集新的数据
	static boolean isUpdate = true;//false不更新,true更新或采集
	
	// 取得页面源代码
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
		method.setRequestHeader("Host", "www.fawan.com.cn");
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
	
	public static void getfile(String furl,String newname) throws IOException{   
        HttpClient client = new HttpClient();   
        GetMethod get = new GetMethod(furl);   
        client.executeMethod(get);          		
        newname = replaceChar(newname);
        File storeFile = new File(newname);  
        //删除下载失败的文件
        delDownFailFile(storeFile);
        
        if(!storeFile.exists()){    	
        FileOutputStream output = new FileOutputStream(storeFile);   
        //得到网络资源的字节数组,并写入文件   
        output.write(get.getResponseBody());   
        output.close();   
        }
    }   

	
	public static String getSingleImg(String str, String pattern) {
		Pattern p = Pattern.compile(pattern);
		Matcher m = p.matcher(str);
		String rs = "";
		String imgurlHead = "http://www.fawan.com.cn/res/";
		//先取得所有的图片
		List imgUList = new ArrayList<String>();
		while (m.find()) {			
			rs = m.group(1);
			imgUList.add(imgurlHead + rs);			
		}
		
		//再转换为String的形式
		StringBuffer imgUrls = new StringBuffer();
		for (int i = 0; i < imgUList.size(); i++) {
			String url = (String) imgUList.get(i);
			imgUrls.append(url);
			if(i<imgUList.size()-1){
				imgUrls.append(";");
			}
		}
		return imgUrls.toString();
	}
	
	public static String getSingleContent(String str, String pattern) {
		Pattern p = Pattern.compile(pattern);
		Matcher m = p.matcher(str);
		String rs = "";
		while (m.find()) {

			rs = m.group(1);
		}
		return rs;
	}
	
	public static String getSingleContent(String str, String pattern,int index) {
		Pattern p = Pattern.compile(pattern);
		Matcher m = p.matcher(str);
		String rs = "";
		while (m.find()) {

			rs = m.group(index);
		}
		return rs;
	}
	
	public static List<String> getContent(String str, String pattern) {
		Pattern p = Pattern.compile(pattern);
		Matcher m = p.matcher(str);
		List<String> list = new ArrayList<String>();
		while (m.find()) {

			list.add(m.group(1));
		}
		return list;
	}
	
	//取得法制晚报页面跳转url
	public static String getIndexGoto(String content){
		String p1 = "CONTENT=\"0; URL=(.+?)\"";
		String rs = getSingleContent(content,p1);
		
		return rs;
	}
	
	//取得法制晚报版面文字及url
	public static List<String[]> getBanMianList(String content){
		//String pattern = "<a id=pageLink href=(.+?) class=mulu>(.+?)</a>";		
		String pattern = "href=(\\S+)[^>]*>([^>]+)</a></td>\\s*<td width=\"16\"><a\\s*href=([^>]+)><img[^>]+?src=\"([^\"]+)";
		Pattern p = Pattern.compile(pattern);
		Matcher m = p.matcher(content);
		List<String[]> list = new ArrayList<String[]>();
		while (m.find()) {
            String tmp[] = new String[4];
            
            tmp[0] = m.group(1);
            tmp[1] = m.group(2);
            String imgName = m.group(3).replaceAll("../../../page/", "page/");
            tmp[2] = "http://www.fawan.com.cn/"+ imgName;
            tmp[3] = m.group(4);
            
			list.add(tmp);
		}
		return list;
	} 
	
	//取得法制晚报每个版面新闻列表
	public static List<String[]> getNewsList(String content){
		String pattern = "<a href=content_(.+?).htm class=mulu><div id=mp(.+?) style=\"display:inline\">(.+?)</div></a>";
		Pattern p = Pattern.compile(pattern);
		Matcher m = p.matcher(content);
		List<String[]> list = new ArrayList<String[]>();
		while (m.find()) {
            String tmp[] = new String[2];
            
            tmp[0] = m.group(1);
            tmp[1] = m.group(3);
            
			list.add(tmp);
		}
		return list;
	} 
	
	//判断法制晚报详细页是否存在图片
	public static boolean haspic(String content){
		
		boolean rs = false;
		if(content.indexOf("../../../res/")>=0){
			rs = true;
		}
		return rs;
	}
	
	//取得法制晚报详细页图片
	public static String getPic(String content){
		String p1 = "<IMG src=\"../../../res/(.+?)\">";
		String rs = getSingleImg(content,p1);
		
		return rs;
	}
	
	//解析采集日期
	public static String cjrq(String content){

		String[] tmp = content.split("/");
		 
		 return tmp[1]+"/"+tmp[2];
	}
	
	/////////////////////////////////////////////////////////////////////////
	//发送linux命令
	public static void sendLinuxCMD(String cmd2) throws IOException {

		InputStream ins = null;
		
		try {
			Process process = Runtime.getRuntime().exec(cmd2);
			ins = process.getInputStream(); // cmd 的信息

			BufferedReader reader = new BufferedReader(new InputStreamReader(
					ins));
			String line = null;
			while ((line = reader.readLine()) != null) {
				System.out.println(line); // 输出
			}

			int exitValue = process.waitFor();
			System.out.println("返回值：" + exitValue);
			
			process.getOutputStream().close(); // 不要忘记了一定要关

		} catch (Exception e) {

			e.printStackTrace();
		}
	}
	
	//写文件
	public static void writefile(String filename,String content,String child1,String child2,String child3,String id, String ycjrq) throws Exception {
			
		//String path = "/usr/local/webserver/nginx/html/ewj/gw/updatesoft/报纸采集/";
			    
		// 取得当前日期
		Date date = getDateParseYYYY_MM_DD(ycjrq);
		
	    
	    //先判断当天是否为要加班的日期
		String otDays = Appconfig.getValue("otDay");
		String holidays = Appconfig.getValue("holiday");
		//如果不是加班的日期并且为周末或是节假日
		if(!otDays.contains(ycjrq) && (holidays.contains(ycjrq) || dateIsWeekEnd(date))){
			fileDirPath = "F:/gw/updatesoft/报纸采集/我的微盘/hh/"; 
		}
		
		String path = fileDirPath;
		path = path+child1+"/"+child2+"/"+child3.replaceAll("·", "-").replaceAll(" ", "-");
		String fname = path+"/"+filename+".txt";
		//System.out.println(fname);
		/*if(!new File(path).exists()){
			String cmd = "mkdir -p "+path;
			sendLinuxCMD(cmd);//建立目录
		}*/
		
		File file = new File(replaceChar(path));
		if (!file.exists()) {
			file.mkdirs();
		}
		fname = replaceChar(fname);
		File f = new File(fname);	
		 //删除下载失败的文件
        delDownFailFile(f);
        
		if(!f.exists()){			
			//创建文件
			logger.info("创建文件路径 ["+id+"]:"+ f.getAbsolutePath());
			f.createNewFile();
			logger.info("创建文件["+id+"]:"+fname);
			//写内容到文件
			//BufferedWriter utput = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(f), "GB2312"));
			BufferedWriter utput = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(f), "UTF-8"));
		    utput.write(content.replaceAll("(\\s)*$", ""));
		    utput.close();
		}
	}

	static String replaceChar(String fname) {
		String newFileNm = "";
		
		try {
			 newFileNm = fname.replace("?", "").replace("“", "\'").replace("”", "\'").replace("\"", "\'");
			 
		} catch (Exception e) {
			newFileNm = fname;
		}
			
		return newFileNm;
	}
	
	//采集图片
	public static void wgetpic(String picurl,String newname,String topath) throws IOException{
		String[] a = picurl.split("/");
		String realpicname = a[a.length-1];//下载下来后的图片名称
		String[] b = realpicname.split("\\.");
		String imghz = b[1];//图片后缀名
		if(imghz.equalsIgnoreCase("jp")){
			imghz = "jpg";
		}
		String mvnewname = newname+"."+imghz;
		//sendLinuxCMD("wget "+picurl);//下载图片
		//sendLinuxCMD("mv "+realpicname+" "+mvnewname);//修改图片名
		//sendLinuxCMD("mv "+mvnewname+" "+topath);//转移图片
		
		String headPath = fileDirPath + topath.replaceAll(" ", "-");
		File file = new File(headPath);
		if (!file.exists()) {
			file.mkdirs();
		}
		getfile(picurl,headPath +"/"+mvnewname.replace("/", "-"));
		logger.info("图片:"+picurl+"已下载到 "+topath);
	}
	////////////////////////////////////////////////////////////////////////
	
	//采集法制晚报
	public static void getFzwb(String rq) throws Exception{
		
		String url = "http://www.fawan.com.cn";
		
		String content = getpage(url);
		String gourl = getIndexGoto(content);
		String ycjrq = cjrq(gourl);//要跳转的日期
		if(!"".equals(rq)){
			ycjrq = rq;
		}
		
		// 取得当前日期
		 String nowDay = getTdate();
		if(nowDay != null && !"".equals(nowDay) && ycjrq != null && !"".equals(ycjrq)){
			if(!ycjrq.equals(nowDay)){
				logger.info(ycjrq + "该日期法制晚报已采集过,今天"+nowDay+"的官网上还没有发布内容!");
				System.exit(0);
			}
		}
		String qz = url+"/html/"+ycjrq;//url前缀		
		logger.info("当前法制晚报采集日期为-----" + ycjrq);	
		
		DbBean db = new DbBean();
		if(db.ifGetThisDay(ycjrq)){//如果采集过就停止采集
			logger.info(ycjrq + "该日期法制晚报已采集过");
			System.exit(0);
			
		}
		
		//进入某期内容
		
		//查看是否采集某期索引
		//String url1 = "";
		if(!db.ifGetThisDayIndex(ycjrq)){
			String dateurl = url+"/"+gourl;
			//String dateurl = qz+"/node_2.htm";
			String index1 = getpage(dateurl);
			List<String[]> BanMianList = getBanMianList(index1);
			
			if(BanMianList.size()==0){
				dateurl = qz+"/node_2.htm";
				index1 = getpage(dateurl);
				BanMianList = getBanMianList(index1);
			}
			
			
			if(BanMianList.size()==0){
				logger.info("当日---法制晚报----信息还没发布");
				System.exit(0);
			}
			//url1 = dateurl.substring(0, dateurl.lastIndexOf("/"));
			logger.info("开始采集---法制晚报----"+ycjrq+"的版面......");
			for (String[] strings : BanMianList) {
				String url2 = qz+"/"+strings[0].replaceFirst("./", "");
				String sql = "INSERT INTO banmian (content,url,lx,bmdate,pdf_url) VALUES ('"+strings[1]+"', '"+url2+"', 'fzwb', '"+ycjrq+"','"+strings[2]+"')";
				
				db.execInsertSql(sql);
				//logger.info(sql);
			}
			logger.info("采集---法制晚报----"+ycjrq+"的版面结束");
			db.execInsertSql("INSERT INTO cjzt (cjrq,indexcjzt,cjlx) VALUES ('"+ycjrq+"', '1', 'fzwb')");
			logger.info("更新索引采集状态");
		}
		else{
			logger.info("该日索引已采集过");
		}
		
		if(isUpdate){
			//采集新闻标题
			List<String[]> BanMianList = db.getWaitingBanMianList(ycjrq);
			//int total = BanMianList.size();
			String sql = "";
			String index2 = "";
			List<String[]> tmp;
			for (String[] strings : BanMianList) {
				sql = "delete from newslist where bmid="+strings[0];
				db.execInsertSql(sql);//既然没有标志该版面为采集完状态，肯定有未采集完成的新闻，先del在重新采集该版面
				logger.info(sql);
				logger.info("开始采集---法制晚报----版面:"+strings[1]+" url:"+strings[2]);
				index2 = getpage(strings[2]);
				tmp = getNewsList(index2);
				for (String s[] : tmp) {
					sql = "INSERT INTO newslist (bmid ,content ,url) VALUES ('"+strings[0]+"', '"+s[1].replaceAll("<BR/>", "").replaceAll("/", "-").replace("?", "").trim()+"', '"+qz+"/content_"+s[0]+".htm')";
					db.execInsertSql(sql);
					logger.info("采集---法制晚报----标题:"+s[1]);
				}
				sql = "update banmian set getdone=1 where id="+strings[0];
				db.execInsertSql(sql);
				logger.info(sql);
				logger.info("---法制晚报----版面:"+strings[1]+" url:"+strings[2]+"采集完成");
			}
		}
		
		
		//采集新闻详细页面
		logger.info("开始采集---法制晚报----详细内容");
		List<String[]> NewsList = db.getWaitingNewsList(ycjrq);
		String index3 = "";
		String img = "";
		for (String[] strings : NewsList) {
			index3 = getpage(strings[2]);
			img = "";
			if(index3.indexOf("res/")>=0){
				//img = imgurl+getPic(index3);				
				img = getPic(index3);
			}
		String	sql = "INSERT INTO detaillist (newsid ,content ,imgurl) VALUES ('"+strings[0]+"', '"+getSingleContent(index3,"<founder-content>(.+?)</founder-content>")+"', '"+img+"')";
			db.execInsertSql(sql);
			db.execInsertSql("update newslist set getdone=1 where id = "+strings[0]);
			logger.info("采集  "+strings[1]+" "+strings[2]+" "+img);
		}
		logger.info("---法制晚报----详细内容采集完成");
		logger.info("所有---法制晚报----采集工作已经完成");
		
		//开始生成文件
		logger.info("---法制晚报----开始生成txt和图片文件......");
		List<String[]> ContentList = db.getNewsByDate(ycjrq);
		for (String[] strings : ContentList) {
			String path = strings[2].replaceAll("/", "-").replaceAll("<", "小于").replaceAll(":", "-").replaceAll("/*", "");
			writefile(path, strings[3].replaceAll("&nbsp;", " ").replaceAll("<P>", "").replaceAll("</p>", "\r\n").replaceAll("<BR/>", "\r\n").replaceAll("<br/>", "\r\n").replaceAll("<BR>", "\r\n").replaceAll("<br>", "\r\n\r\n").replaceAll("<p>", "").replaceAll("</P>", ""), "法制晚报", "法制晚报("+ycjrq.replaceAll("/", "-")+")", strings[1].trim(), strings[0],ycjrq.replaceAll("/", "-"));//生成txt
			if(!"".equals(strings[4])){			
				
				if(strings[4] != null && !"".equals(strings[4]) && !"null".equals(strings[4])){
					//保存1或多个文件
					String[] imgFiles = strings[4].split(";");
					for (int i = 0; i < imgFiles.length; i++) {
						String imgFileNm = "";
						if(i < imgFiles.length -1){
							imgFileNm = strings[2]+"_"+(i+1);
						} else {
							imgFileNm = strings[2];
						}
						//String dirPath = fileDirPath + "/法制晚报/法制晚报("+ycjrq.replaceAll("/", "-")+")/";
						String dirPath = "/法制晚报/法制晚报("+ycjrq.replaceAll("/", "-")+")/";
						try {
							
							wgetpic(imgFiles[i], imgFileNm.replaceAll("/", "-").replaceAll("<", "小于").replaceAll(":", "-"),dirPath + strings[1].replaceAll("·", "-"));
						} catch (Exception e) {
							logger.info(e.getMessage());
						}
						//swgetpic(strings[4], strings[2], "F:/gw/updatesoft/报纸采集/法制晚报/法制晚报("+ycjrq.replaceAll("/", "-")+")/"+strings[1].trim().replaceAll("·", "-"));
					}
				}				
				
			}								  
			db.execInsertSql("update detaillist set createdone=1 where id="+strings[0]);
		}
		logger.info("---法制晚报----生成txt和图片文件已完成");
		
		
		logger.info("---法制晚报----开始生成pdf图片......");
		
		List<String[]> bmPdfList = db.getBanMianPdfList(ycjrq);
		for (String[] strings : bmPdfList) {			
			//保存版面的所有PDF图片
			//String dirPath = fileDirPath +"/法制晚报/法制晚报("+ycjrq.replaceAll("/", "-")+")/版面图片/";
			String dirPath = "/法制晚报/法制晚报("+ycjrq.replaceAll("/", "-")+")/版面图片/";
			if(strings[1].substring(strings[1].lastIndexOf(".")+1,strings[1].length()).equalsIgnoreCase("pdf")){
				wgetpic(strings[1], strings[0].replaceAll("/", "-"),dirPath);			
			}
		}
		
		logger.info("---法制晚报----生成pdf图片已完成");
		
		db.execInsertSql("update cjzt set bmcjzt=1 where cjlx='fzwb' and cjrq='"+ycjrq+"'");
		logger.info("---法制晚报----生成文件已完成");
		logger.info("---法制晚报----全部采集工作已完成");
		
		//清空采集数据表
		db.execInsertSql("TRUNCATE TABLE `detaillist`");
		db.execInsertSql("TRUNCATE TABLE `newslist`");
		db.execInsertSql("TRUNCATE TABLE `banmian`");
		logger.info("---法制晚报----采集数据表已清空");
	}	
	

	//删除下载失败的文件
	private static void delDownFailFile(File storeFile) throws IOException,
			FileNotFoundException {
		
        if (storeFile.exists()) {	
        	//该文件大小为0时,就说明该文件没下载成功,必须先删除    
    		FileInputStream fileIns = new FileInputStream(storeFile);
    		int fileLength = fileIns.available();
    		fileIns.close();
    		if( fileLength < 1024){
    			//删除文件
            	boolean d = storeFile.delete();
            	if(d){        		
            		logger.info("删除原来文件成功");
            	} else {
            		logger.info("删除原来文件失败");
            	}
    		}
			
		}
	}  
	
	
	
	/**
	 * 字符串转换成日期
	 * */
	public static Date getDateParseYYYY_MM_DD(String date) {
		date = date.replace("/", "-");
		System.out.println("date2 = "+date);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if (date == null || "".equals(date)) {
			return null;
		}
		try {
			return sdf.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}	
	
	/**
	 * 判断是否为周末
	 * @param date
	 * @return
	 */
	public static boolean dateIsWeekEnd(Date date) {
		boolean result = false;

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int weekday = calendar.get(Calendar.DAY_OF_WEEK);

		if (weekday == Calendar.SATURDAY || weekday == Calendar.SUNDAY) {
			result = true;
		}
		return result;
	}
	
	public static String getTdate() {
		return new SimpleDateFormat("yyyy-MM/dd").format(new Date());
	}
	
	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {

		System.setProperty("org.apache.commons.logging.Log",
				"org.apache.commons.logging.impl.SimpleLog");
		System.setProperty("org.apache.commons.logging.simplelog.showdatetime",
				"true");
		System.setProperty("org.apache.commons.logging"
				+ ".simplelog.log.org.apache.commons.httpclient", "error");

		//String rq = "2012-01/12";
		String rq = "";

		//手动输入日期
		if (args.length > 0) {
			if (!"".equals(args[0]))
				rq = args[0];
		}
		//手动输入日期		
		getFzwb(rq);		
	}

}
