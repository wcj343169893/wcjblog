package com.snssly.sms.admin;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.snssly.sms.commons.Config;
import com.snssly.sms.dao.MessageDao;
import com.snssly.sms.dao.UserDao;
import com.snssly.sms.entity.Message;
import com.snssly.sms.entity.User;

public class DesktopAction {
	
	UserDao userDao = null;
	MessageDao messageDao = null;
	/**
	 * -- 获取学生生日列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws ServletException
	 */
	public List<User> getStudent(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException,
			ParseException {
		User login_user = (User) request.getSession().getAttribute(
				Config.LOGIN_SESSION);
		Integer cid = login_user.getCid();// 班级id，cid
		Integer rid = 3;
		if (cid != null && !"".equals(cid)) {
			response.setContentType("text/html;charset=utf-8");
			response.setCharacterEncoding("UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			
			PrintWriter out = response.getWriter();
			StringBuilder sb = new StringBuilder();
			userDao = new UserDao();
			List<User> studentList =  userDao.getStudentByBrithday(rid, cid);
			Iterator it = studentList.iterator();
				while (it.hasNext()) {
					User student = (User) it.next();
					sb.append(student.getNikeName()+"_"+student.getBrithday()+",");					
				}
				out.print(sb.toString());
				out.flush();
				out.close();		
		}
		return null;
	}
	/**
	 * -- 获取所有教师生日列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws ServletException
	 */
	public List<User> getAllTeacher(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException,
			ParseException {
		User login_user = (User) request.getSession().getAttribute(
				Config.LOGIN_SESSION);
			Integer rid = 2;
			response.setContentType("text/html;charset=utf-8");
			response.setCharacterEncoding("UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			
			PrintWriter out = response.getWriter();
			StringBuilder sb = new StringBuilder();
			userDao = new UserDao();
			List<User> teacherList =  userDao.getTeacherByBrithday(rid);          
			Iterator it = teacherList.iterator();
				while (it.hasNext()) {
					User teacher = (User) it.next();
					sb.append(teacher.getNikeName()+"_"+teacher.getBrithday()+",");					
				}
				out.print(sb.toString());
				out.flush();
				out.close();		
		return null;
	}
	/**
	 * -- 获取教师生日
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws ServletException
	 */
	public List<User> getTeacher(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException,
			ParseException {
		User login_user = (User) request.getSession().getAttribute(
				Config.LOGIN_SESSION);
		Integer cid = login_user.getCid();// 班级id，cid
		Integer rid = 2;
		if (cid != null && !"".equals(cid)) {
			response.setContentType("text/html;charset=utf-8");
			response.setCharacterEncoding("UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			
			PrintWriter out = response.getWriter();
			StringBuilder sb = new StringBuilder();
			userDao = new UserDao();
			List<User> teacherList =  userDao.getTeacherByBrithday(rid, cid);
			Iterator it = teacherList.iterator();
				while (it.hasNext()) {
					User teacher = (User) it.next();
					sb.append(teacher.getNikeName()+"_"+teacher.getBrithday()+",");					
				}
				out.print(sb.toString());
				out.flush();
				out.close();		
		}
		return null;
	}
	/**
	 * -- 获取教师生日
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws ServletException
	 */
	public List<User> getLeader(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException,
			ParseException {
			Integer rid = 5;
			response.setContentType("text/html;charset=utf-8");
			response.setCharacterEncoding("UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			PrintWriter out = response.getWriter();
			StringBuilder sb = new StringBuilder();
			userDao = new UserDao();
			List<User> leaderList =  userDao.getLeaderByBrithday(rid);
			Iterator it = leaderList.iterator();
				while (it.hasNext()) {
					User leader = (User) it.next();
					sb.append(leader.getNikeName()+"_"+leader.getBrithday()+",");					
				}
				out.print(sb.toString());
				out.flush();
				out.close();		
		return null;
	}
	/**
	 * -- 获取信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws ServletException
	 */
	public List<Message> getMessage(HttpServletRequest request,
			HttpServletResponse response)throws IOException,ServletException,ParseException{
		User login_user = (User) request.getSession().getAttribute(
				Config.LOGIN_SESSION);
		Integer id = login_user.getId();
		if (id != null && !"".equals(id)) {
			response.setContentType("text/html;charset=utf-8");
			response.setCharacterEncoding("UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			
			PrintWriter out = response.getWriter();
			StringBuilder sb = new StringBuilder();
			messageDao = new MessageDao();
		List<Message> messageList = messageDao.getMessage(id);
		Iterator it = messageList.iterator();
		while (it.hasNext()) {
			Message message = (Message) it.next();
			sb.append(message.getTitle()+"_"+message.getNikeName()+"_"+message.getMobile()+"_"+message.getSendTime()+",");					
		}
		out.print(sb.toString());
		out.flush();
		out.close();
	  }
		return null;
	}
}
