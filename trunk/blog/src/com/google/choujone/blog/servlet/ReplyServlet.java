package com.google.choujone.blog.servlet;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.choujone.blog.common.Operation;
import com.google.choujone.blog.dao.BlogDao;
import com.google.choujone.blog.dao.ReplyDao;
import com.google.choujone.blog.entity.Blog;
import com.google.choujone.blog.entity.Reply;
import com.google.choujone.blog.util.Tools;

@SuppressWarnings("serial")
public class ReplyServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String operation = req.getParameter("op") != null ? req
				.getParameter("op") : "";// 获取操作
		String ids = req.getParameter("bid");// 博客id
		String id = req.getParameter("id") != null ? req.getParameter("id")
				: "";// 回复
		String repyMsg = req.getParameter("msg");
		ReplyDao replyDao = new ReplyDao();
		Reply reply = new Reply();
		if (operation.trim().equals(Operation.delete.toString())) {// 删除
			reply.setId(Long.valueOf(ids));
			replyDao.operationReply(Operation.delete, reply);
			resp.sendRedirect("/admin/reply_list.jsp");
		} else if (operation.trim().equals(Operation.modify.toString())) {// 加载修改(页面直接用url请求)
			reply.setId(Tools.strTolong(id));
			reply.setReplyMessage(repyMsg);
			reply.setReplyTime(Tools.changeTime(new Date()));
			replyDao.operationReply(Operation.modify, reply);
			resp.sendRedirect("/admin/reply_list.jsp");
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String operation = req.getParameter("op") != null ? req
				.getParameter("op") : "";// 获取操作
		String content = req.getParameter("content");// 文章信息
		String name = req.getParameter("name");// 署名
		String id = req.getParameter("id") != null ? req.getParameter("id")
				: "";// 回复
		String bid = req.getParameter("bid") != null ? req.getParameter("bid")
				: "";// 文章id
		String email = req.getParameter("email");// email
		String url = req.getParameter("url");// email
		String repyMsg = req.getParameter("msg");
		ReplyDao replyDao = new ReplyDao();
		Reply reply = new Reply();
		if (operation.trim().equals(Operation.add.toString())) {// 新增
			reply.setEmail(email);
			reply.setSdTime(Tools.changeTime(new Date()));
			reply.setName(name);
			reply.setUrl(url);
			reply.setBid(Tools.strTolong(bid));
			reply.setContent(content);
			replyDao.operationReply(Operation.add, reply);
			if (reply.getBid()>0) {
				BlogDao blogDao=new BlogDao();
				blogDao.operationBlog(Operation.replyTimes, new Blog(reply.getBid()));
			}
			
			if (reply.getBid() > 0) {
				resp.sendRedirect("/blog_detail.jsp?id=" + reply.getBid());
			} else {
				resp.sendRedirect("/leaveMessage.jsp");
			}
		} else if (operation.trim().equals(Operation.lists.toString())) {// 评论列表
			System.out.println("请求一下");
			req.setAttribute("reply", reply);
		}
	}
}
