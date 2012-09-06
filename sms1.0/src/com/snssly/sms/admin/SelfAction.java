package com.snssly.sms.admin;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jdom.Parent;

import com.snssly.sms.commons.Config;
import com.snssly.sms.commons.Env;
import com.snssly.sms.dao.ClazzDao;
import com.snssly.sms.dao.GradeDao;
import com.snssly.sms.dao.GroupsDao;
import com.snssly.sms.dao.ParentsDao;
import com.snssly.sms.dao.RoleDAO;
import com.snssly.sms.dao.UserDao;
import com.snssly.sms.entity.User;

public class SelfAction {
	UserDao userDao = null;
	RoleDAO roleDao = null;
	GroupsDao groupsDao = null;
	GradeDao gradeDao = null;
	ClazzDao clazzDao = null;

	/**
	 * 查询个人详细信息
	 * 
	 **/
	public String info(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		User user = (User) request.getSession().getAttribute(
				Config.LOGIN_SESSION);
		Integer id = user.getId();
		userDao = new UserDao();
		User u = userDao.findUserDetailById(id);
		request.setAttribute("user", u);
		return "self/selfInfo";
	}

	/**
	 * 
	 * 
	 * */
	public String update(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		Integer _form_session_id = Integer.parseInt(request
				.getParameter("_form_session_id") != null ? request
				.getParameter("_form_session_id") : "0");
		List<Integer> _form_session = (List<Integer>) request.getSession()
				.getAttribute("_form_session");
		if (_form_session.contains(_form_session_id)) {// 存在于session中
			System.out.println("正常操作");
			User user = (User) request.getSession().getAttribute(
					Config.LOGIN_SESSION);
			User u = (User) request.getAttribute("form");
			// 查询数据库，得到原有信息
			UserDao userDao = new UserDao();
			User user2 = userDao.findUserById(user.getId());
			
			// user2.set姓名，性别，生日，身份证，籍贯，手机号码
				user2.setName(u.getMobile());
				user2.setSex(u.getSex());
				user2.setNikeName(u.getNikeName());
				user2.setBrithday(u.getBrithday());
				user2.setSid(u.getSid());
				user2.setBirthplace(u.getBirthplace());
				user2.setMobile(u.getMobile());
			if (u.getPassword() != null && !"".equals(u.getPassword())) {
				if (u.getPassword().equals(request.getParameter("password1"))) {
					user2.setPassword(Env.encode(u.getPassword().trim()));
				}
			}
			if (user.getRid() == 4) {
				//当父母续期会员时间时，同时更新学生会员时间
				ParentsDao parentsDao = new ParentsDao();
					List<User> s = parentsDao.findStudentByPuid(u.getId());
					Integer length = s.size();
					if (s != null) {
						for (Integer i=0; i < length; i++){
							User user1=s.get(i);
							user1.setMemberEndTime(u.getMemberEndTime());
							userDao.updateEndTime(user1);
						}
					}
					user2.setMemberEndTime(u.getMemberEndTime());
			}
			userDao.updateSelf(user2);
			for (int i = 0; i < _form_session.size(); i++) {
				if (_form_session.get(i).equals(_form_session_id)) {
					_form_session.remove(i);
				}
			}
		} else {
			System.out.println("不要重复提交啦！~~~谢谢合作");
		}
		request.getSession().setAttribute("_form_session", _form_session);
		return "self/self_info.html";
	}

	public String updateInit(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		User user = (User) request.getSession().getAttribute(
				Config.LOGIN_SESSION);
		Integer id = user.getId();
		userDao = new UserDao();
		User u = userDao.findUserDetailById(id);
		request.setAttribute("now", Env.format(new Date(), "yyyy-MM-dd"));
		request.setAttribute("user", u);
		return "self/selfInfoUpdate";
	}
	
	/**
	 * @param request
	 * @param response
	 * @return通过手机号码检查用户是否已存在
	 * @throws IOException
	 * @throws ServletException
	 */
	public String checkSelf(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		String mobile = request.getParameter("mobile");
		if (mobile != null && !"".equals(mobile)) {
			response.setContentType("text/html;charset=utf-8");
			response.setCharacterEncoding("UTF-8");
			response.setHeader("Cache-Control", "no-cache");

			PrintWriter out = response.getWriter();
			StringBuilder sb = new StringBuilder();

			userDao = new UserDao();
			List<User> userList = userDao.findUsersByMobile(mobile);
			if (userList != null) {
				Iterator it = userList.iterator();
				while (it.hasNext()) {
					User p = (User) it.next();
					sb.append(p.getId() + ",");
				}
				out.print(sb.toString().substring(0, sb.length()));
				out.flush();
				out.close();
			}
		}
		return null;
	}
}
