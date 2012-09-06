package com.snssly.sms.admin;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.snssly.sms.commons.Config;
import com.snssly.sms.commons.Env;
import com.snssly.sms.dao.AdminuserDAO;
import com.snssly.sms.dao.MessageDao;
import com.snssly.sms.dao.ModuleDAO;
import com.snssly.sms.dao.OnlineDao;
import com.snssly.sms.dao.UserDao;
import com.snssly.sms.entity.Adminuser;
import com.snssly.sms.entity.Module;
import com.snssly.sms.entity.User;

/**
 * -- 后台管理员登录退出
 * 
 * @author Administrator
 * 
 */
public class AdminuserAction {
	UserDao userDao = null;
	OnlineDao onlineDao = null;
	MessageDao messageDao = null;

	/**
	 * -- 管理员登录
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws ServletException
	 */
	public String login(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		Adminuser adm = (Adminuser) request.getAttribute("form");
		if (adm == null) {
			response.sendRedirect("/admin/index.jsp");
			return null;
		}
		if (adm.getName() == null || adm.getPassword() == null
				|| "".equals(adm.getName().trim())
				|| "".equals(adm.getPassword().trim())) {
			request.setAttribute("login_error_message", "请输入用户名和密码");
			// response.sendRedirect("/admin/index.jsp");
			request.getRequestDispatcher("/admin/index.jsp").forward(request,
					response);
			return null;
		}
		request.setAttribute("login_name",adm.getName());
		String vcode = request.getParameter("verifycode");
		if (vcode == null || vcode.equals("")) {
			request.setAttribute("login_error_message", "请输入验证码");
			request.getRequestDispatcher("/admin/index.jsp").forward(request,
					response);
			return null;
		}
		String rand = (String) request.getSession().getAttribute(
				Config.IMG_CODE);
		if (!vcode.equalsIgnoreCase(rand)) {
			request.setAttribute("login_error_message", "验证码输入错误");
			request.getRequestDispatcher("/admin/index.jsp").forward(request,
					response);
			return null;
		}
		String pwd = Env.encode(adm.getPassword());
		adm.setPassword(pwd);
		AdminuserDAO admDAO = new AdminuserDAO();
		User user_login = admDAO.findByUser(adm);
		onlineDao = new OnlineDao();
		if (user_login != null) {
			Integer id = user_login.getId();
			Integer rid = user_login.getRid();
			Integer cid = user_login.getCid();
			String ip = this.getIpAddr(request);
			// 更新上次登录时间
			admDAO.update(user_login.getId());
			// 更新在线列表
			onlineDao.updateOnline(id, ip);
			userDao = new UserDao();
			if (rid == 2) {// 登录角色为教师，获得本班学生、教师、领导生日
				List<User> studentList = userDao.getStudentByBrithday(3, cid);
				List<User> teacherList = userDao.getTeacherByBrithday(2);
				List<User> leaderList = userDao.getLeaderByBrithday(5);
				request.getSession().setAttribute("studentList", studentList);
				request.getSession().setAttribute("teacherList", teacherList);
				request.getSession().setAttribute("leaderList", leaderList);
			} else if (rid == 3) {// 登录角色为学生，获得当月生日的学生和班主任
				List<User> studentList = userDao.getStudentByBrithday(3, cid);
				List<User> teacherList = userDao.getTeacherByBrithday(2, cid);
				request.getSession().setAttribute("studentList", studentList);
				request.getSession().setAttribute("teacherList", teacherList);
			} else if (rid == 4) {// 登录角色为家长，获得教师、领导生日
				List<User> teacherList = userDao.getTeacherByBrithday(2, cid);
				List<User> leaderList = userDao.getLeaderByBrithday(5);
				request.getSession().setAttribute("teacherList", teacherList);
				request.getSession().setAttribute("leaderList", leaderList);
			} else if (rid == 5) {// 登录角色为领导，获得教师、领导生日
				List<User> teacherList = userDao.getTeacherByBrithday(2);
				List<User> leaderList = userDao.getLeaderByBrithday(5);
				request.getSession().setAttribute("teacherList", teacherList);
				request.getSession().setAttribute("leaderList", leaderList);
			}
			// 获得未读信息
			// messageDao = new MessageDao();
			// List<Message> messageList = messageDao.getMessage(id);
			// request.getSession().setAttribute("newMessageList", messageList);
			// request.getSession().setAttribute("rid", rid);
			// 存用户信息
			request.getSession().setAttribute(Config.LOGIN_SESSION, user_login);
			// 存用户权限
			ModuleDAO moduleDao = new ModuleDAO();
			List<Module> moduleList = moduleDao.findByUid(user_login.getId());
			request.getSession().setAttribute(Config.LOGIN_MODULE_LIST_SESSION,
					moduleList);
			response.sendRedirect("/web/index.html");
		} else {
			request.setAttribute("login_error_message", "用户名或密码输入错误");
			request.getRequestDispatcher("/admin/index.jsp").forward(request,
					response);
		}
		return null;
	}

	/**
	 * -- 退出登录
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws ServletException
	 */
	public String logout(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		User user = (User) request.getSession().getAttribute(
				Config.LOGIN_SESSION);
		if (user != null) {
			Integer id = user.getId();
			onlineDao = new OnlineDao();
			onlineDao.deleteOnline(id);// 正常退出时，删除online_user表中的数据
		}
		request.getSession().removeAttribute(Config.LOGIN_SESSION);
		// response.sendRedirect("/admin/");
		request.getRequestDispatcher("/admin/index.jsp").forward(request,
				response);
		return null;
	}

	/**
	 * -- 获取IP
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws ServletException
	 */
	public String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

}
