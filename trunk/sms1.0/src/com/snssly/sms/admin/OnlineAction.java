package com.snssly.sms.admin;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.snssly.sms.commons.Config;
import com.snssly.sms.dao.OnlineDao;
import com.snssly.sms.entity.OnlineUser;
import com.snssly.sms.entity.Role;
import com.snssly.sms.entity.User;

public class OnlineAction {
	OnlineDao onlineDao = null;
	
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
		onlineDao = new OnlineDao();
		//登陆者的ip
		User login_user = (User) request.getSession().getAttribute(
				Config.LOGIN_SESSION);
		Integer uid = login_user.getId();
		List<OnlineUser> ipList = onlineDao.findIp(uid);
		String ip = "无法识别IP";
		if (ipList!=null) {
			 ip = ipList.get(0).getIp();
		}
		Integer onlineCount = onlineDao.getCount();
		//在线用户的角色和人数
		List<Role> roleList = onlineDao.findRole();
		for (Role role : roleList) {
			Integer rid = role.getId();
			Integer count = onlineDao.findOnlineUser(rid);
			role.setCount(count);
		}
		//在线用户列表，根据角色id
		Integer rid = 1;
		String[] p = (String[])request.getAttribute("params");
		if (p != null && p[0].matches("\\d+")) {//判断是否是数字
			rid = Integer.valueOf(p[0]);
		}
		System.out.println(rid);
		List<OnlineUser> onlineList = onlineDao.findOnlineList(rid);
		
		request.setAttribute("onlineCount", onlineCount);
		request.setAttribute("onlineList", onlineList);
		request.setAttribute("ip", ip);
		request.setAttribute("roleList", roleList);
		return "self/onlineInfo";
	}
}
