package com.snssly.sms.admin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.snssly.sms.commons.Config;
import com.snssly.sms.dao.MessageDao;
import com.snssly.sms.dao.MessageTypeDao;
import com.snssly.sms.dao.SendListDao;
import com.snssly.sms.dao.UserDao;
import com.snssly.sms.entity.MessageStatistics;
import com.snssly.sms.entity.MessageType;
import com.snssly.sms.entity.User;

public class StatAction {
	MessageDao messageDao = null;
	MessageTypeDao mtypeDao = null;

	/**
	 * 查询所有的用户
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws ServletException
	 */
	public String info(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		// 准备数据
		messageDao = new MessageDao();
		mtypeDao = new MessageTypeDao();
		UserDao userDao = new UserDao();
		SendListDao sendDao = new SendListDao();
		// html页面，列名显示
		User login_user = (User) request.getSession().getAttribute(
				Config.LOGIN_SESSION);
		Integer uid = login_user.getId();
		Integer rid = login_user.getRid();
		// 登陆者角色的短信类型
		List<MessageType> typeList = mtypeDao.findType(rid);

		// p的不同情况
		Integer logo = 0;
		String[] p = (String[]) request.getAttribute("params");
		if (p != null && p[0].matches("\\d+")) {// 判断是否是数字
			logo = Integer.valueOf(p[0]);
		}
		String start = request.getParameter("start");// 起始时间
		String end = request.getParameter("end");// 结束时间
		if (logo == 1) {// 短信统计
			if (start.compareTo(end) > 0) {
				String temp = end;
				end = start;
				start = temp;
			}
			// html页面，具体数据 (注：暂时只显示个人的信息)
			List<MessageStatistics> mixedList = new ArrayList<MessageStatistics>();
			User user = userDao.getNikeNameByuid(uid);// 根据不同uid，得到不同的user集
			MessageStatistics messageStatistics = new MessageStatistics();
			//for (User user : userList) {
				String name = user.getNikeName();
				Integer sendCount = sendDao.getCount(start, end, uid);
				List<MessageType> messageTypeList = new ArrayList<MessageType>();
				for (MessageType messageType : typeList) {
					Integer tid = messageType.getId();
					Integer userid = user.getId();
					Integer messageCount = messageDao.getCountByRid(tid,
							userid, start, end);
					messageType.setCount(messageCount);
					messageTypeList.add(messageType);
				}
				messageStatistics.setName(name);
				messageStatistics.setSendCount(sendCount);
				messageStatistics.setMessageTypeList(messageTypeList);
				mixedList.add(messageStatistics);
			//}
			request.setAttribute("mixedList", mixedList);
		}
		request.setAttribute("start", start);
		request.setAttribute("end", end);
		request.setAttribute("typeList", typeList);
		return "self/statInfo";
	}
}
