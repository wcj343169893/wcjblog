package com.google.choujone.blog.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.choujone.blog.dao.DataFileDao;
import com.google.choujone.blog.entity.DataFile;

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
			//下面这段代码，是因为google限制了流量，限制了数据库访问量 ，所以禁止显示图片
			/**
			 * DataFile df = null; if (id != null) { df =
			 * service.get(Long.valueOf(id), true); }
			 * 
			 * if (df != null && df.getFilename().equals(filename)) {
			 * resp.getOutputStream().write(df.getFileData().getBytes()); }
			 * */
		} catch (Exception e) {
			// resp.sendRedirect("/images/error.gif");
		}
		resp.sendRedirect("/images/error.gif");
	}
}