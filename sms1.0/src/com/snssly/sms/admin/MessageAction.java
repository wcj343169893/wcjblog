package com.snssly.sms.admin;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.XMLOutputter;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.snssly.sms.commons.Config;
import com.snssly.sms.commons.Env;
import com.snssly.sms.commons.SendSms;
import com.snssly.sms.dao.ExamClazzSendDao;
import com.snssly.sms.dao.ExamDao;
import com.snssly.sms.dao.ExamScoresDAO;
import com.snssly.sms.dao.ExamSubjectDao;
import com.snssly.sms.dao.GradeDao;
import com.snssly.sms.dao.GroupsDao;
import com.snssly.sms.dao.MessageDao;
import com.snssly.sms.dao.MessageTypeDao;
import com.snssly.sms.dao.SendListDao;
import com.snssly.sms.dao.SubjectDao;
import com.snssly.sms.dao.UserDao;
import com.snssly.sms.entity.Exam;
import com.snssly.sms.entity.ExamClazzSend;
import com.snssly.sms.entity.ExamScores;
import com.snssly.sms.entity.ExamSubject;
import com.snssly.sms.entity.Grade;
import com.snssly.sms.entity.Groups;
import com.snssly.sms.entity.Message;
import com.snssly.sms.entity.MessageType;
import com.snssly.sms.entity.Sendlist;
import com.snssly.sms.entity.Subjects;
import com.snssly.sms.entity.User;

/**
 * 消息
 * 
 */
public class MessageAction {
	private MessageDao messageDao;
	private SendListDao sld;
	private UserDao userDao;
	private JSONObject json = new JSONObject();

	/**
	 * 后台查询所有
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws ServletException
	 */
	public String list(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		messageDao = new MessageDao();
		// 设置分页用到的变量
		Integer page = 0;
		Integer count = 10;
		Integer maxPage = 1;
		Integer maxCount = 0;
		String sc = "";// 搜索条件
		Integer ty = 0;// 类型
		String[] p = (String[]) request.getAttribute("params");
		if (p != null && p[0].matches("\\d+")) {
			page = Integer.valueOf(p[0]);
		}
		if (p != null && p.length > 1 && p[1].matches("\\d+")) {
			ty = Integer.valueOf(p[1]);
		} else {
			ty = Integer.valueOf(request.getParameter("ty") != null ? request
					.getParameter("ty") : "0");// 搜索类型
		}
		if (p != null && p.length > 2) {
			sc = p[2];
			sc = new String(sc.getBytes("ISO-8859-1"), "GBK");
		} else {
			sc = request.getParameter("sc") != null ? request
					.getParameter("sc").trim() : "";// 搜索条件
		}
		sc = Env.replaceHTML(sc);
		String condition = " where 1=1 ";
		if (sc != null && !"".equals(sc.trim())) {
			condition += " and (title like '%" + sc + "%' or content like '%"
					+ sc + "%')";
		}
		if (ty > 0) {
			condition += " and tid=" + ty + " ";
		}
		// 数据的总条数
		maxCount = messageDao.getAllCount(condition);
		List<Message> messageList = new ArrayList<Message>();
		if (maxCount > 0) {
			messageList = messageDao.getMessageList(page, count, condition);
		}
		maxPage = (maxCount / count) + (maxCount % count == 0 ? 0 : 1);
		// 返回页面的数据
		request.setAttribute("maxPage", maxPage);
		request.setAttribute("maxCount", maxCount);
		request.setAttribute("page", page);
		request.setAttribute("sc", URLEncoder.encode(sc, "GBK"));
		request.setAttribute("ty", ty);
		request.setAttribute("messageList", messageList);
		initData(request);
		return "base/messageAdmin";
	}

	/**
	 * 后台删除信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws ServletException
	 */
	public String delMessage(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		String[] p = (String[]) request.getAttribute("params");
		Integer mid = 0;
		if (p != null && p[0].matches("\\d+")) {
			mid = Integer.valueOf(p[0]);
		}
		messageDao = new MessageDao();
		messageDao.delete(mid, 1);
		return "base/message_list-1.html";
	}

	/**
	 * 后台恢复信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws ServletException
	 */
	public String recoveryAdmin(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		String[] p = (String[]) request.getAttribute("params");
		Integer mid = 0;
		if (p != null && p[0].matches("\\d+")) {
			mid = Integer.valueOf(p[0]);
		}
		messageDao = new MessageDao();
		messageDao.delete(mid, 0);
		return "base/message_list-1.html";
	}

	/**
	 * 后台新增，修改信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws ServletException
	 */
	public String updateMessage(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		initData(request);
		messageDao = new MessageDao();
		sld = new SendListDao();
		Integer mid = 0;
		Message message = null;
		List<Sendlist> slList = new ArrayList<Sendlist>();
		String[] p = (String[]) request.getAttribute("params");
		String userIds = request.getParameter("userIds");// 发送列表
		a: if (p != null && p[0].matches("\\d+")) {// 后台查询，显示信息
			mid = Integer.valueOf(p[0]);
			// 查询信息内容
			message = messageDao.findById(mid);
			slList = sld.findListByMid(mid);
			if (p != null && p.length > 1 && p[1].matches("\\d+")) {// 回复或转发
				userDao = new UserDao();
				User u = userDao.findUserById(message.getUid());
				// 1.回复，2.转发,3.回复全部
				Integer type = Integer.parseInt(p[1]);
				message.setId(null);
				if (type == 1) {
					slList = new ArrayList<Sendlist>();
					slList.add(new Sendlist(u.getId(), u.getMobile(), u
							.getNikeName()));
					message.setContent("");
					message.setTitle("回复:" + message.getTitle());
				} else if (type == 2) {
					slList = new ArrayList<Sendlist>();
					message.setTitle("转发:" + message.getTitle());
				} else if (type == 3) {
					message.setTitle("");
					message.setContent("");
				}
				break a;
			}
			// 进入查看详细页面
			request.setAttribute("message", message);
			request.setAttribute("slList", slList);
			return "base/messageDetail";
		} else if (userIds != null) {// 后台新增，修改信息
			// 获取登录用户
			User user = (User) request.getSession().getAttribute(
					Config.LOGIN_SESSION);
			mid = Integer.parseInt(request.getParameter("id") != null
					&& !"".equals(request.getParameter("id").trim()) ? request
					.getParameter("id") : "0");
			String title = request.getParameter("title");// 主题
			String content = request.getParameter("content");// 发送内容
			String mt = request.getParameter("mt");// 消息类型
			String isSend = request.getParameter("isSend") != null ? request
					.getParameter("isSend") : "0";// 发送还是草稿
			message = new Message();
			message.setId(mid);
			message.setTitle(title);
			message.setUid(user.getId());
			message.setIsSend(Integer.parseInt(isSend));//
			message.setContent(content);
			message.setTid(Integer.parseInt(mt != null ? mt : "0"));
			message.setCreateTime(new Date());
			// 没有修改这个说法耶，我看这个操作还是不要了吧
			// if (message.getId() > 0) {
			// messageDao.update(message);
			// // 更新发送列表
			// // 删除原有的列表信息
			// // sld.delete(mid);
			// } else {
			// message.setId(messageDao.add(message));
			// }
			message.setId(messageDao.add(message));
			// 增加发送列表
			addLinkMan(message, slList, userIds);
			// 跳转到消息列表
			return "base/message_list.html";
		}// else进入新增信息
		request.setAttribute("message", message);
		request.setAttribute("slList", slList);
		return "base/messageUpdate";
	}

