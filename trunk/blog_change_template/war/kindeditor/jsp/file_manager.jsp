
<%@page import="com.google.choujone.blog.util.Tools"%>
<%@page import="com.google.choujone.blog.entity.DataFile"%>
<%@page import="com.google.choujone.blog.common.Pages"%>
<%@page import="com.google.choujone.blog.dao.DataFileDao"%><%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*,java.io.*" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="org.json.simple.*" %>
<%

//根目录路径，可以指定绝对路径，比如 /var/www/attached/
String rootPath = pageContext.getServletContext().getRealPath("/") + "attached/";
//out.println(rootPath);
//return;
//根目录URL，可以指定绝对路径，比如 http://www.yoursite.com/attached/
String rootUrl  = request.getContextPath() + "/file/";
//图片扩展名
String[] fileTypes = new String[]{"gif", "jpg", "jpeg", "png", "bmp"};

int p=request.getParameter("p")!= null ? Integer.parseInt(request.getParameter("p").toString()) : 1;
//根据path参数，设置各路径和URL
String path = request.getParameter("path") != null ? request.getParameter("path") : "";
String currentPath = rootPath + path;
String currentUrl = rootUrl + path;
String currentDirPath = path;
String moveupDirPath = "";
if (!"".equals(path)) {
	String str = currentDirPath.substring(0, currentDirPath.length() - 1);
	moveupDirPath = str.lastIndexOf("/") >= 0 ? str.substring(0, str.lastIndexOf("/") + 1) : "";
}

//排序形式，name or size or type
String order = request.getParameter("order") != null ? request.getParameter("order").toLowerCase() : "name";

//不允许使用..移动到上一级目录
if (path.indexOf("..") >= 0) {
	out.println("Access is not allowed.");
	return;
}
//最后一个字符不是/
if (!"".equals(path) && !path.endsWith("/")) {
	out.println("Parameter is not valid.");
	return;
}
//目录不存在或不是目录
///File currentPathFile = new File(currentPath);
//if(!currentPathFile.isDirectory()){
//	out.println("Directory does not exist.");
//	return;
//}

//遍历目录取的文件信息
List<Hashtable> fileList = new ArrayList<Hashtable>();
//读取所有文件
DataFileDao dfDao=new DataFileDao();
Pages pages=new Pages();
pages.setPageNo(p);
pages.setPageSize(100);
List<DataFile> dfList= dfDao.getDataFileListByPage(pages);
if(dfList!=null && dfList.size()>0){
	for(DataFile df :dfList){
		Hashtable<String, Object> hash = new Hashtable<String, Object>();
		String fileName=df.getFilename();
		String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
		hash.put("is_dir", false);
		hash.put("has_file", false);
		hash.put("filesize", df.getSize());
		hash.put("is_photo", Arrays.<String>asList(fileTypes).contains(fileExt));
		hash.put("filetype", fileExt);
		hash.put("filename",df.getId()+"_"+ fileName);
		hash.put("datetime", Tools.changeTime(df.getPostDate()));
		fileList.add(hash);
	}
}

if ("size".equals(order)) {
	Collections.sort(fileList, new SizeComparator());
} else if ("type".equals(order)) {
	Collections.sort(fileList, new TypeComparator());
} else {
	Collections.sort(fileList, new NameComparator());
}
JSONObject result = new JSONObject();
result.put("moveup_dir_path", moveupDirPath);
result.put("current_dir_path", currentDirPath);
result.put("current_url", currentUrl);
result.put("total_count", fileList.size());
result.put("file_list", fileList);

response.setContentType("application/json; charset=UTF-8");
out.println(result.toJSONString());
%>
<%!
public class NameComparator implements Comparator {
	public int compare(Object a, Object b) {
		Hashtable hashA = (Hashtable)a;
		Hashtable hashB = (Hashtable)b;
		if (((Boolean)hashA.get("is_dir")) && !((Boolean)hashB.get("is_dir"))) {
			return -1;
		} else if (!((Boolean)hashA.get("is_dir")) && ((Boolean)hashB.get("is_dir"))) {
			return 1;
		} else {
			return ((String)hashA.get("filename")).compareTo((String)hashB.get("filename"));
		}
	}
}
public class SizeComparator implements Comparator {
	public int compare(Object a, Object b) {
		Hashtable hashA = (Hashtable)a;
		Hashtable hashB = (Hashtable)b;
		if (((Boolean)hashA.get("is_dir")) && !((Boolean)hashB.get("is_dir"))) {
			return -1;
		} else if (!((Boolean)hashA.get("is_dir")) && ((Boolean)hashB.get("is_dir"))) {
			return 1;
		} else {
			if (((Long)hashA.get("filesize")) > ((Long)hashB.get("filesize"))) {
				return 1;
			} else if (((Long)hashA.get("filesize")) < ((Long)hashB.get("filesize"))) {
				return -1;
			} else {
				return 0;
			}
		}
	}
}
public class TypeComparator implements Comparator {
	public int compare(Object a, Object b) {
		Hashtable hashA = (Hashtable)a;
		Hashtable hashB = (Hashtable)b;
		if (((Boolean)hashA.get("is_dir")) && !((Boolean)hashB.get("is_dir"))) {
			return -1;
		} else if (!((Boolean)hashA.get("is_dir")) && ((Boolean)hashB.get("is_dir"))) {
			return 1;
		} else {
			return ((String)hashA.get("filetype")).compareTo((String)hashB.get("filetype"));
		}
	}
}
%>