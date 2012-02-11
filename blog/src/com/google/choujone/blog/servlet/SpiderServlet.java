package com.google.choujone.blog.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.choujone.blog.common.Operation;
import com.google.choujone.blog.common.WebPage;
import com.google.choujone.blog.util.SpiderUtil;

public class SpiderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO 采集测试
		String operation = req.getParameter("opera") != null ? req
				.getParameter("opera") : "";
		// 网站域名
		String web_host = req.getParameter("web_host") != null ? req
				.getParameter("web_host") : "";
		// 网站编码
		String charset = req.getParameter("web_charSet") != null ? req
				.getParameter("web_charSet") : "utf-8";
		// 列表地址
		String web_list_url = req.getParameter("web_list_url") != null ? req
				.getParameter("web_list_url") : "";
		// 列表开始位置和结束位置
		String web_list_begin = req.getParameter("web_list_begin") != null ? req
				.getParameter("web_list_begin")
				: "";
		String web_list_end = req.getParameter("web_list_end") != null ? req
				.getParameter("web_list_end") : "";
		// 内容标题
		String web_content_title = req.getParameter("web_content_title") != null ? req
				.getParameter("web_content_title")
				: "";
		// 内容开始位置和结束位置
		String web_content_begin = req.getParameter("web_content_begin") != null ? req
				.getParameter("web_content_begin")
				: "";
		String web_content_end = req.getParameter("web_list_end") != null ? req
				.getParameter("web_content_end") : "";
		// 内容保留标签
		String clear_content_reg = req.getParameter("clear_content_reg") != null ? req
				.getParameter("clear_content_reg")
				: "";
		String tids = req.getParameter("tids") != null ? req
				.getParameter("tids") : "";

		SpiderUtil spider = new SpiderUtil();
		spider.setCharset(charset);
		spider.setWeb_host(web_host);
		resp.setContentType("text/html;charset=utf-8");
		resp.setCharacterEncoding("UTF-8");
		resp.setHeader("Cache-Control", "no-cache");
		PrintWriter out = resp.getWriter();
		if (operation.equals(Operation.testList.toString())) {// 测试列表
			// 设置list地址
			spider.setListUrl(web_list_url);
			// 设置list列表区域开始和结束
			spider.setWeb_list_begin(web_list_begin);
			spider.setWeb_list_end(web_list_end);
			// 得到列表地址
			List<String> urls = spider.getContentUrls();
			for (String string : urls) {
				out.println(string);
			}

		} else if (operation.equals(Operation.testContent.toString())) {// 测试内容
			// 设置list地址
			spider.setListUrl(web_list_url);
			// 设置list列表区域开始和结束
			spider.setWeb_list_begin(web_list_begin);
			spider.setWeb_list_end(web_list_end);
			// 得到列表之后，设置内容页的标题
			spider.setWeb_content_title(web_content_title);
			// 设置标签清理 为空代表清理所有
			spider.setClear_title_reg(null);
			// 设置内容开始结束位置
			spider.setWeb_content_begin(web_content_begin);
			spider.setWeb_content_end(web_content_end);
			// 清理内容标签
			spider.setClear_content_reg(clear_content_reg.split(","));
			// 得到内容页面
			List<WebPage> webPages = spider.getWebPages();
			for (WebPage webPage : webPages) {
//				System.out.println("url:" + webPage.getUrl() + " \t title:"
//						+ webPage.getTitle() + "\t content:"
//						+ webPage.getContent().substring(0, 100));
				out.println("url:" + webPage.getUrl() + " \t title:"+ webPage.getTitle());
			}
		} else {
			// 设置list地址
			spider
					.setListUrl("http://www.xiaodiao.com/html/gndy/dyzz/index.html");
			// 设置list列表区域开始和结束
			spider.setWeb_list_begin("<div class=\"co_content8\">");
			spider.setWeb_list_end("align=\"center\" bgcolor=\"#F4FAE2\"");
			// 得到列表之后，设置内容页的标题
			spider.setWeb_content_title("<h1>(.*)</h1>");
			// 设置标签清理 为空代表清理所有
			spider.setClear_title_reg(null);
			// 设置内容开始结束位置
			spider.setWeb_content_begin("<div id=\"Zoom\">");
			spider.setWeb_content_end("安装软件后,点击即可下载,谢谢大家支持，欢迎每天来");
			// 清理内容标签
			spider.setClear_content_reg(new String[] { "img", "p", "/p",
					"span", "/span" });
			// 得到内容页面
			List<WebPage> webPages = spider.getWebPages();
			for (WebPage webPage : webPages) {
				System.out.println("url:" + webPage.getUrl() + " \t title:"
						+ webPage.getTitle() + "\t content:"
						+ webPage.getContent().substring(0, 100));
			}
		}
		// out.write(web_host);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
	}
}