	/**
	 * 添加发送列表
	 * 
	 * @param message
	 * @param slList
	 * @param userIds
	 */
	private void addLinkMan(Message message, List<Sendlist> slList,
			String userIds) {
		// 创建发送列表
		String[] uid = userIds.split(",");
		Sendlist sl = null;
		for (int i = 0; i < uid.length; i++) {
			if (uid[i] != null && !"".equals(uid[i].trim())) {
				sl = new Sendlist();
				sl.setUid(Integer.parseInt(uid[i]));
				sl.setSendTime(new Date());
				sl.setStatus(1);
				sl.setMid(message.getId());
				slList.add(sl);
			}
		}
		sld.add(slList);
	}

	/**
	 * 加载数据
	 * 
	 * @param request
	 */
	private List<MessageType> initData(HttpServletRequest request) {
		MessageTypeDao mtd = new MessageTypeDao();
		List<MessageType> messageTypeList = mtd.getMessageTypeList();
		request.setAttribute("messageTypeList", messageTypeList);
		return messageTypeList;
	}

	/**
	 * 前台查看消息详细
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws ServletException
	 */
	public String detail(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		initData(request);
		messageDao = new MessageDao();
		sld = new SendListDao();
		Integer mid = 0;
		Message message = null;
		List<Sendlist> slList = new ArrayList<Sendlist>();
		Sendlist sl = null;
		String[] p = (String[]) request.getAttribute("params");
		if (p != null && p[0].matches("\\d+")) {// 显示信息
			mid = Integer.valueOf(p[0]);
			// 查询信息内容
			message = messageDao.findById(mid);
			slList = sld.findListByMid(mid);
			User user = (User) request.getSession().getAttribute(
					Config.LOGIN_SESSION);
			// 如果是父母登录，查询子女的信息
			List<User> childList = new ArrayList<User>();
			if (user.getRid() == 4) {
				userDao = new UserDao();
				childList = userDao.getUserByPuid(user.getId());
			} else {
				childList.add(user);
			}
			for (int i = 0; i < slList.size(); i++) {// 更新消息阅读状态
				if (slList.get(i).getUid() == user.getId()) {
					sl = slList.get(i);
					sl.setIsRead(1);
					sld.updateRead(sl);// 更新阅读状态
				}
			}
			if (message.getTid() == 3) {// 如果消息为成绩
				String content = message.getContent();
				// 删除隐含信息
				message.setContent("");
				for (int i = 0; i < slList.size(); i++) {
					for (int j = 0; j < childList.size(); j++) {
						if (childList.get(j).getId() == slList.get(i).getUid()) {
							// 如果消息为成绩，则查询这个接收者的成绩
							message.setContent(message.getContent()
									+ getMessageByExam(content, childList
											.get(j).getId(), childList.get(j)
											.getCid()) + "<br/>");
						}
					}
				}
			}
			// 进入查看详细页面
			request.setAttribute("sl", sl);
			request.setAttribute("message", message);
			request.setAttribute("slList", slList);
			return "message/messageDetail";
		}
		return "message_inbox.html";
	}

	/**
	 * 收件箱
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws ServletException
	 */
	public String inbox(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		MessageDao messageDao = new MessageDao();
		User user = (User) request.getSession().getAttribute(
				Config.LOGIN_SESSION);
		String[] p = (String[]) request.getAttribute("params");// 分页
		Integer page = 0;
		Integer count = 10;
		Integer maxPage = 1;
		Integer maxCount = 0;
		// 判断传入的page
		if (p != null && p[0].matches("\\d+")) {
			page = Integer.valueOf(p[0]);
		}
		List<Message> messageList = null;
		// 查询总条数
		maxCount = messageDao.findInCount(user.getId());
		if (maxCount > 0) {
			messageList = messageDao.findInByPage(page, count, user.getId());
		}
		maxPage = (maxCount / count) + (maxCount % count == 0 ? 0 : 1);
		request.setAttribute("maxPage", maxPage);
		request.setAttribute("maxCount", maxCount);
		request.setAttribute("page", page);
		request.setAttribute("messageList", messageList);
		return "message/inbox";
	}

	/**
	 * 发件箱
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws ServletException
	 */
	public String outbox(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		MessageDao messageDao = new MessageDao();
		User user = (User) request.getSession().getAttribute(
				Config.LOGIN_SESSION);
		String[] p = (String[]) request.getAttribute("params");// 分页
		Integer page = 0;
		Integer count = 10;
		Integer maxPage = 1;
		Integer maxCount = 0;
		// 判断传入的page
		if (p != null && p[0].matches("\\d+")) {
			page = Integer.valueOf(p[0]);
		}
		List<Message> messageList = null;
		// 查询总条数
		maxCount = messageDao.findOutCount(user.getId());
		if (maxCount > 0) {
			messageList = messageDao.findOutByPage(page, count, user.getId());
		}
		maxPage = (maxCount / count) + (maxCount % count == 0 ? 0 : 1);
		request.setAttribute("maxPage", maxPage);
		request.setAttribute("maxCount", maxCount);
		request.setAttribute("page", page);
		request.setAttribute("messageList", messageList);
		return "message/outbox";
	}

