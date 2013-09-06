package com.google.choujone.blog.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.choujone.blog.common.Operation;
import com.google.choujone.blog.dao.BlogDao;
import com.google.choujone.blog.entity.Blog;
import com.google.choujone.blog.util.Tools;

public class Blog4AndroidServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		boolean flag = false;
		if (Tools.isLogin(req)) {
			String title = req.getParameter("title");// 标题
			String content = req.getParameter("content");// 文章信息
			String tag = req.getParameter("tag");// 关键字
			String isVisible = req.getParameter("isVisible") != null ? req
					.getParameter("isVisible") : "1";// 是否发布
			String tid = req.getParameter("tid") != null ? req
					.getParameter("tid") : "-1L";// 分类id
			Blog blog = new Blog();
			BlogDao blogDao = new BlogDao();
			blog.setTag(tag);
			blog.setTitle(title);
			blog.setTid(Long.valueOf(tid));
			blog.setIsVisible(Integer.parseInt(isVisible));
			blog.setContent(new com.google.appengine.api.datastore.Text(Tools
					.changeHTML(Tools.toChinese(content))));
			blog.setCount(0);
			blog.setReplyCount(0);
			blog.setSdTime(Tools.changeTime(new Date()));
			blog.setSource("android发布");
			flag = blogDao.operationBlog(Operation.add, blog);
		}
		resp.setContentType("text/html;charset=utf-8");
		resp.setCharacterEncoding("UTF-8");
		resp.setHeader("Cache-Control", "no-cache");
		PrintWriter out = resp.getWriter();
		out.println(flag);
		out.close();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}
}
