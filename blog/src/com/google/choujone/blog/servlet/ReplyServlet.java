package com.google.choujone.blog.servlet;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.Text;
import com.google.choujone.blog.common.Operation;
import com.google.choujone.blog.dao.BlogDao;
import com.google.choujone.blog.dao.ReplyDao;
import com.google.choujone.blog.entity.Blog;
import com.google.choujone.blog.entity.Reply;
import com.google.choujone.blog.util.Mail;
import com.google.choujone.blog.util.MyCache;
import com.google.choujone.blog.util.Tools;

@SuppressWarnings("serial")
public class ReplyServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String operation = req.getParameter("op") != null ? req
				.getParameter("op") : "";// 获取操作
		String bid = req.getParameter("bid");// 博客编号
		String p = req.getParameter("p");// 页码
		String ids = req.getParameter("ids");// 评论编号列表
		String id = req.getParameter("id") != null ? req.getParameter("id")
				: "";// 回复
		String repyMsg = req.getParameter("msg");
		ReplyDao replyDao = new ReplyDao();
		Reply reply = new Reply();
		if (operation.trim().equals(Operation.delete.toString())) {// 删除
			// reply.setId(Long.valueOf(ids));
			replyDao.deleteReply(ids);
		} else if (operation.trim().equals(Operation.modify.toString())) {// 加载修改(页面直接用url请求)
			reply.setId(Tools.strTolong(id));
			reply.setReplyMessage(repyMsg);
			reply.setReplyTime(Tools.changeTime(new Date()));
			replyDao.operationReply(Operation.modify, reply);
			// resp.sendRedirect("/admin/reply_list.jsp");
		} else if (operation.trim().equals(Operation.clearCache.toString())) {// 清理缓存

		}
		// 清理缓存
//		String key = "replyDao_bid_" + bid + "_" + p;// 更新前台
//		MyCache.clear(key);
//		key = "replyDao_getReplyList_all_" + p;// 更新后台
//		MyCache.clear(key);
		// MyCache.updateList(key, reply);
		resp.sendRedirect("/admin/reply_list.jsp");
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String operation = req.getParameter("op") != null ? req
				.getParameter("op") : "";// 获取操作
		String content = req.getParameter("content");// 文章信息
		
		String name = req.getParameter("name");// 署名
		if (name == null || "".equals(name.trim())) {
			name = "匿名";
		}
		String id = req.getParameter("id") != null ? req.getParameter("id")
				: "";// 回复
		String bid = req.getParameter("bid") != null ? req.getParameter("bid")
				: "";// 文章id
		String email = req.getParameter("email");// email
		String url = req.getParameter("url");// email
//		String repyMsg = req.getParameter("msg");
		String p = req.getParameter("p") != null ? req.getParameter("p") : "1";
		ReplyDao replyDao = new ReplyDao();
		Reply reply = new Reply();
		
		if (operation.trim().equals(Operation.add.toString())) {// 新增
			//判断内容是否为空
			if(content==null || "".equals(content.trim())){
				if (Tools.strTolong(bid) > 0) {
					resp.sendRedirect("/blog_detail.jsp?id=" + Tools.strTolong(bid));
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
//			reply.setContent(content);
			reply.setContent2(new Text(content));
			// 获取到留言者的信息
			reply.setVisiter(req.getRemoteAddr() + ";"
					+ Tools.getAddressByIP(Tools.getIpAddr(req)) + ";"
					+ req.getHeader("user-agent"));
			replyDao.operationReply(Operation.add, reply);
			if (reply.getBid() > 0) {
				BlogDao blogDao = new BlogDao();
				blogDao.operationBlog(Operation.replyTimes, new Blog(reply
						.getBid()));
				resp.sendRedirect("/blog?id=" + reply.getBid());
			} else {
				resp.sendRedirect("/leaveMessage.jsp");
				bid = "-1";
			}
			//清理前台缓存
//			String key = "replyDao_bid_" + bid + "_" + p;
//			MyCache.clear(key);
//			//清理后台缓存
//			key="replyDao_getReplyList_all_"+1;
//			MyCache.clear(key);
			//更新统计
			
			//发送 邮件到邮箱
			Mail.send(bid,content);
		} else if (operation.trim().equals(Operation.lists.toString())) {// 评论列表
			System.out.println("请求一下");// 功能未完成
			req.setAttribute("reply", reply);
		}
	}
}
