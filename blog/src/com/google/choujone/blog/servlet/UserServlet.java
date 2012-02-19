package com.google.choujone.blog.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.choujone.blog.common.Operation;
import com.google.choujone.blog.dao.UserDao;
import com.google.choujone.blog.entity.User;
import com.google.choujone.blog.util.Config;
import com.google.choujone.blog.util.Tools;

@SuppressWarnings("serial")
public class UserServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String operation = req.getParameter("op") != null ? req
				.getParameter("op") : "";// 获取操作
		UserDao userDao = new UserDao();
		User user = new User();
		if (operation.trim().equals(Operation.delete.toString())) {// 删除
			// reply.setId(Long.valueOf(ids));
			// replyDao.operationReply(Operation.delete, reply);
			// resp.sendRedirect("/admin/reply_list.jsp");
		} else if (operation.trim().equals(Operation.modify.toString())) {
			// 判断用户是否登录
			if (!Tools.isLogin(req)) {
				resp.sendRedirect("/login.jsp");
				return;
			} else {
				// 加载用户信息
				user = userDao.getUserDetail();
				if (user == null) {
					userDao.operationUser(Operation.add, null);// 新增用户
					user = userDao.getUserDetail();
				}
				req.setAttribute("user", user);
				req.getRequestDispatcher("/admin/setting.jsp").forward(req,
						resp);
			}
		} else {// 注销
			req.getSession().removeAttribute("login_user");
			UserService us=UserServiceFactory.getUserService();
			String url=us.createLogoutURL("/");
			resp.sendRedirect(url);
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String operation = req.getParameter("op") != null ? req
				.getParameter("op") : "";// 获取操作
		String pTitle = req.getParameter("pTitle") != null ? req
				.getParameter("pTitle") : "";// 
		String name = req.getParameter("name") != null ? req
				.getParameter("name") : "";// 
		String ctitle = req.getParameter("ctitle") != null ? req
				.getParameter("ctitle") : "";//
		String notice = req.getParameter("notice") != null ? req
				.getParameter("notice") : "";// 
		String email = req.getParameter("email") != null ? req
				.getParameter("email") : "";// 
		String password = req.getParameter("password") != null ? req
				.getParameter("password") : "";// 
		String url = req.getParameter("url") != null ? req.getParameter("url")
				: "";// 
		String address = req.getParameter("address") != null ? req
				.getParameter("address") : "";
		String brithday = req.getParameter("brithday") != null ? req
				.getParameter("brithday") : "";
		String description = req.getParameter("description") != null ? req
				.getParameter("description") : "";
		String style = req.getParameter("style") != null ? req
				.getParameter("style") : "";

		Integer isWeather = Integer
				.parseInt(req.getParameter("isWeather") != null ? req
						.getParameter("isWeather") : "0");// 是否显示天气
		Integer isCalendars = Integer
				.parseInt(req.getParameter("isCalendars") != null ? req
						.getParameter("isCalendars") : "0");// 是否显示日历
		Integer isHotBlog = Integer
				.parseInt(req.getParameter("isHotBlog") != null ? req
						.getParameter("isHotBlog") : "0");// 是否显示热门文章

		Integer isNewReply = Integer
				.parseInt(req.getParameter("isNewReply") != null ? req
						.getParameter("isNewReply") : "0");// 是否显示最新评论
		Integer isLeaveMessage = Integer.parseInt(req
				.getParameter("isLeaveMessage") != null ? req
				.getParameter("isLeaveMessage") : "0");// 是否显示留言

		Integer isUpload = Integer
				.parseInt(req.getParameter("isUpload") != null ? req
						.getParameter("isUpload") : "0");// 是否允许上传图片

		Integer isStatistics = Integer.parseInt(req
				.getParameter("isStatistics") != null ? req
				.getParameter("isStatistics") : "0");// 是否显示统计
		Integer isFriends = Integer
				.parseInt(req.getParameter("isFriends") != null ? req
						.getParameter("isFriends") : "0");// 是否显示友情链接
		Integer isInfo = Integer
				.parseInt(req.getParameter("isInfo") != null ? req
						.getParameter("isInfo") : "0");// 是否显示个人资料
		Integer isTags = Integer
				.parseInt(req.getParameter("isTags") != null ? req
						.getParameter("isTags") : "0");// 是否显示tags
		Integer isType = Integer
				.parseInt(req.getParameter("isType") != null ? req
						.getParameter("isType") : "0");// 是否显示文章类型
		String blogMenu = req.getParameter("blogMenu") != null ? req
				.getParameter("blogMenu") : "";// 博客menu
		String blogKeyword = req.getParameter("blogKeyword") != null ? req
				.getParameter("blogKeyword") : "";// 博客关键字
		String blogDescription = req.getParameter("blogDescription") != null ? req
				.getParameter("blogDescription")
				: "";// 博客描述
		String preMessage = req.getParameter("preMessage") != null ? req
				.getParameter("preMessage") : "";// 博客描述
		// 2011-10-28 添加顶部和底部代码
		String blogHead = req.getParameter("blogHead") != null ? req
				.getParameter("blogHead") : ""; // 博客顶部 声明
		String blogFoot = req.getParameter("blogFoot") != null ? req
				.getParameter("blogFoot") : ""; // 博客 底部声明

		UserDao userDao = new UserDao();
		User user = new User();
		if (operation.trim().equals(Operation.modify.toString())) {// 修改信息
			// 判断用户是否登录
			if (!Tools.isLogin(req)) {
				resp.sendRedirect("/login.jsp");
				return;
			} else {
				user.setpTitle(pTitle);
				user.setName(name);
				user.setCtitle(ctitle);
				user.setNotice(notice);
				user.setEmail(email);
				user.setPassword(password);
				user.setUrl(url);
				user.setAddress(address);
				user.setBrithday(brithday);
				user.setDescription(description);
				user.setStyle(style);

				user.setIsWeather(isWeather);
				user.setIsCalendars(isCalendars);
				user.setIsHotBlog(isHotBlog);
				user.setIsNewReply(isNewReply);
				user.setIsLeaveMessage(isLeaveMessage);
				user.setIsStatistics(isStatistics);
				user.setIsFriends(isFriends);
				user.setIsInfo(isInfo);
				user.setIsTags(isTags);
				user.setIsType(isType);

				user.setPreMessage(new com.google.appengine.api.datastore.Text(
						Tools.changeHTML(Tools.toChinese(preMessage))));
				user.setMenu(new com.google.appengine.api.datastore.Text(
						Tools.changeHTML(Tools.toChinese(blogMenu))));

				user.setBlogDescription(blogDescription);
				user.setBlogKeyword(blogKeyword);
				// 2011-10-28 添加顶部和底部代码
				user.setBlogHead(blogHead);
				user.setBlogFoot(blogFoot);

				user.setIsUpload(isUpload);
				userDao.operationUser(Operation.modify, user);
//				req.setAttribute("user", user);
//				req.getRequestDispatcher("/admin/setting.jsp").forward(req,resp);
				resp.sendRedirect("/admin/setting.jsp");
			}
		} else if (operation.trim().equals(Operation.add.toString())) {
			// 初次安装使用 创建用户信息
			user.setpTitle("java博客");
			url = req.getServerName();
			user.setUrl(url);
			user.setStyle(Config.getStyle_url());// 设置默认样式
			user.setName(name);
			user.setPassword(password);

			userDao.operationUser(Operation.add, user);
//			req.setAttribute("user", user);
//			req.getRequestDispatcher("/index.jsp").forward(req, resp);
			resp.sendRedirect("/admin/setting.jsp");

		} else {// 登录
			if (name != null && !"".equals(name.trim())) {
				if (password != null && !"".equals(password.trim())) {
					user = userDao.getUserByName(name);
					if (user != null) {
						if (user.getPassword().equals(password.trim())) {
							req.getSession().setAttribute("login_user", user);
							resp.sendRedirect("/admin/");
						} else {
							req.setAttribute("error", "密码错误");
							req.getRequestDispatcher("/login.jsp").forward(req,
									resp);
						}
					} else {
						req.setAttribute("error", "用户名错误");
						req.getRequestDispatcher("/login.jsp").forward(req,
								resp);
					}
				} else {
					req.setAttribute("error", "请输入密码");
					req.getRequestDispatcher("/login.jsp").forward(req, resp);
				}
			} else {
				req.setAttribute("error", "请输入用户名");
				req.getRequestDispatcher("/login.jsp").forward(req, resp);
			}
		}
	}
}
