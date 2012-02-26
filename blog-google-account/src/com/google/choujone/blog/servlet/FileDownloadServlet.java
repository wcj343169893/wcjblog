package com.google.choujone.blog.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.Blob;
import com.google.choujone.blog.dao.DataFileDao;
import com.google.choujone.blog.dao.UserDao;
import com.google.choujone.blog.entity.DataFile;
import com.google.choujone.blog.entity.User;
import com.google.choujone.blog.util.MyCache;

public class FileDownloadServlet extends HttpServlet {
	private static final long serialVersionUID = 1857614584140595636L;
	private DataFileDao service = new DataFileDao();

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setContentType("image/jpeg");
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		try {
			resp.setContentType("image/jpeg");
			String url = req.getRequestURI();
			int ind = url.lastIndexOf("/");
			if (ind == -1)
				return;

			String temp[] = url.split("/");
			String filename = temp[temp.length - 1];
			ind = filename.indexOf("_");
			String id = filename.substring(0, ind);
			filename = filename.substring(ind + 1);
			// 下面这段代码，是因为google限制了流量，限制了数据库访问量 ，所以禁止显示图片
			UserDao userDao = new UserDao();
			User user = userDao.getUserDetail();
			if (user != null && user.getIsUpload() != null
					&& user.getIsUpload() != 1) {// 账户允许上传图片
				// 把图片也加入到缓存中
				String key = "image_"+id;
				Blob image=(Blob)MyCache.cache.get(key);
				if (image == null) {
					DataFile df = null;
					if (id != null) {
						df = service.get(Long.valueOf(id), true);
					}

					if (df != null && df.getFilename().equals(filename)) {
						image = df.getFileData();
					}
					MyCache.cache.put(key, image);
				}
				if (image!=null) {
					resp.getOutputStream().write(image.getBytes());
				}
			} else {
				resp.sendRedirect("/images/error.gif");
			}

		} catch (Exception e) {
			resp.sendRedirect("/images/error.gif");
		}
	}
}