	/**
	 * 草稿箱-->编辑信息-->发送
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws ServletException
	 */
	public String draft(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		MessageDao messageDao = new MessageDao();
		User user = (User) request.getSession().getAttribute(
				Config.LOGIN_SESSION);
		String[] p = (String[]) request.getAttribute("params");// 分页
		Integer page = 0;
		Integer count = 10;
		Integer maxPage = 1;
		Integer maxCount = 0;
		// 判断传入的page
		if (p != null && p[0].matches("\\d+")) {
			page = Integer.valueOf(p[0]);
		}
		List<Message> messageList = null;
		// 查询总条数
		maxCount = messageDao.findDraftCount(user.getId());
		if (maxCount > 0) {
			messageList = messageDao.findDraftByPage(page, count, user.getId());
		}
		maxPage = (maxCount / count) + (maxCount % count == 0 ? 0 : 1);
		request.setAttribute("maxPage", maxPage);
		request.setAttribute("maxCount", maxCount);
		request.setAttribute("page", page);
		request.setAttribute("messageList", messageList);
		return "message/draft";
	}

	/**
	 * 垃圾箱-->回收到发件箱或者收件箱
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws ServletException
	 */
	public String rubbish(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		MessageDao messageDao = new MessageDao();
		User user = (User) request.getSession().getAttribute(
				Config.LOGIN_SESSION);
		String[] p = (String[]) request.getAttribute("params");// 分页
		Integer page = 0;
		Integer count = 10;
		Integer maxPage = 1;
		Integer maxCount = 0;
		// 判断传入的page
		if (p != null && p[0].matches("\\d+")) {
			page = Integer.valueOf(p[0]);
		}
		List<Message> messageList = null;
		// 查询总条数
		maxCount = messageDao.findRubbishCount(user.getId());
		if (maxCount > 0) {
			messageList = messageDao.findRubbishByPage(page, count, user
					.getId());
		}
		maxPage = (maxCount / count) + (maxCount % count == 0 ? 0 : 1);
		request.setAttribute("maxPage", maxPage);
		request.setAttribute("maxCount", maxCount);
		request.setAttribute("page", page);
		request.setAttribute("messageList", messageList);
		return "message/rubbish";
	}

	/**
	 * 得到成绩信息
	 * 
	 * @param content
	 * @return
	 */
	private String getMessageByExam(String content, Integer uid, Integer cid) {
		Integer eid = getEid(content);
		String sids = getSids(content);
		ExamScoresDAO examScoresDAO = new ExamScoresDAO();
		ExamDao examDao = new ExamDao();
		UserDao userDao = new UserDao();
		User student = userDao.findUserById(uid);
		Exam exam = examDao.findById(eid);
		String msg = student.getNikeName() + ":";
		msg += exam.getName() + ",";
		List<ExamScores> examScoreList = examScoresDAO.getES(uid, eid, sids,
				false);// 得到这个学生的所有成绩
		// 构造成绩短信
		for (int j = 0; j < examScoreList.size(); j++) {
			ExamScores es = examScoreList.get(j);
			msg += es.getSname() + ":" + es.getScores();
			if (es.getRemark() != null && !"".equals(es.getRemark().trim())) {
				msg += es.getRemark().trim();
			}
			msg += ";";
		}
		msg = msg.substring(0, msg.length());
		// 判断发送是否发送总分,是否发送平均分,是否发送...
		msg += " 总分:" + examScoresDAO.getSumByUidEid(uid, eid);
		msg += " 平均分:" + examScoresDAO.getAvgByUidEid(uid, eid);
		msg += "班级名次:" + examScoresDAO.getRankByUidEid(uid, eid, cid);
		msg += " 年级名次:" + examScoresDAO.getRankByUidEid(uid, eid);
		return msg;
	}

	/**
	 * 发送信息（前台）
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
	public String add(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		Message message = (Message) request.getAttribute("form");
		User user = (User) request.getSession().getAttribute(
				Config.LOGIN_SESSION);
		messageDao = new MessageDao();
		MessageTypeDao mtd = new MessageTypeDao();
		MessageType messageType = mtd.findById(message.getTid());
		// 判断用户是否有发送此短信的权限
		if (messageType.getRidList().contains(user.getRid())) {
			List<Sendlist> sendlists = new ArrayList<Sendlist>();// 如果要构造这个数组，则需要先保存message
			// sendListDao.add(sendLists)
			// List<User> userList = userDao.findByIds(message.getUserIds());
			// 1.保存信息内容
			MessageDao messageDao = new MessageDao();
			SendListDao sendListDao = new SendListDao();
			message.setUid(user.getId());
			String ids = message.getUserIds();// 得到接受者的列表
			String[] id_str = ids.split(",");
			Sendlist sl = null;
			Date sendTime = message.getSendTime() != null
					&& !"".equals(message.getSendTime().toString().trim()) ? message
					.getSendTime()
					: new Date();
			message.setSendTime(sendTime);
			Integer mid = 0;
			// 判断消息主题是否为空
			if (message.getTitle() == null
					|| "".equals(message.getTitle().trim())) {
				if (message.getContent() != null
						&& !"".equals(message.getContent())) {
					message.setTitle(Env.replace(message.getContent(), 10));
				} else {
					message.setTitle("无主题");
				}
			}
			if (message.getId() != null && message.getId() > 0) {
				// 这个不包括重发,转发,只是草稿箱中需要发送的信息
				messageDao.update(message);// 修改消息内容,但是无法修改发送者编号
				mid = message.getId();
				// 移除发送列表,然后再重新添加发送列表
				sendListDao.delete2(mid);// 彻底删除
			} else {
				mid = messageDao.add(message);// 保存消息内容
				message.setId(mid);
			}
			String toUser = message.getToUser() != null ? message.getToUser()
					: "";
			userDao = new UserDao();
			for (int i = 0; i < id_str.length; i++) {
				if (id_str[i] != null && !"".equals(id_str[i].trim())) {
					Integer uid = Integer.parseInt(id_str[i].trim());
					addSendlist(message, sendlists, sendTime, mid, uid, 0);
					// 判断是否发送给了家长
					if (toUser.indexOf("0") != -1) {
						// 发送给家长
						// 查询父母的电话(也许有多个)
						List<User> parentList = userDao.getParentByUid(uid);
						for (User parent : parentList) {
							addSendlist(message, sendlists, sendTime, mid,
									parent.getId(), 1);
						}
					}
				}
			}
			if (message.getReplyId() != null && message.getReplyId() > 0) {// 修改回复状态
				sendListDao.updateReply(new Sendlist(message.getReplyId(), user
						.getId(), 1));
			}
			// isSend:发送/草稿
			// isMessage:短信/私人留言
			if (message.getIsMessage() != null && message.getIsMessage() == 1
					&& message.getIsSend() == 1
					&& messageType.getSmsRidList().contains(user.getRid())) {// 发送短信
				// TODO 调用发送短信
				new Send(message, sendlists, user, messageDao, sendListDao)
						.start();
			} else {// 私人留言
				sendListDao.add(sendlists);
				message.setStatus(1);
				messageDao.updateStatus(message);
			}
		}
		return "message/success";
	}

	/**
	 * 添加发送列表
	 * 
	 * @param message
	 * @param sendlists
	 * @param sendTime
	 * @param mid
	 * @param uid
	 * @param isParent
	 */
	private void addSendlist(Message message, List<Sendlist> sendlists,
			Date sendTime, Integer mid, Integer uid, Integer isParent) {
		for (int i = 0; i < sendlists.size(); i++) {
			if (sendlists.get(i).getUid() == uid) {
				return;
			}
		}
		Sendlist sl = new Sendlist();
		sl.setMid(mid);
		sl.setUid(uid);
		sl.setIsParent(isParent);
		sl.setSendTime(sendTime);
		sl.setStatus(message.getIsSend());
		sendlists.add(sl);
	}

