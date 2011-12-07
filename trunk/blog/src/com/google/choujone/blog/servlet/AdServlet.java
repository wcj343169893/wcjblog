package com.google.choujone.blog.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.choujone.blog.util.Spider;

public class AdServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// 前台显示广告
		resp.setContentType("text/html;charset=utf-8");
		resp.setCharacterEncoding("UTF-8");
		resp.setHeader("Cache-Control", "no-cache");
		PrintWriter out = resp.getWriter();
		String code = "";
		Spider.getUrl();
		try {

			String url = req.getRequestURI();
			int ind = url.lastIndexOf("/");
			if (ind == -1)
				return;

			String temp[] = url.split("/");
			String id = temp[temp.length - 1];//获取广告位编号
			//相同广告位的广告，随机抓取
			code = "广告位:" + id;
		} catch (Exception e) {
		}
		out.write(code);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// 后台管理广告
		//添加广告位
		
		//添加广告
	}
}
