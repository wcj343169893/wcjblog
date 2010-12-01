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
		String tname = req.getParameter("tname") != null ? req
				.getParameter("tname") : "";
		String info = req.getParameter("info") != null ? req
				.getParameter("info") : "";
		String operation = req.getParameter("opera") != null ? req
				.getParameter("opera") : "";
		String t = req.getParameter("t") != null
				&& !"".equals(req.getParameter("t").trim()) ? req
				.getParameter("t") : "0";// 获取博文原来的类型
		boolean flag = false;
		btd = new BlogTypeDao();
		BlogType bt = new BlogType();
		bt.setInfo(info);
		bt.setName(tname);
		if (Operation.add.toString().equals(operation.trim())) {// 新增
			flag = btd.operationBlogType(Operation.add, bt);
		} else if (Operation.modify.toString().equals(operation.trim())) {// 修改
			Long tid = 0L;
			try {
				tid = Long.valueOf(t);
			} catch (Exception e) {
			}
			bt.setId(tid);
			flag = btd.operationBlogType(Operation.modify, bt);
		} else {// 删除
			Long tid = 0L;
			try {
				tid = Long.valueOf(t);
			} catch (Exception e) {
			}
			bt.setId(tid);
			flag = btd.operationBlogType(Operation.delete, bt);
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
		String operation = req.getParameter("opera") != null ? req
				.getParameter("opera") : "";

		Long tid = 0L;
		try {
			tid = Long.valueOf(t);
		} catch (Exception e) {
		}
		btd = new BlogTypeDao();
		resp.setContentType("text/html;charset=utf-8");
		resp.setCharacterEncoding("UTF-8");
		resp.setHeader("Cache-Control", "no-cache");
		PrintWriter out = resp.getWriter();
		if (operation.equals(Operation.lists.toString())) {// 查询所有
			List<BlogType> blogTypeList = btd.getBlogTypeList();
			String type_str = "<select name=\"tid\" id=\"tids\">";
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
			type_str += "</select>&nbsp;";
			type_str += "<a href=\"javascript:void(0)\" onclick=\"showOrHideDiv('addType')\">增加</a>&nbsp;";
			type_str += "<a href=\"javascript:void(0)\" onclick=\"modifyType('modifyType','tids')\">修改</a>&nbsp;";
			type_str += "<a href=\"javascript:void(0)\" onclick=\"deleteType('tids')\">删除</a>";
			out.println(type_str);
		} else {// 根据id查询
			BlogType bt = btd.getBlogTypeById(tid);
			if (bt != null) {
				StringBuffer sb = new StringBuffer();
				sb.append("<strong>修改：</strong>&nbsp;<input type=\"hidden\" id=\"modifytid\" value=\"");
				sb.append(bt.getId());
				sb.append("\" > ");
				sb
						.append("分类名:<input type=\"text\" id=\"modifytname\" value=\"");
				sb.append(bt.getName());
				sb.append("\" >");
				sb
						.append("简 &nbsp;&nbsp;介:<input type=\"text\" id=\"modifyinfo\" value=\"");
				sb.append(bt.getInfo());
				sb.append("\" >");
				sb
						.append("<input type=\"button\" onclick=\"tmodify()\" value=\"保存\">");
				out.println(sb.toString());
			}

		}
		out.close();
	}
}
