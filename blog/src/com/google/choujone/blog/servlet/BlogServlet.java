package com.google.choujone.blog.servlet;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.choujone.blog.common.Operation;
import com.google.choujone.blog.dao.BlogDao;
import com.google.choujone.blog.entity.Blog;
import com.google.choujone.blog.util.Config;
import com.google.choujone.blog.util.Tools;

@SuppressWarnings("serial")
public class BlogServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String operation = req.getParameter("op") != null ? req
				.getParameter("op") : "";// 获取操作
		String id = req.getParameter("id");// 博客id
		String ids = req.getParameter("ids");// 博客id数组
		String isVisible = req.getParameter("isVisible");// 是否显示
		Blog blog = new Blog();
		BlogDao blogDao;
		if (operation.trim().equals(Operation.delete.toString())) {// 删除
			blogDao = new BlogDao();
			if (ids != null) {
				String[] id_str = ids.split(",");
				for (int i = 0; i < id_str.length; i++) {
					Long bid = Long.valueOf(id_str[i].trim());
					if (bid > 0) {
						blogDao.deleteBlog(bid);// 真删除
					}
				}
			}
			if (id != null && isVisible != null && Long.valueOf(id.trim()) > 0) {// 隐藏
				blog.setId(Long.valueOf(id.trim()));
				blog.setIsVisible(Integer.parseInt(isVisible));
				blogDao.operationBlog(Operation.delete, blog);
			}
			blogDao.closePM();
			resp.sendRedirect("/admin/blog_list.jsp");
		} else if (operation.trim().equals(Operation.modify.toString())) {// 加载修改
			blogDao = new BlogDao();
			blog = blogDao.getBlogById(Tools.strTolong(id));
			req.setAttribute("blog", blog);
			req.getRequestDispatcher("/admin/blog_edit.jsp").forward(req, resp);
		} else if (operation.trim().equals(Operation.readTimes.toString())) {// 定时更新阅读次数
			try {
				blogDao = new BlogDao();
				// 读取浏览缓存
				// 更新点击
				Map<Long, Integer> blogReadCount = Config.blogReadCount;
				if (blogReadCount != null) {
					for (Long bid : blogReadCount.keySet()) {
						blog = new Blog();
						blog.setId(bid);
						blog.setCount(blogReadCount.get(bid));
						blogDao.operationBlog(Operation.readTimes, blog);
					}
				}
			} catch (Exception e) {
			}
			// 清空统计
			Config.blogReadCount = new HashMap<Long, Integer>();
		} else {
			// 随机显示博客
			if (id == null) {
				String url = req.getRequestURI();
				String temp[] = url.split("/");
				id = temp[temp.length - 1];// 获取博客
				if (id == null || id.equals("blog")) {
					// id = blogDao.getBlogByRand();
					// 不能随机显示，直接跳转到主页
					resp.sendRedirect("/");
					return;
				}
				req.setAttribute("id", id);
			}// 显示某篇文章
			// 如果id==-1
			if (id.equals("-1")) {
				resp.sendRedirect("/leaveMessage.jsp");
			} else {
				req.getRequestDispatcher("/blog_detail.jsp").forward(req, resp);
			}
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String operation = req.getParameter("op") != null ? req
				.getParameter("op") : "";// 获取操作
		String title = req.getParameter("title");// 标题
		String content = req.getParameter("content");// 文章信息
		String tag = req.getParameter("tag");// 关键字
		String ids = req.getParameter("id");// 文章id
		String tid = req.getParameter("tid");// 分类id
		String isVisible = req.getParameter("isVisible") != null ? req
				.getParameter("isVisible") : "1";// 是否发布
		Blog blog = new Blog();
		BlogDao blogDao = new BlogDao();
		blog.setTag(tag);
		blog.setTitle(title);
		blog.setTid(Long.valueOf(tid));
		blog.setIsVisible(Integer.parseInt(isVisible));
		blog.setContent(new com.google.appengine.api.datastore.Text(Tools
				.changeHTML(Tools.toChinese(content))));
		if (operation.trim().equals(Operation.add.toString())) {// 新增
			blog.setCount(0);
			blog.setReplyCount(0);
			blog.setSdTime(Tools.changeTime(new Date()));
			blog.setSource("后台发布");
			blogDao.operationBlog(Operation.add, blog);
		} else if (operation.trim().equals(Operation.modify.toString())) {// 修改
			blog.setId(Long.valueOf(ids));
			blogDao.operationBlog(Operation.modify, blog);
		}
		resp.sendRedirect("/admin/blog_list.jsp");
	}
}
