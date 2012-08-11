package com.google.choujone.blog.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;

@SuppressWarnings("serial")
public class UploadFileServlet extends HttpServlet {
	String savePath;
	ServletContext sc;
	private BlobstoreService blobstoreService = BlobstoreServiceFactory
			.getBlobstoreService();

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		BlobKey blobKey = new BlobKey(req.getParameter("blob-key"));
		// 获得 BlobKey 之后，通过如下方法获得文件
		blobstoreService.serve(blobKey, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		Map<String, BlobKey> blobs = blobstoreService.getUploadedBlobs(req);
		// 获得 BlobKey 之后，可以对其进行处理，比如保存
		BlobKey blobKey = blobs.get("myfile");
		if (blobKey == null) {
			resp.sendRedirect("/");
		} else {
			resp.sendRedirect("/uploadFile?blob-key=" + blobKey.getKeyString());
		}
		
//		FileUploadServlet fileupload=new FileUploadServlet();
//		fileupload.doPost(req, resp);
	}

	/**
	 * Ajax辅助方法 获取 PrintWriter
	 * 
	 * @return
	 * @throws IOException
	 * @throws IOException
	 *             request.setCharacterEncoding("utf-8");
	 *             response.setContentType("text/html; charset=utf-8");
	 */
	private PrintWriter encodehead(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");

		return response.getWriter();
	}

	/*
	 * 初始化上传路径
	 * 
	 * @see javax.servlet.GenericServlet#init(javax.servlet.ServletConfig)
	 */
	@Override
	public void init(ServletConfig config) throws ServletException {
		savePath = config.getInitParameter("savePath");
		sc = config.getServletContext();
		super.init();
	}
}