	/**
	 * 发送短信 总线程
	 * 
	 */
	class Send extends Thread {
		private Message message;// 消息内容
		private List<Sendlist> sendList;// 接收者列表
		private User user;// 发送者编号
		private UserDao userDao;
		private MessageDao messageDao;// 信息处理
		private SendListDao sendListDao;// 发送列表处理

		public Send() {
		}

		public Send(Message message, List<Sendlist> sendList, User user,
				MessageDao messageDao, SendListDao sendListDao) {
			this.message = message;
			this.sendList = sendList;
			this.user = user;
			this.messageDao = messageDao;
			this.sendListDao = sendListDao;
		}

		@Override
		public void run() {
			// 2.判断发送短信的类型,然后根据页面传过来的选项，进行相应的操作
			userDao = new UserDao();
			// 判断短信接受者是家长还是学生,如果是家长，默认加上学生姓名
			// 获取定时发送时间
			String toUser = message.getToUser() != null ? message.getToUser()
					: "";
			if (message.getTid() == 3) {
				// 成绩
				String content = message.getContent();
				if (content != null && !"".equals(content.trim())) {
					// TODO 分析content 得到需要查询考试成绩
					Integer eid = getEid(content);
					if (eid > 0) {
						// 考试科目(所有科目)
						ExamScoresDAO examScoresDAO = new ExamScoresDAO();
						ExamDao examDao = new ExamDao();
						Exam exam = examDao.findById(eid);
						String sids = getSids(content);

						for (int i = 0; i < sendList.size(); i++) {// 这里的发送列表一定不是家长
							Sendlist sl = sendList.get(i);
							sl.setStatus(0);
							if (sl.getIsParent() != 0) {
								sl.setStatus(1);
								sendListDao.add(sl);
								continue;
							}
							String msg = "";
							Integer uid = sl.getUid();
							User child = userDao.findUserById(sl.getUid());
							List<String> mobileList = new ArrayList<String>();
							// 判断短信发送给家长还是学生
							if (toUser.indexOf("0") != -1) {
								// 发送给家长
								msg += child.getNikeName() + ",";// 得到家长子女的名字
								// 查询父母的电话(也许有多个)
								List<User> parentList = userDao
										.getParentByUid(child.getId());
								for (int j = 0; j < parentList.size(); j++) {
									// 判断家长的会员时间是否到期
									if (!Env.compareDate(parentList.get(j)
											.getMemberEndTime(), new Date())) {
										mobileList.add(parentList.get(j)
												.getMobile());
									}
								}
							}
							if (toUser.indexOf("1") != -1) {
								mobileList.add(child.getMobile());
							}
							sl.setMobileList(mobileList);
							msg += exam.getName() + ",";
							List<ExamScores> examScoreList = examScoresDAO
									.getES(uid, eid, sids, this.message
											.getZero() != 1);// 得到这个学生的所有成绩
							// System.out.println(examScoreList);
							// 构造成绩短信
							for (int j = 0; j < examScoreList.size(); j++) {
								ExamScores es = examScoreList.get(j);
								if (es.getScores() > 0
										|| message.getZero() != 0) {// 是否发送零分成绩
									msg += es.getSname() + ":" + es.getScores();
									// if (message.getDescription() == 1) {//
									// 发送备注
									// if (es.getRemark() != null
									// && !"".equals(es.getRemark()
									// .trim())) {
									// msg += es.getRemark().trim();
									// }
									// }
									msg += ";";
								}
							}
							msg = msg.substring(0, msg.length());
							// 判断发送是否发送总分,是否发送平均分,是否发送...
							if (message.getTotal() == 1) {// 发送总分
								msg += "总分:"
										+ examScoresDAO
												.getSumByUidEid(uid, eid);
							}
							if (message.getAvgs() == 1) {// 发送平均分
								msg += "平均分:"
										+ examScoresDAO
												.getAvgByUidEid(uid, eid);
							}
							// 备注判断，改为班级名次
							if (message.getDescription() == 1) {// 发送班级名次
								msg += "班级名次:"
										+ examScoresDAO.getRankByUidEid(uid,
												eid, child.getCid());
							}
							if (message.getRank() == 1) {// 发送年级名次
								msg += "年级名次:"
										+ examScoresDAO.getRankByUidEid(uid,
												eid);
							}
							msg = makeSignature(msg);
							sl.setMessage(msg);
							sendListDao.add(sl);
							// 判断用户的会员时间是否到期
							if (!Env.compareDate(child.getMemberEndTime(),
									new Date())) {
								new SendToUser(sl, sendListDao).start();
							}
						}
						ExamSubjectDao examSubjectDao = new ExamSubjectDao();
						String[] sid_str = sids.split(",");
						Integer status = 1;
						for (int i = 0; i < sid_str.length; i++) {
							if (sid_str[i] != null
									&& !"".equals(sid_str[i].trim())) {
								Integer sid = Integer.parseInt(sid_str[i]
										.trim());
								ExamSubject es = examSubjectDao.findByEidSid(
										eid, sid);// 得到考试科目中间表，用来插入到发送记录
								ExamClazzSend ecs = new ExamClazzSend(es
										.getId(), user.getId(), user.getCid(),
										message.getZero(), message.getAvgs(),
										message.getDescription(), message
												.getSendTime(), status);// 这个的发送状态和message的一致
								ExamClazzSendDao ecsd = new ExamClazzSendDao();
								ecsd.add(ecs);
							}
						}
						message.setStatus(status);

					}
				}
			} else {
				// 一般短信、作业、评语、祝福
				// 发送内容由用户直接输入
				for (int i = 0; i < sendList.size(); i++) {
					Sendlist sl = sendList.get(i);
					// 根据用户编号查询手机号码
					sendListDao.add(sl);
					// 判断短信发送给家长还是学生
					String msg = "";
					User child = userDao.findUserById(sl.getUid());
					List<String> mobileList = new ArrayList<String>();
					if (toUser != null && !"".endsWith(toUser.trim())) {
						if (toUser.indexOf("0") != -1) {
							// 判断接受者是不是学生
							if (child.getRid() != 3) {
								mobileList.add(child.getMobile());// 直接发送给列表用户
							} else {
								// 发送给学生家长
								msg += child.getNikeName() + ",";// 得到家长子女的名字
								// 查询父母的电话(也许有多个)
								List<User> parentList = userDao
										.getParentByUid(child.getId());
								for (int j = 0; j < parentList.size(); j++) {
									// 判断家长的会员时间是否到期
									if (!Env.compareDate(parentList.get(j)
											.getMemberEndTime(), new Date())) {
										mobileList.add(parentList.get(j)
												.getMobile());
									}
								}
							}
						}
						if (toUser.indexOf("1") != -1) {
							mobileList.add(child.getMobile());
						}
					} else {// 发送给一般人
						mobileList.add(child.getMobile());
					}
					msg += message.getContent();
					msg = makeSignature(msg);
					sl.setMessage(msg);
					sl.setMobileList(mobileList);
					// 判断用户的会员时间是否到期
					if (!Env.compareDate(child.getMemberEndTime(), new Date())) {
						new SendToUser(sl, sendListDao).start();
					}
					message.setStatus(1);
				}
			}
			messageDao.updateStatus(message);
		}

