
<%@page import="org.apache.commons.fileupload.FileUploadBase.FileUploadIOException"%>
<%@page import="com.google.choujone.blog.util.Uplode"%><%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*,java.io.*"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="org.apache.commons.fileupload.*"%>
<%@ page import="org.apache.commons.fileupload.disk.*"%>
<%@ page import="org.apache.commons.fileupload.servlet.*"%>
<%@ page import="org.json.simple.*"%>
<%@ page import="org.apache.commons.fileupload.*"%>
<%@ page import="com.google.appengine.api.datastore.Blob"%>
<%@ page import="com.google.choujone.blog.dao.DataFileDao"%>
<%@ page import="com.google.choujone.blog.entity.DataFile"%>

<%
	boolean verbose = false;
	long serialVersionUID = -3372121303184986833L;
	int MAX_FILE_SIZE = 1024 * 1024 * 1;// 1MB
	int MAX_FILES = 5;
	byte[] data = new byte[MAX_FILE_SIZE];
	byte[] buffer = new byte[1024];// 1kb
	DataFileDao service = new DataFileDao();
	response.setContentType("text/html");
	String path = null;
	if (request.getServerPort() == 80)
		path = "http://" + request.getServerName();
	else
		path = "http://" + request.getServerName() + ":"
				+ request.getServerPort();

	// if(Authority.checkAuthorize() == false){
	// UserService userService = UserServiceFactory.getUserService();
	// response.sendRedirect(userService.createLoginURL(path+"/upload.html"));
	// return;
	// }

	StringBuffer buff = new StringBuffer();
	boolean isMultipart = ServletFileUpload.isMultipartContent(request);

	if (isMultipart) {
		ServletFileUpload uploader = new ServletFileUpload();
		// set single file size
		uploader.setFileSizeMax(MAX_FILE_SIZE);
		// set total file size
		uploader.setSizeMax(MAX_FILES * MAX_FILE_SIZE);

		int size = 0, len = 0;
		try {
			FileItemIterator it = uploader.getItemIterator(request);
			String url = "";
			//现在只允许上传一张图片
			while (it.hasNext()) {
				size = len = 0;
				FileItemStream item = it.next();
				if (item.isFormField() || item.getName() == null)
					continue;

				String filename = item.getName();
				//重新生成一个文件名
				
				// 制作文件名
				String[] str = filename.split("\\.");// 只要后缀
				int random = (int) (Math.random() * 10000);// 随机数防止文件名重复
				filename = Uplode.getDateTime("yyyyMMddHHmmss") + "_" + random + "."
						+ str[str.length - 1];
				//System.out.println(mytime);
				
				InputStream in = item.openStream();
				while ((len = in.read(buffer)) != -1) {
					System.arraycopy(buffer, 0, data, size, len);
					size += len;
				}
				in.close();
				if (size <= 0)
					continue;

				if (verbose)
					System.out.println("filename:" + filename
							+ "size: " + len);
				byte[] byt = Arrays.copyOf(data, size);
				Blob bo = new Blob(byt);
				DataFile df = new DataFile();
				df.setFileData(bo);
				df.setFilename(filename);
				df.setSize(size);
				df.setPostDate(new Date(System.currentTimeMillis()));
				
				StringBuffer custom_info=new StringBuffer();
	            custom_info.append("RemoteAddr"+":"+request.getRemoteAddr()+"</br>");
	            custom_info.append("Content-Length"+":"+request.getHeader("Content-Length")+"</br>");
	            custom_info.append("Accept-Charset"+":"+request.getHeader("Accept-Charset")+"</br>");
	            custom_info.append("Accept-Language"+":"+request.getHeader("Accept-Language")+"</br>");
				
				df.setDescription(custom_info.toString());
				service.add(df);
				url = path + "/file/" + df.getId() + "_"
						+ df.getFilename();
			}

			JSONObject obj = new JSONObject();
			obj.put("error", 0);
			obj.put("url", url);
			out.print(obj.toJSONString());
		}catch (FileUploadBase.FileUploadIOException e) {
			out.println(getError("上传文件不能大于"+MAX_FILE_SIZE/1024/1024+"M。"));
		} catch (Exception e) {
			out.println(getError("上传出错啦。"));
		}
	}
%>
<%!
private String getError(String message) {
	JSONObject obj = new JSONObject();
	obj.put("error", 1);
	obj.put("message", message);
	return obj.toJSONString();
}
%>