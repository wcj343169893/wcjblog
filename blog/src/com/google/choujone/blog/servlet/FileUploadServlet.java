package com.google.choujone.blog.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadBase.FileUploadIOException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.google.appengine.api.datastore.Blob;
import com.google.choujone.blog.dao.DataFileDao;
import com.google.choujone.blog.entity.DataFile;

/**
 * @author
 */
public class FileUploadServlet extends HttpServlet {
	public static final boolean verbose = true;
	private static final long serialVersionUID = -3372121303184986833L;
	public static final int MAX_FILE_SIZE = 1024 * 1024 * 2;// 2MB
	public static final int MAX_FILES = 5;
	private byte[] data = new byte[MAX_FILE_SIZE];
	private byte[] buffer = new byte[1024];// 1kb
	private DataFileDao service = new DataFileDao();

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setContentType("text/html");
		String path = null;
		if (req.getServerPort() == 80)
			path = "http://" + req.getServerName();
		else
			path = "http://" + req.getServerName() + ":" + req.getServerPort();

		// if(Authority.checkAuthorize() == false){
		// UserService userService = UserServiceFactory.getUserService();
		// resp.sendRedirect(userService.createLoginURL(path+"/upload.html"));
		// return;
		// }

		StringBuffer buff = new StringBuffer();
		boolean isMultipart = ServletFileUpload.isMultipartContent(req);

		if (isMultipart) {
			ServletFileUpload uploader = new ServletFileUpload();
			// set single file size
			uploader.setFileSizeMax(MAX_FILE_SIZE);
			// set total file size
			uploader.setSizeMax(MAX_FILES * MAX_FILE_SIZE);

			int size = 0, len = 0;
			try {
				FileItemIterator it = uploader.getItemIterator(req);

				buff.append("<div>");
				while (it.hasNext()) {
					size = len = 0;
					FileItemStream item = it.next();
					if (item.isFormField() || item.getName() == null)
						continue;

					String filename = item.getName();
					InputStream in = item.openStream();
					while ((len = in.read(buffer)) != -1) {
						System.arraycopy(buffer, 0, data, size, len);
						size += len;
					}
					in.close();
					if (size <= 0)
						continue;

					if (verbose)
						System.out.println("filename:" + filename + "size: "
								+ len);
					byte[] byt = Arrays.copyOf(data, size);
					Blob bo = new Blob(byt);
					DataFile df = new DataFile();
					df.setFileData(bo);
					df.setFilename(filename);
					df.setSize(size);
					df.setPostDate(new Date(System.currentTimeMillis()));

					StringBuffer custom_info=new StringBuffer();
		            custom_info.append("RemoteAddr"+":"+req.getRemoteAddr()+"</br>");
		            custom_info.append("Content-Length"+":"+req.getHeader("Content-Length")+"</br>");
		            custom_info.append("Accept-Charset"+":"+req.getHeader("Accept-Charset")+"</br>");
		            custom_info.append("Accept-Language"+":"+req.getHeader("Accept-Language")+"</br>");
					
					df.setDescription(custom_info.toString());
					
					service.add(df);
					appendFile(df, buff, path);
				}

				buff.append("</div>");

				resp.getOutputStream().write(buff.toString().getBytes());
			} catch (FileUploadIOException e) {
				resp.getOutputStream().write(
						"File size should be within 2M each.".getBytes());
			} catch (Exception e) {
				resp.getOutputStream().write("File upload failure.".getBytes());
			}
		}
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setContentType("text/html");
		//分页获取连接
		
		StringBuffer buff = new StringBuffer();
		buff.append("<div>");
		buff.append("Post method is not supported!");
		buff.append("</div>");
		resp.getOutputStream().write(buff.toString().getBytes());
	}

	private void appendFile(DataFile df, StringBuffer buff, String path) {
		String url = path + "/file/" + df.getId() + "_" + df.getFilename();
		String durl = path + "/delete/" + df.getId() + "_" + df.getFilename();
		buff.append("<ul>");
		buff.append("<li><a href=\"").append(url).append(
				"\" target=\"_blank\">").append(url).append("</a>");
		buff.append("</ul>");
	}
}