		private String makeSignature(String msg) {
			// 判断是否选择签名
			if (message.getIsSignature() != null
					&& message.getIsSignature() == 1) {
				msg += "--" + user.getNikeName();
			}
			return msg;
		}
	}

	/**
	 * 
	 * 单独发送每条信息
	 */
	class SendToUser extends Thread {
		private Sendlist sendList;
		private SendListDao sendListDao;

		public SendToUser(Sendlist sendList, SendListDao sendListDao) {
			this.sendList = sendList;
			this.sendListDao = sendListDao;
		}

		@Override
		public void run() {
			Date date = sendList.getSendTime();
			final Timer timer = new Timer(true);
			timer.schedule(new TimerTask() {
				@Override
				public void run() {
					// TODO 发送信息
					List<String> mobileList = sendList.getMobileList();
					Integer status = 1;
					if (sendList.getIsParent() != 1) {
						for (int i = 0; i < mobileList.size(); i++) {
							// 把特殊字符转回来
							String message = Env.replaceHTMLRe(sendList
									.getMessage());
							// 发送信息,得到发送状态
							status = SendSms.sendToSomeBody(message, mobileList
									.get(i));
							sendList.setStatus(status);// 设置发送状态
							// System.out.println("发送短信成功 手机号码："
							// + mobileList.get(i) + " 内容:" + message);
						}
					} else {
						sendList.setStatus(status);
					}
					sendListDao.updateStatus(sendList);// 更新发送状态
					timer.cancel();
				}
			}, date);
		}
	}

	/**
	 * 分析字符串，得到考试编号
	 * 
	 * @param content
	 * @return
	 */
	private Integer getEid(String content) {
		Integer eid = 0;
		String regEx = "exam\\d+";
		Pattern p = Pattern.compile(regEx);
		Matcher propsMatcher = p.matcher(content);
		if (propsMatcher.find()) {
			int startIndex = propsMatcher.start();
			int endIndex = propsMatcher.end();
			String currentMatch = content.substring(startIndex, endIndex);
			try {
				eid = Integer.parseInt(currentMatch.replaceAll("exam", ""));
			} catch (Exception e) {
				eid = 0;
			}
		}
		return eid;
	}

	/**
	 * 分析字符串，得到科目编号
	 * 
	 * @param content
	 * @return
	 */
	private String getSids(String content) {
		String sids = "";
		String regEx = "subject\\d+";
		Pattern p = Pattern.compile(regEx);
		Matcher propsMatcher = p.matcher(content);
		while (propsMatcher.find()) {
			int startIndex = propsMatcher.start();
			int endIndex = propsMatcher.end();
			String currentMatch = content.substring(startIndex, endIndex);
			sids += currentMatch.replaceAll("subject", ",");
		}
		if (sids.length() > 0) {
			return sids.substring(1, sids.length());
		}
		return sids;
	}

	/**
	 * 前台修改信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws ServletException
	 */
	public String modify(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		String[] p = (String[]) request.getAttribute("params");
		Integer mid = 0;// 消息编号
		Message message = null;
		if (p != null && p[0].matches("\\d+")) {// 后台查询，显示信息
			mid = Integer.valueOf(p[0]);
			messageDao = new MessageDao();
			message = messageDao.findById(mid);
		}
		request.setAttribute("message", message);
		return null;
	}

