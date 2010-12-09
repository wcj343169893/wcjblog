package com.google.choujone.blog.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.choujone.blog.common.Operation;
import com.google.choujone.blog.dao.UserDao;
import com.google.choujone.blog.entity.User;
import com.google.choujone.blog.util.Config;

@SuppressWarnings("serial")
public class UserServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String operation = req.getParameter("op") != null ? req
				.getParameter("op") : "";// 获取操作
		UserDao userDao = new UserDao();
		User user = new User();
		// if (operation.trim().equals(Operation.delete.toString())) {// 删除
		// // reply.setId(Long.valueOf(ids));
		// // replyDao.operationReply(Operation.delete, reply);
		// // resp.sendRedirect("/admin/reply_list.jsp");
		// } else if (operation.trim().equals(Operation.modify.toString())) {//
		// 加载用户信息
		// user = userDao.getUserDetail();
		// if (user == null) {
		// userDao.operationUser(Operation.add, null);// 新增用户
		// user = userDao.getUserDetail();
		// }
		// req.setAttribute("user", user);
		// req.getRequestDispatcher("/admin/setting.jsp").forward(req, resp);
		// }else{//注销
		req.getSession().removeAttribute("login_user");
		resp.sendRedirect("/");
		// }
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
		UserDao userDao = new UserDao();
		User user = new User();
		if (operation.trim().equals(Operation.modify.toString())) {// 修改信息
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
			userDao.operationUser(Operation.modify, user);
			req.setAttribute("user", user);
			req.getRequestDispatcher("/admin/setting.jsp").forward(req, resp);
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
