package com.google.choujone.blog.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.choujone.blog.common.Operation;
import com.google.choujone.blog.dao.BlogTypeDao;
import com.google.choujone.blog.entity.BlogType;

@SuppressWarnings("serial")
public class BlogTypeServlet extends HttpServlet {
	BlogTypeDao btd = null;

	/*
	 * 增加博文类型
	 * 
	 * @see
	 * javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest
	 * , javax.servlet.http.HttpServletResponse)
	 */
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String tname = req.getParameter("tname");
		String info = req.getParameter("info");
		String operation = req.getParameter("opera");
		boolean flag = false;
		btd = new BlogTypeDao();
		BlogType bt = new BlogType();
		bt.setInfo(info);
		bt.setName(tname);
		if (Operation.add.toString().equals(operation.trim())) {
			flag = btd.operationBlog(Operation.add, bt);
		}
		resp.setContentType("text/html;charset=utf-8");
		resp.setCharacterEncoding("UTF-8");
		resp.setHeader("Cache-Control", "no-cache");
		PrintWriter out = resp.getWriter();
		out.println(flag);
		out.close();
	}

	/*
	 * 显示博文类型
	 * 
	 * @see
	 * javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest
	 * , javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String t = req.getParameter("t") != null
				&& !"".equals(req.getParameter("t").trim()) ? req
				.getParameter("t") : "0";// 获取博文原来的类型
		int tid = 0;
		try {
			tid = Integer.parseInt(t);
		} catch (Exception e) {
		}
		btd = new BlogTypeDao();
		List<BlogType> blogTypeList = btd.getBlogTypeList();
		resp.setContentType("text/html;charset=utf-8");
		resp.setCharacterEncoding("UTF-8");
		resp.setHeader("Cache-Control", "no-cache");
		String type_str = "<select name=\"tid\">";
		if (blogTypeList != null && blogTypeList.size() > 0) {
			for (BlogType blogType : blogTypeList) {
				type_str += "<option value=\"";
				type_str += blogType.getId();
				type_str += "\"";
				if (tid == blogType.getId()) {
					type_str += " selected";
				}
				type_str += "\">";
				type_str += blogType.getName();
				type_str += "</option>";
			}
		}
		type_str += "</select><a href=\"javascript:void(0)\" onclick=\"showOrHideDiv('addType')\">增加分类</a>";
		PrintWriter out = resp.getWriter();
		out.println(type_str);
		out.close();
	}
}
