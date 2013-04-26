package com.google.choujone.blog.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.google.choujone.blog.util.Mail;
import com.google.choujone.blog.util.SpiderUtil;
import com.google.choujone.blog.util.Tools;

/**
 * choujone'blog<br>
 * 功能描述：采集json数据 2013-4-25
 */
public class CrawlServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setContentType("text/html;charset=utf-8");
		resp.setCharacterEncoding("UTF-8");
		resp.setHeader("Cache-Control", "no-cache");
		PrintWriter out = resp.getWriter();
		JSONObject obj = new JSONObject();
		String link = req.getParameter("url") != null ? req.getParameter("url")
				: "";
		String callback = req.getParameter("callback") != null ? req
				.getParameter("callback") : "";
		String ip = Tools.getIpAddr(req);
		String emailContent = "URL:" + link + "<br/>Ip:" + ip + "<br/>"
				+ req.getHeader("user-agent");
		if (!"".equals(link) && link.indexOf("http") == 0) {
			SpiderUtil spiderUtil = new SpiderUtil();
			String content = spiderUtil.getContent(link);
			JSONParser jp = new JSONParser();
			try {
				obj = (JSONObject) jp.parse(content);
			} catch (ParseException e) {
				Mail.send("转换json失败", emailContent);
				out.print("Json 转换失败!");
				return;
			}
			//Mail.send("转换json", emailContent);
		} else {
			Mail.send("非法json", emailContent);
		}
		if (!"".equals(callback)) {
			resp.setContentType("text/javascript");
			out.print(callback + "(" + obj.toJSONString() + ")");
		} else {
			// resp.setContentType("application/json");
			// String str=obj.toJSONString();
			// 直接输出纯文本
			out.print(obj.toJSONString());
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		this.doGet(req, resp);
	}
}