	/**
	 * 删除
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws ServletException
	 */
	public String delete(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		String toUrl = "message/message_inbox.html";
		User user = (User) request.getSession().getAttribute(
				Config.LOGIN_SESSION);
		// 得到mid数组例如 mids=1-2-3-4
		// 收件箱0，发件箱1/草稿箱2，垃圾箱3:
		String[] p = (String[]) request.getAttribute("params");
		if (p != null) {// 消息类型
			if (p[0].matches("\\d+")) {// 处理类型
				if (p[0].equals("0")) {// 删除收件箱中的信息
					SendListDao sendListDao = new SendListDao();
					for (int i = 1; i < p.length; i++) {
						if (p[i] != null && !"".equals(p[i])
								&& p[i].matches("\\d+")) {
							Integer id = Integer.parseInt(p[i]);
							sendListDao.updateRead(id, user.getId(), -1);
						}
					}
					toUrl = "message/message_inbox-1.html";
				} else if (p[0].equals("1")) {// 发件箱
					messageDao = new MessageDao();
					for (int i = 1; i < p.length; i++) {
						if (p[i] != null && !"".equals(p[i])
								&& p[i].matches("\\d+")) {
							Integer id = Integer.parseInt(p[i]);
							messageDao.delete(id, 1);
						}
					}
					toUrl = "message/message_outbox-1.html";
				} else if (p[0].equals("2")) {// 草稿箱
					messageDao = new MessageDao();
					for (int i = 1; i < p.length; i++) {
						if (p[i] != null && !"".equals(p[i])
								&& p[i].matches("\\d+")) {
							Integer id = Integer.parseInt(p[i]);
							messageDao.delete(id, 1);
						}
					}
					toUrl = "message/message_draft-1.html";
				} else if (p[0].equals("3")) {// 垃圾箱，彻底删除
					SendListDao sendListDao = new SendListDao();
					messageDao = new MessageDao();
					for (int i = 1; i < p.length; i++) {
						if (p[i] != null && !"".equals(p[i])
								&& p[i].matches("\\d+")) {
							// 判断消息的发送者是否是自己，如果是，则删除message，否则更新sendlist
							Integer id = Integer.parseInt(p[i]);
							Message message = messageDao.findById(id);
							if (message != null) {
								if (message.getUid() == user.getId()) {
									messageDao.delete(id, -1);
								} else {
									sendListDao
											.updateRead(id, user.getId(), -2);
								}
							}
						}
					}
					toUrl = "message/message_rubbish-1.html";
				}
			}
		}
		return toUrl;
	}

	/**
	 * 垃圾箱中恢复消息
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws ServletException
	 */
	public String recovery(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		User user = (User) request.getSession().getAttribute(
				Config.LOGIN_SESSION);
		String[] p = (String[]) request.getAttribute("params");
		if (p != null && p.length > 0) {
			messageDao = new MessageDao();
			SendListDao sendListDao = new SendListDao();
			for (int i = 0; i < p.length; i++) {
				if (p[i] != null && !"".equals(p[i]) && p[i].matches("\\d+")) {
					// 判断消息的发送者是否是自己，如果是，则删除message，否则更新sendlist
					Integer id = Integer.parseInt(p[i]);
					Message message = messageDao.findById(id);
					if (message != null) {
						if (message.getUid() == user.getId()) {
							messageDao.delete(id, 0);
						} else {
							sendListDao.updateRead(id, user.getId(), 1);
						}
					}
				}
			}
		}
		return "message/message_rubbish-1.html";
	}

	/**
	 * 彻底删除(没有用)
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws ServletException
	 */
	public String clear(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		// 得到mid数组例如 mids=1-2-3-4
		return null;
	}

