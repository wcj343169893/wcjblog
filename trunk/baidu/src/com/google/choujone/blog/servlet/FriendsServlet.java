package com.google.choujone.blog.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import com.google.choujone.blog.common.Operation;
import com.google.choujone.blog.dao.FriendsDao;
import com.google.choujone.blog.entity.Friends;
import com.google.choujone.blog.util.MyCache;
import com.google.choujone.blog.util.Tools;

@SuppressWarnings("serial")
public class FriendsServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String operation = req.getParameter("op") != null ? req
				.getParameter("op") : "";// 获取操作
		Long id = req.getParameter("id") != null ? Tools.strTolong(req
				.getParameter("id")) : -1L;// 获取编号
		String ids = req.getParameter("ids");// 友情链接id数组
		FriendsDao friendsDao = new FriendsDao();
		Friends friends = new Friends();
		friends.setId(id);
		if (operation.trim().equals(Operation.delete.toString())) {// 删除
			if (ids != null) {
				String[] id_str = ids.split(",");
				for (int i = 0; i < id_str.length; i++) {
					Long bid = Long.valueOf(id_str[i].trim());
					if (bid > 0) {
						friends.setId(bid);
						friendsDao.operationFriends(Operation.delete, friends);
					}
				}
			}
			req.getRequestDispatcher("/admin/friends_list.jsp").forward(req,
					resp);
		} else if (operation.trim().equals(Operation.modify.toString())) {// 加载修改
			friends = friendsDao.getFriendsById(id);
			// req.setAttribute("friends", friends);
			// req.getRequestDispatcher("/admin/friends_list.jsp").forward(req,
			// resp);
			// ajax获取内容json数据
			resp.setContentType("application/json");
			resp.setCharacterEncoding("UTF-8");
			resp.setHeader("Cache-Control", "no-cache");
			PrintWriter out = resp.getWriter();
			JSONObject obj = new JSONObject();
			obj.put("id", friends.getId());
			obj.put("tid", friends.getTid());
			obj.put("description", friends.getDescription());
			obj.put("istop", friends.getIstop() != null ? friends.getIstop()
					: 0);
			obj.put("name", friends.getName());
			obj.put("url", friends.getUrl());
			out.print(obj.toJSONString());
		} else if (operation.trim().equals(Operation.clearCache.toString())) {// 清理缓存
			String key = "friendsDao_getFriendsByPage_1_10";
			MyCache.clear(key);
			req.getRequestDispatcher("/admin/friends_list.jsp").forward(req,
					resp);
		} else if (operation.trim().equals(Operation.ttop.toString())) {// 推荐
			if (id > 0) {
				friendsDao.operationFriends(Operation.ttop, friends);
			}
			if (ids != null) {
				String[] id_str = ids.split(",");
				for (int i = 0; i < id_str.length; i++) {
					Long bid = Long.valueOf(id_str[i].trim());
					if (bid > 0) {
						friends.setId(bid);
						friendsDao.operationFriends(Operation.ttop, friends);
					}
				}
			}
			req.getRequestDispatcher("/admin/friends_list.jsp").forward(req,
					resp);
		} else if (operation.trim().equals(Operation.dtop.toString())) {// 取消推荐
			if (id > 0) {
				friendsDao.operationFriends(Operation.dtop, friends);
			}
			if (ids != null) {
				String[] id_str = ids.split(",");
				for (int i = 0; i < id_str.length; i++) {
					Long bid = Long.valueOf(id_str[i].trim());
					if (bid > 0) {
						friends.setId(bid);
						friendsDao.operationFriends(Operation.dtop, friends);
					}
				}
			}
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
		Integer tid = req.getParameter("tid") != null ? Integer.parseInt(req
				.getParameter("tid")) : 1;// 友情链接分类
		Integer istop = req.getParameter("istop") != null ? Integer
				.parseInt(req.getParameter("istop")) : 0;// 是否置顶
		String description = req.getParameter("description");// 
		FriendsDao friendsDao = new FriendsDao();
		Friends friends = new Friends();
		friends.setName(name);
		friends.setUrl(url);
		friends.setTid(tid);
		friends.setIstop(istop);
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
