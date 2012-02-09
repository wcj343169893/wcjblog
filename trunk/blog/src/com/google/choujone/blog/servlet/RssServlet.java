package com.google.choujone.blog.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.choujone.blog.dao.BlogDao;
import com.google.choujone.blog.entity.Blog;
import com.google.choujone.blog.util.Config;
import com.google.choujone.blog.util.Tools;

public class RssServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// 前台显示xml
		resp.setContentType("text/xml;charset=utf-8");
		resp.setCharacterEncoding("UTF-8");
		resp.setHeader("Cache-Control", "no-cache");
		PrintWriter out = resp.getWriter();
		StringBuffer code = new StringBuffer();
		// Spider.getUrl();
		// try {
		// String url = req.getRequestURI();
		// int ind = url.lastIndexOf("/");
		// if (ind == -1)
		// return;
		//
		// String temp[] = url.split("/");
		// String id = temp[temp.length - 1];
		// // code = "广告位:" + id;
		// } catch (Exception e) {
		//
		// }
		BlogDao bd = new BlogDao();
		List<Blog> blogList = bd.getBlogList();
		code.append("<?xml version=\"1.0\" encoding=\"utf-8\" ?>");
		code.append("<blogs>");
		for (Blog blog : blogList) {
			code.append("<blog>");
			code.append("<id>");
			code.append(blog.getId() + "");
			code.append("</id>");
			code.append("<title>");
			code.append(blog.getTitle());
			code.append("</title>");
			code.append("<url>");
			code.append(Config.getWebUrl() + "blog?" + blog.getId());
			code.append("</url>");
			code.append("<content>");
			code.append(Tools.escape(blog.getContent(200, "").getValue()));
			code.append("</content>");
			code.append("</blog>");
		}

		code.append("</blogs>");
		out.write(code.toString());
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req, resp);
	}
}