	/**
	 * 加载 新增 信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws ServletException
	 */
	public String updateInit(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		UserDao userDao = new UserDao();
		GroupsDao groupsDao = new GroupsDao();
		GradeDao gradeDao = new GradeDao();
		// 消息类型，默认为第一个
		List<MessageType> messageTypeList = initData(request);
		Message message = null;
		List<Sendlist> slList = new ArrayList<Sendlist>();
		String[] p = (String[]) request.getAttribute("params");
		Integer tid = 0;// 消息类型
		Integer mid = 0;// 消息编号
		Integer function = 0;// 处理功能
		if (p != null && p[0].matches("\\d+")) {// 消息类型
			tid = Integer.valueOf(p[0]);
		}
		if (tid < 1) {
			if (messageTypeList != null && messageTypeList.size() > 0) {
				tid = messageTypeList.get(0).getId();
			}
		}
		if (p != null && p.length > 1 && p[1].matches("\\d+")) {// 消息编号
			mid = Integer.valueOf(p[1]);
			messageDao = new MessageDao();
			message = messageDao.findById(mid);// 查询消息详细
			// 查询发送列表
			SendListDao sldao = new SendListDao();
			slList = sldao.findListByMid(mid);
		}
		User user = (User) request.getSession().getAttribute(
				Config.LOGIN_SESSION);
		if (p != null && p.length > 2 && p[2].matches("\\d+")) {// 处理功能
			// TODO 1：编辑,2：回复，3：转发，4：重发，5：回复全部
			function = Integer.valueOf(p[2]);
			// if (function != 0 && function != 1 && function != 4) {
			// tid = 1;// 设置为一般信息
			// }
			// 判断用户是否拥有相同的消息类型message.getTid()
			MessageType mt = null;
			boolean isType = false;
			for (int i = 0; i < messageTypeList.size(); i++) {
				mt = messageTypeList.get(i);
				if (mt.getRid().indexOf(user.getRid() + "") != -1) {
					if (mt.getId().equals(tid)) {
						isType = true;
						break;
					}
				}
			}
			tid = isType ? tid : 1;
			if (function == 2) {// 回复
				message.setContent("");// 消息清空
				message.setTitle("回复:" + message.getTitle());// 修改标题
				message.setReplyId(message.getId());// 设置回复编号
				message.setId(0);// 删除消息编号
				slList = new ArrayList<Sendlist>();
				User user_send = userDao.findUserById(message.getUid());// 查询消息的发送者
				slList.add(new Sendlist(user_send.getId(), user_send
						.getMobile(), user_send.getNikeName()));
			} else if (function == 3) {// 转发,
				slList = new ArrayList<Sendlist>();// 清空联系人
				message.setTitle("转发:" + message.getTitle());
				message.setId(0);// 删除消息编号
			} else if (function == 4) {// 重发
				message.setTitle("重发:" + message.getTitle());
				message.setId(0);// 删除消息编号
			} else if (function == 5) {// 回复全部
				message.setTitle("回复:" + message.getTitle());
				message.setContent("");// 消息清空
				message.setReplyId(message.getId());// 设置回复编号
				message.setId(0);// 删除消息编号
			}
		}

		// 根据年级编号，角色编号和分组编号查询用户
		// 根据角色编号，分组编号和班级编号查询用户
		if (user.getRid() == 5) {
			// 领导加载教师
			List<Grade> gradeList = initTeacher(userDao, groupsDao, gradeDao);
			request.setAttribute("gradeList", gradeList);
		} else if (user.getRid() == 2) {
			// 教师加载学生
			List<Groups> groupsList = initStudent(user.getCid(), userDao,
					groupsDao);
			// List<Groups> parentList = initParent(user.getCid(), userDao,
			// groupsDao);
			// 教师加载领导
			List<User> leaderList = initLeader(userDao);
			request.setAttribute("groupsList", groupsList);
			// request.setAttribute("parentList", parentList);
			request.setAttribute("leaderList", leaderList);
		} else if (user.getRid() == 3) {
			// 学生加载教师
			List<User> teacherList = initTeacher(user.getCid(), userDao);
			request.setAttribute("teacherList", teacherList);
		} else if (user.getRid() == 4) {
			// 家长加载教师
			List<User> teacherList = initTeacherByParent(user.getId(), userDao);
			request.setAttribute("teacherList", teacherList);
		} else {// 管理员
			// 加载所有教师，领导
			List<Grade> gradeList = initTeacher(userDao, groupsDao, gradeDao);
			request.setAttribute("gradeList", gradeList);
			List<User> leaderList = initLeader(userDao);
			request.setAttribute("leaderList", leaderList);
		}
		Integer eid = 0;
		if (p != null && p.length > 3 && p[3].matches("\\d+")) {// 考试名称
			eid = Integer.parseInt(p[3]);
		}
		// TODO 所有用户都可以给管理员发送信息
		request.setAttribute("adminUserList", initAdmin(userDao));
		request.setAttribute("adminWebUserList", initWebAdmin(userDao));
		request.setAttribute("message", message);
		request.setAttribute("mid", mid);
		request.setAttribute("tid", tid);
		request.setAttribute("eid", eid);
		request.setAttribute("function", function);
		request.setAttribute("slList", slList);
		// 不同的类型，发送内容有所变动
		return "message/updateInit";
	}

	/**
	 * 查询住校管理员信息
	 * 
	 * @param userDao
	 * @return
	 */
	private List<User> initAdmin(UserDao userDao) {
		return userDao.findByRid(6);
	}

	/**
	 * 网站管理员
	 * 
	 * @param userDao
	 * @return
	 */
	private List<User> initWebAdmin(UserDao userDao) {
		return userDao.findByRid(1);
	}

	/**
	 * 领导发送短信给教师<br>
	 * 加载教师OK
	 */
	private List<Grade> initTeacher(UserDao userDao, GroupsDao groupsDao,
			GradeDao gradeDao) {
		List<Grade> gradeList = gradeDao.findAll();
		for (int i = 0; i < gradeList.size(); i++) {
			List<Groups> groupList = groupsDao.findGroupsByRidGid(2, gradeList
					.get(i).getId());
			for (int j = 0; j < groupList.size(); j++) {
				List<User> teacherList = userDao.findByGidGid(groupList.get(j)
						.getId(), gradeList.get(i).getId());
				groupList.get(j).setUserList(teacherList);
			}
			gradeList.get(i).setGroupsList(groupList);
		}
		return gradeList;
	}

	/**
	 * 教师发送短信给学生(页面上可以选择是发送给学生还是家长还是两者都发)
	 * 
	 * @param cid
	 *            班级编号
	 * @param userDao
	 * @param groupsDao
	 * @return
	 */
	private List<Groups> initStudent(Integer cid, UserDao userDao,
			GroupsDao groupsDao) {
		List<Groups> groupsList = groupsDao.findGroupsByRidCid(3, cid);
		for (int i = 0; i < groupsList.size(); i++) {
			groupsList.get(i).setUserList(
					userDao.findByGidCid(groupsList.get(i).getId(), cid));
		}
		return groupsList;
	}

	/**
	 * 加载家长(没有用)
	 * 
	 * @param cid
	 * @param userDao
	 * @param groupsDao
	 * @return
	 */
	private List<Groups> initParent(Integer cid, UserDao userDao,
			GroupsDao groupsDao) {
		List<Groups> groupsList = groupsDao.findGroupsByRidCid(3, cid);
		for (int i = 0; i < groupsList.size(); i++) {
			groupsList.get(i).setUserList(
					userDao.findByGidCid(groupsList.get(i).getId(), cid));
			// userDao.getParentByUid(uid);
		}
		return groupsList;
	}

	/**
	 * 学生查询教师
	 * 
	 * @param cid
	 * @param userDao
	 * @return
	 */
	private List<User> initTeacher(Integer cid, UserDao userDao) {
		return userDao.findByRidCid(2, cid);
	}

	/**
	 * 家长加载教师
	 * 
	 * @param puid
	 * @param userDao
	 * @return
	 */
	private List<User> initTeacherByParent(Integer puid, UserDao userDao) {
		return userDao.findTeacherByPuid(puid);
	}

	/**
	 * 查询所有的领导
	 * 
	 * @param userDao
	 * @return
	 */
	private List<User> initLeader(UserDao userDao) {
		return userDao.findByRid(5);
	}

