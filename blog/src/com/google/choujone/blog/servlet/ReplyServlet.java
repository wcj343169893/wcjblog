package com.google.choujone.blog.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.choujone.blog.common.Operation;
import com.google.choujone.blog.common.Pages;
import com.google.choujone.blog.dao.BlogDao;
import com.google.choujone.blog.dao.IpDao;
import com.google.choujone.blog.dao.ReplyDao;
import com.google.choujone.blog.entity.Blog;
import com.google.choujone.blog.entity.Ip;
import com.google.choujone.blog.entity.Reply;
import com.google.choujone.blog.util.Mail;
import com.google.choujone.blog.util.Tools;

@SuppressWarnings("serial")
public class ReplyServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String operation = req.getParameter("op") != null ? req
				.getParameter("op") : "";// 获取操作
		String bid = req.getParameter("bid");// 博客编号
		String p = req.getParameter("p") != null ? req.getParameter("p") : "1";// 页码
		String ids = req.getParameter("ids");// 评论编号列表
		String id = req.getParameter("id") != null ? req.getParameter("id")
				: "";// 回复
		String repyMsg = req.getParameter("msg");
		ReplyDao replyDao = new ReplyDao();
		Reply reply = new Reply();
		resp.setCharacterEncoding("UTF-8");
		if (operation.trim().equals(Operation.delete.toString())) {// 删除
			// reply.setId(Long.valueOf(ids));
			replyDao.deleteReply(ids);
			resp.sendRedirect("/admin/reply_list.jsp");
		} else if (operation.trim().equals(Operation.modify.toString())) {// 加载修改(页面直接用url请求)
			reply.setId(Tools.strTolong(id));
			reply.setReplyMessage(repyMsg);
			reply.setReplyTime(Tools.changeTime(new Date()));
			replyDao.operationReply(Operation.modify, reply);
			// 发送短信到留言者邮箱中
			if (Tools.isEmail(reply.getEmail())) {
				String title = "网站留言";
				String content = "<div>原信息：" + reply.getContent()
						+ "<div>留言时间：" + reply.getSdTime() + "</div>"
						+ "</div><hr/>" + "<div>管理员回复:"
						+ reply.getReplyMessage() + "<div>回复时间："
						+ reply.getReplyTime() + "</div>" + "</div>";
				String url = "http://www.choujone.com/leaveMessage.jsp";
				if (reply.getBid() > 0) {
					BlogDao bd = new BlogDao();
					Blog blog = bd.getBlogById(reply.getBid());
					title = blog.getTitle();
					url = "http://www.choujone.com/blog/" + reply.getBid();
				}
				content += "<div>链接：<a href='" + url + "'>" + url
						+ "</a></div>";
				Mail.send(title, content, reply.getEmail(), reply.getName());
			}
			resp.sendRedirect("/admin/reply_list.jsp");
		} else if (operation.trim().equals(Operation.clearCache.toString())) {// 清理缓存

		} else {
			Long bid_long = -1L;
			if (bid != null) {
				bid_long = Long.valueOf(bid);
			}

			// resp.setContentType("text/html;charset=utf-8");
			resp.setContentType("application/json");
			resp.setCharacterEncoding("UTF-8");
			resp.setHeader("Cache-Control", "no-cache");
			PrintWriter out = resp.getWriter();
			int page = Integer.parseInt(p);
			Pages pages = new Pages();
			pages.setPageNo(page);
			List<Reply> replyList = replyDao.getReplyListByBid(bid_long, pages);
			JSONArray ja = new JSONArray();
			if (replyList != null) {
				for (Reply r : replyList) {
					JSONObject obj = new JSONObject();
					obj.put("replyTime", r.getReplyTime());
					obj.put("sdTime", r.getSdTime());
					obj.put("replyMessage", r.getReplyMessage());
					obj.put("content", r.getContent());
					obj.put("name", r.getName());
					obj.put("url", r.getUrl());
					ja.add(obj);
				}
			}
			out.print(ja.toJSONString());
			// }else{
			// resp.sendRedirect("/");
			// }
		}
		// 清理缓存
		// String key = "replyDao_bid_" + bid + "_" + p;// 更新前台
		// MyCache.clear(key);
		// key = "replyDao_getReplyList_all_" + p;// 更新后台
		// MyCache.clear(key);
		// MyCache.updateList(key, reply);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String operation = req.getParameter("op") != null ? req
				.getParameter("op") : "";// 获取操作
		String content = req.getParameter("content") != null ? req
				.getParameter("content") : "";// 文章信息
		String title = req.getParameter("title") != null ? req
				.getParameter("title") : "网站留言";// 文章信息

		String name = req.getParameter("name") != null ? req
				.getParameter("name") : "";// 署名
		if (name == null || "".equals(name.trim()) || !name.equals("choujone")) {
			name = "匿名";
			return;//不允许评论
		}
		String id = req.getParameter("id") != null ? req.getParameter("id")
				: "";// 回复
		String bid = req.getParameter("bid") != null ? req.getParameter("bid")
				: "-1L";// 文章id
		String email = req.getParameter("email") != null ? req
				.getParameter("email") : "";// email
		String url = req.getParameter("url") != null ? req.getParameter("url")
				: "";// email
		// 保存游客信息到cookies
		Cookie[] cookies = req.getCookies();
		boolean isCookied = false;
		try {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("gustName")) {
					name = URLDecoder.decode(cookie.getValue(), "UTF-8");
					isCookied = true;
				} else if (cookie.getName().equals("gustEmail")) {
					email = URLDecoder.decode(cookie.getValue(), "UTF-8");
				} else if (cookie.getName().equals("gustURL")) {
					url = URLDecoder.decode(cookie.getValue(), "UTF-8");
				}
			}
		} catch (Exception e) {
			req.getSession().setAttribute("errorMsg", "请登录");
			if (Tools.strTolong(bid) > 0) {
				resp.sendRedirect("/blog/" + Tools.strTolong(bid));
			} else {
				resp.sendRedirect("/leaveMessage.jsp");
				bid = "-1";
			}
			return;
		}
		if (!isCookied) {
			Cookie gustName = new Cookie("gustName", URLEncoder.encode(name,
					"UTF-8"));
			Cookie gustEmail = new Cookie("gustEmail", URLEncoder.encode(email,
					"UTF-8"));
			Cookie gustURL = new Cookie("gustURL", URLEncoder.encode(url,
					"UTF-8"));
			resp.setCharacterEncoding("UTF-8");
			resp.addCookie(gustName);
			resp.addCookie(gustEmail);
			resp.addCookie(gustURL);
			isCookied = true;
		}
		// 判断是否使用google账号登录
		String google_id = "";
		if (!isCookied) {
			try {
				UserService userService = UserServiceFactory.getUserService();
				req.getSession().setAttribute("errorMsg", "");
				if (!userService.isUserLoggedIn()) {
					req.getSession().setAttribute("errorMsg", "请登录");
					if (Tools.strTolong(bid) > 0) {
						resp.sendRedirect("/blog/" + Tools.strTolong(bid));
					} else {
						resp.sendRedirect("/leaveMessage.jsp");
						bid = "-1";
					}
					return;
				}
				google_id = userService.getCurrentUser().getUserId();
				email = userService.getCurrentUser().getEmail();
				name = userService.getCurrentUser().getNickname();
				url = userService.getCurrentUser().getAuthDomain();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		// String repyMsg = req.getParameter("msg");
		String p = req.getParameter("p") != null ? req.getParameter("p") : "1";
		ReplyDao replyDao = new ReplyDao();
		Reply reply = new Reply();

		if (operation.trim().equals(Operation.add.toString())) {// 新增
			IpDao ipdao = new IpDao();
			String ipAddress = Tools.getIpAddr(req);
			List<Ip> ips = ipdao.getIpList();
			if (ips != null && ipAddress != null) {
				for (Ip ip : ips) {
					if (Tools.isRightIp(ip.getAddress(), ipAddress)) {
						content = null;
						break;
					}
				}
			}
			// 判断内容是否为空(2014-02-17 禁止网站留言)
			if (content == null || "".equals(content.trim())) {
				if (Tools.strTolong(bid) > 0) {
					resp.sendRedirect("/blog/" + Tools.strTolong(bid));
				} else {
					resp.sendRedirect("/leaveMessage.jsp");
					bid = "-1";
				}
				return;
			}
			reply.setEmail(email);
			reply.setSdTime(Tools.changeTime(new Date()));
			reply.setName(name);
			reply.setUrl(url);
			reply.setBid(Tools.strTolong(bid));
			// 评论内容 过滤
			reply.setContent(Tools.FilterHTML(content));
			// reply.setContent2(new Text(content));
			// 获取到留言者的信息
			reply.setVisiter(req.getRemoteAddr() + ";"
					+ Tools.getAddressByIP(Tools.getIpAddr(req)) + ";"
					+ req.getHeader("user-agent"));
			replyDao.operationReply(Operation.add, reply);
			// 发送 邮件到邮箱
			String webUrl = "http://www.choujone.com/leaveMessage.jsp";
			if (reply.getBid() > 0) {
				webUrl = "http://www.choujone.com/blog/" + reply.getBid();
			}
			Mail.send(title,
					reply.getContent() + "<br/><hr/><div>留言地址<a href='"
							+ webUrl + "'>" + webUrl + "</a></div><div>访客信息:"
							+ reply.getName() + "</div><div>google userid:"
							+ google_id + "<br/>" + reply.getEmail() + "<br/>"
							+ reply.getUrl() + "<br/>" + reply.getSdTime()
							+ "<br/>" + reply.getVisiter() + "<br/><br/></div>");
			if (reply.getBid() > 0) {
				BlogDao blogDao = new BlogDao();
				blogDao.operationBlog(Operation.replyTimes,
						new Blog(reply.getBid()));
				resp.sendRedirect("/blog/" + reply.getBid());
			} else {
				resp.sendRedirect("/leaveMessage.jsp");
				bid = "-1";
			}
			// 清理前台缓存
			// String key = "replyDao_bid_" + bid + "_" + p;
			// MyCache.clear(key);
			// //清理后台缓存
			// key="replyDao_getReplyList_all_"+1;
			// MyCache.clear(key);
			// 更新统计

		} else if (operation.trim().equals(Operation.lists.toString())) {// 评论列表
			// System.out.println("请求一下");// 功能未完成
			// req.setAttribute("reply", reply);
			resp.setContentType("text/html;charset=utf-8");
			resp.setCharacterEncoding("UTF-8");
			resp.setHeader("Cache-Control", "no-cache");
			PrintWriter out = resp.getWriter();
			int page = Integer.parseInt(p);
			Pages pages = new Pages();
			pages.setPageNo(page);
			List<Reply> replyList = replyDao.getReplyListByBid(
					Long.valueOf(bid), pages);
			JSONObject obj = new JSONObject();
			obj.put("url", url);
			out.print(obj.toJSONString());
		}
	}
}
