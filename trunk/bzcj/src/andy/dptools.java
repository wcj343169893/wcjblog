package andy;

import java.io.BufferedInputStream;
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
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.log4j.Logger;

public class dptools {
	
	static Logger logger = Logger.getLogger(dptools.class);
	final static String fileDirPath = "/usr/local/webserver/nginx/html/ewj/gw/updatesoft/报纸采集/";
	
	// 取得页面源代码
	public static String getpage(String address) {

		StringBuffer html = new StringBuffer();
		String result = null;

		URL url = null;

		HttpURLConnection httpConn = null;

		InputStream in = null;

		FileOutputStream out = null;
		
		try {

			url = new URL(address);

			

			httpConn = (HttpURLConnection) url.openConnection();

			HttpURLConnection.setFollowRedirects(true);
			
			HttpURLConnection.setFollowRedirects(true);

			httpConn.setRequestMethod("GET");

			httpConn.setRequestProperty(
					"User-Agent",
					"Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1; GTB5; .NET CLR 2.0.50727; CIBA)");
			
			in = new BufferedInputStream(httpConn
					.getInputStream());

			try {

				String inputLine;
				
				byte[] buf = new byte[4096];
				int bytesRead = 0;

				while (bytesRead >= 0) {

					inputLine = new String(buf, 0, bytesRead, "ISO-8859-1");

					html.append(inputLine);

					bytesRead = in.read(buf);

					inputLine = null;

				}

				buf = null;

			} finally {
				out.close();
				in.close();
				httpConn.disconnect();
			}

			result = new String(html.toString().trim().getBytes("ISO-8859-1"),
					"gb2312").toLowerCase();

		} catch (Exception e) {

			e.printStackTrace();

			return null;

		}

		html = null;

		return result;

	}
	
	public static void getfile(String furl,String newname) throws IOException{   
        HttpClient client = new HttpClient();   
        GetMethod get = new GetMethod(furl);   
        client.executeMethod(get);   
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
		//String rs = getSingleContent(content,p1);
		String rs = getSingleImg(content,p1);
		return rs;
	}
	
	//解析采集日期
	public static String cjrq(String content){

		logger.info("-------------111-------------");
		
		logger.info("-------------content = " + content);
		
		String[] tmp = content.split("/");
		logger.info("------------2222---------------");
		
		StringBuffer retDate = new StringBuffer();
		
		logger.info("------------333---------------");
		
		for(int i=0; i< tmp.length; i++){
			logger.info("------------tmp["+i+"]=" + tmp[i]);
		}
		
		
		logger.info("------------tmp[0]=" + tmp[0]);
		logger.info("------------tmp[1]=" + tmp[1]);
		
		 if(tmp != null && tmp.length > 0 && tmp[1] != null && !"".equals(tmp[1])){
			 retDate.append(tmp[1]);
			 retDate.append("/");
		 }
		 
		 logger.info("------------444---------------");
		 
		 if(tmp != null && tmp.length > 1  && tmp[2] != null && !"".equals(tmp[2])){
			 retDate.append(tmp[2]);			
		 }
		 
		 logger.info("------------555---------------");
		 
		 return retDate.toString();
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
	public static void writefile(String filename,String content,String child1,String child2,String child3,String id) throws Exception {
			
		String path = fileDirPath;
		path = path+child1+"/"+child2+"/"+child3.replaceAll("·", "-").replaceAll(" ", "-");
		String fname = path+"/"+filename+".txt";
		//System.out.println(fname);
		if(!new File(path).exists()){
			String cmd = "mkdir -p "+path;
			sendLinuxCMD(cmd);//建立目录
		}
		
		File f = new File(fname);
		 //删除下载失败的文件
        delDownFailFile(f);
        
		if(!f.exists()){
			//创建文件
			f.createNewFile();
			logger.info("创建文件["+id+"]:"+fname);
			//写内容到文件
			//BufferedWriter utput = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(f), "GB2312"));
			BufferedWriter utput = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(f), "UTF-8"));
		    utput.write(content.replaceAll("(\\s)*$", ""));
		    utput.close();
		}
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
		String headPath = topath.replaceAll(" ", "-");
		File file = new File(headPath);
		if (!file.exists()) {
			file.mkdirs();
		}
		getfile(picurl,headPath +"/"+mvnewname.replace("/", "-").replaceAll("·", "-"));
		
