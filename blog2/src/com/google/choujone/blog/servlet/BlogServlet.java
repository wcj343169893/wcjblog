package com.google.choujone.blog.servlet;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.choujone.blog.common.Operation;
import com.google.choujone.blog.dao.BlogDao;
import com.google.choujone.blog.entity.Blog;
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
		BlogDao blogDao = new BlogDao();
		if (operation.trim().equals(Operation.delete.toString())) {// 删除
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
			resp.sendRedirect("/admin/blog_list.jsp");
		} else if (operation.trim().equals(Operation.modify.toString())) {// 加载修改
			blog = blogDao.getBlogById(Tools.strTolong(id));
			req.setAttribute("blog", blog);
			req.getRequestDispatcher("/admin/blog_edit.jsp").forward(req, resp);
		} else {// 显示文章
			req.getRequestDispatcher("/blog_detail.jsp").forward(req, resp);
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
			blogDao.operationBlog(Operation.add, blog);
		} else if (operation.trim().equals(Operation.modify.toString())) {// 修改
			blog.setId(Long.valueOf(ids));
			blogDao.operationBlog(Operation.modify, blog);
		}
		resp.sendRedirect("/admin/blog_list.jsp");
	}
}