	/**
	 * ajax根据班级编号,考试编号得到科目
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws ServletException
	 */
	public String subject(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		Integer cid = Integer
				.parseInt(request.getParameter("cid") != null ? request
						.getParameter("cid") : "0");
		Integer eid = Integer
				.parseInt(request.getParameter("eid") != null ? request
						.getParameter("eid") : "0");// 考试科目
		SubjectDao subjectDao = new SubjectDao();
		if (cid != null && !"".equals(cid)) {
			response.setContentType("text/html;charset=utf-8");
			response.setCharacterEncoding("UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			PrintWriter out = response.getWriter();
			StringBuilder sb = new StringBuilder();
			List<Subjects> subjectList = null;
			if (eid != null && eid > 0) {// 根据考试查询科目
				subjectList = subjectDao.findByEid(eid);
			} else {
				subjectList = subjectDao.getByCid(cid);
			}
			int size = subjectList.size();
			int count = 1;
			for (Subjects s : subjectList) {
				sb.append(s.getId() + ":" + s.getName());
				if (count < size) {
					count++;
					sb.append(",");
				}
			}
			out.print(sb.toString());
			out.flush();
			out.close();
		}
		return null;
	}

	/**
	 * 查询考试
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws ServletException
	 */
	public String score(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		Integer cid = Integer
				.parseInt(request.getParameter("cid") != null ? request
						.getParameter("cid") : "0");
		if (cid != null && !"".equals(cid)) {
			ExamDao examDao = new ExamDao();
			List<Exam> examList = examDao.findByCid(cid);
			response.setContentType("text/html;charset=utf-8");
			response.setCharacterEncoding("UTF-8");
			response.setHeader("Cache-Control", "no-cache");

			PrintWriter out = response.getWriter();
			StringBuilder sb = new StringBuilder();
			int size = examList.size();
			int count = 1;
			for (Exam e : examList) {
				sb.append(e.getId() + ":" + e.getName());
				if (count < size) {
					count++;
					sb.append(",");
				}
			}
			out.print(sb.toString());
			out.flush();
			out.close();
		}
		return null;
	}

	/**
	 * 查询模板
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws ServletException
	 */
	public String template(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		Integer tid = Integer
				.parseInt(request.getParameter("tid") != null ? request
						.getParameter("tid") : "4");
		if (tid != null && !"".equals(tid)) {
			User user = (User) request.getSession().getAttribute(
					Config.LOGIN_SESSION);
			MessageDao messageDao = new MessageDao();
			List<Message> messageList = messageDao.findByTidRid(tid, user
					.getRid());
			response.setContentType("text/html;charset=utf-8");
			response.setCharacterEncoding("UTF-8");
			response.setHeader("Cache-Control", "no-cache");

			PrintWriter out = response.getWriter();
			StringBuilder sb = new StringBuilder();
			for (Message m : messageList) {
				sb.append("template:=" + m.getContent());
				// if (count < size) {
				// count++;
				// sb.append(",");
				// }
			}
			out.print(sb.toString());
			out.flush();
			out.close();
		}
		return null;
	}

	/**
	 * 查询是否有新信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws ServletException
	 * @throws ParseException
	 */
	public String have(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, ParseException {
		User login_user = (User) request.getSession().getAttribute(
				Config.LOGIN_SESSION);
		Integer id = login_user.getId();
		if (id != null && !"".equals(id)) {
			response.setContentType("text/xml;charset=utf-8");
			response.setCharacterEncoding("UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			PrintWriter out = response.getWriter();
			messageDao = new MessageDao();
			List<Message> messageList = messageDao.getMessage(id);

			Element rootElt = new Element("rightdiv"); // 创建跟节点，名称为student
			Element messageElt = new Element("message");
			Element idElt = new Element("id");
			Element titleElt = new Element("title");
			Element nikeNameElt = new Element("nikeName");
			Element mobileElt = new Element("mobile");
			Element sendTimeElt = new Element("sendTime");
			for (int i = 0; i < messageList.size(); i++) {
				messageElt = new Element("message");
				Message msg = messageList.get(i);
				idElt = new Element("id");
				titleElt = new Element("title");
				nikeNameElt = new Element("nikeName");
				mobileElt = new Element("mobile");
				sendTimeElt = new Element("sendTime");

				idElt.addContent(msg.getId() + "");
				titleElt.addContent(msg.getTitle());
				nikeNameElt.addContent(msg.getNikeName());
				mobileElt.addContent(msg.getMobile());
				sendTimeElt.addContent(msg.getSendTime() + "");

				messageElt.addContent(idElt);
				messageElt.addContent(titleElt);
				messageElt.addContent(nikeNameElt);
				messageElt.addContent(mobileElt);
				messageElt.addContent(sendTimeElt);

				rootElt.addContent(messageElt);
			}
			Document doc = new Document(rootElt);
			XMLOutputter outXML = new XMLOutputter(); // 定义新XML文档
			String xmlStr = outXML.outputString(doc);
			out.print(xmlStr);
			out.flush();
			out.close();
		}
		return null;
	}

	public String findUserByMobile(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		response.setHeader("Cache-Control", "no-cache");
		String mobile = request.getParameter("query");
		mobile = Env.replaceHTML(mobile);// 转换字符
		PrintWriter out = response.getWriter();
		StringBuilder sb = new StringBuilder();
		User login_user = (User) request.getSession().getAttribute(
				Config.LOGIN_SESSION);
		userDao = new UserDao();
		// login_user=userDao.findUserById(login_user.getId());
		Integer cid = null;
		Integer gid = null;
		Integer rid = null;
		if (login_user.getRid() == 2) {// 教师
			cid = login_user.getCid();
			rid = 3;
		} else if (login_user.getRid() == 3) {// 学生
			cid = login_user.getCid();
			rid = 2;
		} else if (login_user.getRid() == 4) {// 家长
			User student = userDao.findUserDetailByPuid(login_user.getId());
			cid = student.getCid();
			rid = 2;
		} else if (login_user.getRid() == 5) {// 领导
			rid = 2;
		}
		List<User> mobileList = userDao
				.findUsersByMobile(mobile, cid, gid, rid);
		int length = mobileList.size();
		json.put("query", mobile);
		JSONArray array_mobile = new JSONArray();
		JSONArray array_id = new JSONArray();
		for (int i = 0; i < length; i++) {
			User user = mobileList.get(i);
			array_mobile.add(user.getNikeName() + "("
					+ (user.getMobile() != null ? user.getMobile() : "") + ")");
			array_id.add(user.getId() + "");
		}
		json.put("suggestions", array_mobile);
		json.put("data", array_id);
		out.print(json);
		out.flush();
		out.close();
		return null;
	}
}
