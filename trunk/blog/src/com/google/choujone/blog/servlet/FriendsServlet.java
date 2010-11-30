package com.google.choujone.blog.servlet;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.choujone.blog.common.Operation;
import com.google.choujone.blog.dao.FriendsDao;
import com.google.choujone.blog.entity.Friends;
import com.google.choujone.blog.util.Tools;

@SuppressWarnings("serial")
public class FriendsServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String operation = req.getParameter("op") != null ? req
				.getParameter("op") : "";// 获取操作
		Long id = req.getParameter("id") != null ? Tools.strTolong(req
				.getParameter("id")) : -1L;// 获取编号
		FriendsDao friendsDao = new FriendsDao();
		Friends friends = new Friends();
		if (operation.trim().equals(Operation.delete.toString())) {// 删除
			friends.setId(id);
			friendsDao.operationFriends(Operation.delete, friends);
			req.getRequestDispatcher("/admin/friends_list.jsp").forward(req,
					resp);
		} else if (operation.trim().equals(Operation.modify.toString())) {// 加载修改
			friends = friendsDao.getFriendsById(id);
			req.setAttribute("friends", friends);
			req.getRequestDispatcher("/admin/friends_list.jsp").forward(req,
					resp);
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String operation = req.getParameter("op") != null ? req
				.getParameter("op") : "";// 获取操作
		String name = req.getParameter("name");// 
		Long id = req.getParameter("id") != null ? Tools.strTolong(req
				.getParameter("id")) : -1L;// 获取编号
		String url = req.getParameter("url");// 
		String description = req.getParameter("description");// 
		FriendsDao friendsDao = new FriendsDao();
		Friends friends = new Friends();
		friends.setName(name);
		friends.setUrl(url);
		friends.setDescription(description);
		if (operation.trim().equals(Operation.add.toString())) {// 新增
			friends.setSdTime(Tools.changeTime(new Date()));
			friendsDao.operationFriends(Operation.add, friends);
		} else if (operation.trim().equals(Operation.modify.toString())) {// 修改
			friends.setId(id);
			friendsDao.operationFriends(Operation.modify, friends);
		}
		resp.sendRedirect("/admin/friends_list.jsp");
	}
}