		logger.info("图片:"+picurl+"已下载到 "+topath);
	}
	////////////////////////////////////////////////////////////////////////
	
	//采集法制晚报
	public static void getFzwb(String rq) throws Exception{

		System.getProperties().setProperty("proxySet", "true");
		// //如果不设置，只要代理IP和代理端口正确,此项不设置也可以

		System.getProperties().setProperty("http.proxyHost", "218.26.204.66");
		System.getProperties().setProperty("http.proxyPort", "8080");
		
		String url = "http://www.fawan.com.cn";
		
		String content = getpage(url);
		String gourl = getIndexGoto(content);
		String ycjrq = cjrq(gourl);//要跳转的日期
		if(!"".equals(rq)){
			ycjrq = rq;
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
			//String dateurl = url+"/"+gourl;
			String dateurl = qz+"/node_2.htm";
			String index1 = getpage(dateurl);
			List<String[]> BanMianList = getBanMianList(index1);
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
				sql = "INSERT INTO newslist (bmid ,content ,url) VALUES ('"+strings[0]+"', '"+s[1].replaceAll("<BR/>", "").replaceAll("/", "-")+"', '"+qz+"/content_"+s[0]+".htm')";
				db.execInsertSql(sql);
				logger.info("采集---法制晚报----标题:"+s[1]);
			}
			sql = "update banmian set getdone=1 where id="+strings[0];
			db.execInsertSql(sql);
			logger.info(sql);
			logger.info("---法制晚报----版面:"+strings[1]+" url:"+strings[2]+"采集完成");
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
			sql = "INSERT INTO detaillist (newsid ,content ,imgurl) VALUES ('"+strings[0]+"', '"+getSingleContent(index3,"<founder-content>(.+?)</founder-content>")+"', '"+img+"')";
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
			writefile(strings[2], strings[3].replaceAll("&nbsp;", " ").replaceAll("<P>", "").replaceAll("</p>", "\r\n").replaceAll("<BR/>", "\r\n").replaceAll("<br/>", "\r\n").replaceAll("<BR>", "\r\n").replaceAll("<br>", "\r\n\r\n").replaceAll("<p>", "").replaceAll("</P>", ""), "法制晚报", "法制晚报("+ycjrq.replaceAll("/", "-")+")", strings[1].trim(), strings[0]);//生成txt
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
						String dirPath = fileDirPath + "/法制晚报/法制晚报("+ycjrq.replaceAll("/", "-")+")/";
						wgetpic(imgFiles[i], imgFileNm.replaceAll("/", "-"),dirPath + strings[1].replaceAll("·", "-"));
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
			String dirPath = fileDirPath +"/法制晚报/法制晚报("+ycjrq.replaceAll("/", "-")+")/版面图片/";
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
	
	public static void main(String[] args) throws Exception {
		//String content = getpage("http://www.fawan.com.cn");//1采集首页
		//String gourl = getIndexGoto(content);//2解析跳转到某日期的地址
		//3判断是否已经采集该日期
		
		/*String index1 = getpage("http://www.fawan.com.cn/html/2010-11/05/node_2.htm");//4采集某日期首页
		List<String[]> tmp = getBanMianList(index1);
		for (String string[] : tmp) {
			System.out.println(string[0]+" "+string[1]);
		}*/
		
		/*String index2 = getpage("http://www.fawan.com.cn/html/2010-11/05/node_26.htm");
		List<String[]> tmp = getNewsList(index2);
		for (String string[] : tmp) {
			System.out.println(string[0]+" "+string[1]);
		}*/
		
		
		//String index3 = getpage("http://www.fawan.com.cn/html/2010-11/08/content_272704.htm");
		//System.out.println(getSingleContent(index3,"<founder-content>(.+?)</founder-content>"));
		//System.out.println(index3.indexOf("res/")>=0);
		
		
		/*String index4 = getpage("http://www.fawan.com.cn/html/2010-11/08/content_272704.htm");
		System.out.println(getPic(index4));*/
		/*String rq = "";
		getFzwb(rq);*/
		getfile("http://www.fawan.com.cn/res/1/1/2010-11/08/A22/res04_attpic_brief.jpg", "c:/图片测试.jpg");
		
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
	    			String cmdMod = "chmod 777 " + storeFile.getAbsolutePath();
	    			sendLinuxCMD(cmdMod);//分配权限
	    			
	    			String cmdDel = "rm -f " + storeFile.getAbsolutePath();
	    			sendLinuxCMD(cmdDel);//删除文件
	    		}
				
			}
		
	}  
}
