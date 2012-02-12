package com.google.choujone.blog.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.Text;
import com.google.choujone.blog.common.Operation;
import com.google.choujone.blog.common.WebPage;
import com.google.choujone.blog.dao.BlogDao;
import com.google.choujone.blog.dao.SpiderDao;
import com.google.choujone.blog.entity.Blog;
import com.google.choujone.blog.entity.Spider;
import com.google.choujone.blog.util.SpiderUtil;
import com.google.choujone.blog.util.Tools;

public class SpiderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO 采集测试
		String id = req.getParameter("id") != null ? req
				.getParameter("id") : "-1L";
		String operation = req.getParameter("opera") != null ? req
				.getParameter("opera") : "";
		// 采集名称
		String name = req.getParameter("name") != null ? req
				.getParameter("name") : "";
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
		// 博客分类
		String tids = req.getParameter("tids") != null ? req
				.getParameter("tids") : "";
		// 采集定时时间 如果为空或者某个时间点，则马上执行；如果为时分秒，则每天的那个时候执行；
		String spider_start = req.getParameter("spider_start") != null ? req
				.getParameter("spider_start") : "";

		SpiderUtil spiderUtil = new SpiderUtil();
		SpiderDao sd = new SpiderDao();
		resp.setContentType("text/html;charset=utf-8");
		resp.setCharacterEncoding("UTF-8");
		resp.setHeader("Cache-Control", "no-cache");
		PrintWriter out = resp.getWriter();
		//设置编码
		spiderUtil.setCharset(charset);
		//设置域名
		spiderUtil.setWeb_host(web_host);
		// 设置list地址
		spiderUtil.setListUrl(web_list_url);
		// 设置list列表区域开始和结束
		spiderUtil.setWeb_list_begin(web_list_begin);
		spiderUtil.setWeb_list_end(web_list_end);
		if (operation.equals(Operation.testList.toString())) {// 测试列表
			// 得到列表地址
			List<String> urls = spiderUtil.getContentUrls();
			for (String string : urls) {
				out.println(string);
			}
		} else if (operation.equals(Operation.testContent.toString())) {// 测试内容
			// 得到列表之后，设置内容页的标题
			spiderUtil.setWeb_content_title(web_content_title);
			// 设置标签清理 为空代表清理所有
			spiderUtil.setClear_title_reg(null);
			// 设置内容开始结束位置
			spiderUtil.setWeb_content_begin(web_content_begin);
			spiderUtil.setWeb_content_end(web_content_end);
			// 清理内容标签
			spiderUtil.setClear_content_reg(clear_content_reg.split(","));
			// 得到内容页面
			List<WebPage> webPages = spiderUtil.getWebPages();
			for (WebPage webPage : webPages) {
				// System.out.println("url:" + webPage.getUrl() + " \t title:"
				// + webPage.getTitle() + "\t content:"
				// + webPage.getContent().substring(0, 100));
				out.println("url:" + webPage.getUrl() + " \t title:"
						+ webPage.getTitle());
			}
		} else if (operation.equals(Operation.add.toString())) {// 新增
			Spider spider = new Spider();
			spider.setCharset(charset);
			spider.setWeb_host(web_host);
			spider.setName(name);
			spider.setTids(tids);
			spider.setSpider_start(spider_start);
			spider.setWeb_list_url(web_list_url);
			spider.setWeb_list_begin(web_list_begin);
			spider.setWeb_list_end(web_list_end);
			spider.setWeb_content_title(web_content_title);
			spider.setWeb_content_begin(web_content_begin);
			spider.setWeb_content_end(web_content_end);
			spider.setClear_content_reg(clear_content_reg);
			sd.operationSpider(Operation.add, spider);
			// // 设置list地址
			// spiderUtil.setListUrl(web_list_url);
			// // 设置list列表区域开始和结束
			// spiderUtil.setWeb_list_begin(web_list_begin);
			// spiderUtil.setWeb_list_end(web_list_end);
			// // 得到列表之后，设置内容页的标题
			// spiderUtil.setWeb_content_title(web_content_title);
			// // 设置标签清理 为空代表清理所有
			// spiderUtil.setClear_title_reg(null);
			// // 设置内容开始结束位置
			// spiderUtil.setWeb_content_begin(web_content_begin);
			// spiderUtil.setWeb_content_end(web_content_end);
			// // 清理内容标签
			// spiderUtil.setClear_content_reg(clear_content_reg.split(","));
			// // 得到内容页面
			// List<WebPage> webPages = spiderUtil.getWebPages();
			// 保存到数据库

			out.print("保存成功");
		} else if (operation.equals(Operation.preModify.toString())) {// 加载修改

		} else if (operation.equals(Operation.modify.toString())) {// 修改

		} else if (operation.equals(Operation.delete.toString())) {// 删除

		} else if (operation.equals(Operation.start.toString())) {// 开始运行
			//得到spider
			Long sid=Long.valueOf(id);
			if (sid>0) {
				Spider spider=sd.getSpiderById(sid);
				// TODO 排除重复地址（内容页）
				List<WebPage> webPageList=spiderUtil.run(spider);
				BlogDao bd=new BlogDao();
				Blog blog=null;
				for (WebPage wp : webPageList) {
					blog=new Blog();
					blog.setTitle(wp.getTitle());
					blog.setTid(Long.valueOf(spider.getTids()));
					blog.setContent(new Text(wp.getContent()));
					blog.setCount(0);
					blog.setIsVisible(0);
					blog.setSdTime(Tools.changeTime(new Date()));
					bd.operationBlog(Operation.add, blog);
				}
				//创建运行线程（ access denied）
//				SpiderThread st=new SpiderThread();
//				st.setSpider(spider);
				out.print("启动成功,任务即将在"+spider.getSpider_start()+"运行");
			}
		}
		// out.write(web_host);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
	}
}
