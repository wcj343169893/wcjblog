package com.snssly.sms.admin;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.snssly.sms.commons.Config;
import com.snssly.sms.commons.Env;
import com.snssly.sms.dao.ClazzDao;
import com.snssly.sms.dao.GradeDao;
import com.snssly.sms.dao.GroupsDao;
import com.snssly.sms.dao.StudentDao;
import com.snssly.sms.entity.Clazz;
import com.snssly.sms.entity.Grade;
import com.snssly.sms.entity.Groups;
import com.snssly.sms.entity.User;

public class StudentAction {
	StudentDao studentDao = null;
	GroupsDao groupsDao = null;
	GradeDao gradeDao = null;
	ClazzDao clazzDao = null;
	Integer rid = 3;

	/**
	 * 查询所有分组
	 * 
	 **/
	public String list(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		User user = (User) request.getSession().getAttribute(
				Config.LOGIN_SESSION);
		Integer cid = user.getCid();
		clazzDao = new ClazzDao();
		Clazz clazz = clazzDao.findClazzByUid(cid);
		studentDao = new StudentDao();
		String[] p = (String[]) request.getAttribute("params");// 分页
		Integer page = 0;
		Integer count = 15;
		Integer maxPage = 1;
		Integer maxCount = 0;
		// 判断传入的page
		if (p != null && p[0].matches("\\d+")) {
			page = Integer.valueOf(p[0]);
		}
		// 查询条件
		String sex = request.getParameter("sex") != null ? request
				.getParameter("sex").trim() : "2";
		String wh = request.getParameter("ws") != null ? request.getParameter(
				"ws").trim() : "";
		String gid = request.getParameter("gid") != null ? request
				.getParameter("gid").trim() : "0";// 按分组查询
		String cond = request.getParameter("order") != null ? request
						.getParameter("order").trim() : "id";// 按列查询
		String way = request.getParameter("sort") != null ? request
								.getParameter("sort").trim() : "desc";// 按升降序查询
		if (p != null && p.length > 1) {
			sex = p[1];
		}
		if (p != null && p.length > 2) {
			gid = p[2];
		}
		Integer c=0;
		String order = "id";
		if (p != null && p.length > 3) {
			cond = p[3];
			c = Integer.parseInt(cond);
			if (c == 0) {
				 order = "id";
			}else if (c==1) {
				 order = "snumber";
			}else if (c==2) {
				 order = "nikeName";
			}else if (c==3) {
				 order = "sex";
			}else if (c==4) {
				 order = "cid";
			}else if (c==5) {
				 order = "gid";
			}else if (c==6) {
				 order = "sid";
			}else if (c==7) {
				 order = "brithday";
			}else if (c==8) {
				 order = "birthplace";
			}else if (c==9) {
				 order = "mobile";
			}else if (c==10) {
				 order = "memberEndTime";
			}
		}
		if (p != null && p.length > 4) {
			way = p[4];
		}
		if (p != null && p.length > 5) {
			wh = p[5];
			wh = new String(wh.getBytes("ISO-8859-1"), "GBK");
		}
		Integer s = Integer.parseInt(sex);
		Integer g = Integer.parseInt(gid);
		List<User> studentList = new ArrayList<User>();
		maxCount = studentDao.getAllCount(wh, user.getCid(), s, g);
		if (maxCount > 0) {
			List<User> ul = studentDao.getUserList(page, count, wh, user.getCid(), s, g ,order,way);
			// 将搜索的关键词替换成其他颜色
			if (ul != null && ul.size() > 0 && wh != null
					&& !"".equals(wh.trim())) {
				for (User u : ul) {
					u.setNikeName(u.getNikeName().replaceAll(wh,
							"<span class='search_key'>" + wh + "</span>"));
					studentList.add(u);
				}
			} else {
				studentList = ul;
			}
		}
		groupsDao = new GroupsDao();
		List<Groups> groupsList = groupsDao.findGroupsByRole(3);
		gradeDao = new GradeDao();
		List<Grade> gradeList = gradeDao.findAll();
		maxPage = (maxCount / count) + (maxCount % count == 0 ? 0 : 1);
		request.setAttribute("maxPage", maxPage);
		request.setAttribute("maxCount", maxCount);
		request.setAttribute("page", page);
		request.setAttribute("ws", URLEncoder.encode(wh, "GBK"));
		request.setAttribute("sex", s);
		request.setAttribute("gid", gid);
		request.setAttribute("order", c);
		request.setAttribute("sort", way);
		request.setAttribute("gradeList", gradeList);
		request.setAttribute("groupsList", groupsList);
		request.setAttribute("studentList", studentList);
		request.setAttribute("clazz", clazz);
		return "user/studentManager";
	}

	/**
	 * 
	 * 删除用户
	 */
	public String delete(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		String[] p = (String[]) request.getAttribute("params");
		if (p != null && p[0].matches("\\d+")) {
			StudentDao studentDAO = new StudentDao();
			Integer mid = Integer.valueOf(p[0]);
			studentDAO.delete(mid);
		}
		request.removeAttribute("params");
		return "user/student_list.html";
	}

	/**
	 * 
	 * 保存添加更新
	 * */
	public String update(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		Integer _form_session_id = Integer.parseInt(request
				.getParameter("_form_session_id") != null ? request
				.getParameter("_form_session_id") : "0");
		User student = null;
		List<Integer> _form_session = (List<Integer>) request.getSession()
				.getAttribute("_form_session");
		if (_form_session.contains(_form_session_id)) {// 存在于session中
			System.out.println("正常操作");
			studentDao = new StudentDao();
			User s = (User) request.getAttribute("form");
			if (s.getId() != null && !"".equals(s.getId()) && s.getId() > 0) {
				student = studentDao.findUserById(s.getId());
				if(s.getMobile()!=null && !"".equals(s.getMobile())){
					student.setName(s.getMobile());
					Integer length = s.getMobile().length();
					student.setPassword(Env.encode(s.getMobile().substring(length - 6,
							length)));
					student.setMobile(s.getMobile());
				}
				student.setNikeName(s.getNikeName());
				student.setSex(s.getSex());
				student.setGradeId(s.getGradeId());
				student.setCid(s.getCid());
				student.setSnumber(s.getSnumber());
				student.setGid(s.getGid());
				student.setBrithday(s.getBrithday());
				student.setSid(s.getSid());
				student.setBirthplace(s.getBirthplace());
			} else {
				if(s.getMobile()!=null && !"".equals(s.getMobile())){
					s.setName(s.getMobile());
					Integer length = s.getMobile().length();
					s.setPassword(Env.encode(s.getMobile().substring(length - 6,
						length)));
				}
				student = s;
			}
			student.setId(studentDao.update(student));
			Integer id = student.getId();
			for (int i = 0; i < _form_session.size(); i++) {
				if (_form_session.get(i).equals(_form_session_id)) {
					_form_session.remove(i);
				}
			}
		} else {
			System.out.println("不要重复提交啦！~~~谢谢合作");
		}
		request.getSession().setAttribute("_form_session", _form_session);
		return "user/student_updateInit-" + student.getId();
	}

	/**
	 * 更新详细
	 * 
	 * */
	public String updateInit(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		String[] p = (String[]) request.getAttribute("params");
		if (p != null && p[0].matches("\\d+")) {
			studentDao = new StudentDao();
			Integer id = Integer.valueOf(p[0]);
			User student = studentDao.findUserById(id);
			request.setAttribute("student", student);
		}
		clazzDao = new ClazzDao();
		User user = (User) request.getSession().getAttribute(
				Config.LOGIN_SESSION);
		List<Clazz> clazzList = clazzDao.findAllByUid(user.getId());
		request.setAttribute("clazzList", clazzList);
		groupsDao = new GroupsDao();
		List<Groups> groupsList = groupsDao.findGroupsByRole(rid);
		request.setAttribute("groupsList", groupsList);
		request.setAttribute("now", Env.format(new Date(), "yyyy-MM-dd"));
		return "user/studentUpdate";
	}
	/**
	 * 生日显示
	 * 
	 * */
	public String birthday(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		clazzDao = new ClazzDao();
		User user = (User) request.getSession().getAttribute(
				Config.LOGIN_SESSION);
		Integer cid = user.getCid();
		Clazz clazz = clazzDao.findClazzByUid(cid);
		studentDao = new StudentDao();
		List<List<User>> birthdayList = new ArrayList<List<User>>();
		List<User> userList = null;
		for (int i = 1; i <= 12; i++) {
			userList = studentDao.getBirthday(cid, i);
			birthdayList.add(userList);
		}
		//显示今天的日期和星期
		  Date date = new Date();
		  Calendar cal=Calendar.getInstance();
		  String dayOfWeekTime="";
		  
		  int dayOfWeek=cal.get(Calendar.DAY_OF_WEEK);
		  switch(dayOfWeek){
		   case 1:dayOfWeekTime="星期天";break;
		   case 2:dayOfWeekTime="星期一";break;
		   case 3:dayOfWeekTime="星期二";break;
		   case 4:dayOfWeekTime="星期三";break;
		   case 5:dayOfWeekTime="星期四";break;
		   case 6:dayOfWeekTime="星期五";break;
		   case 7:dayOfWeekTime="星期六";break;
		  }
		  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		  String timeString = sdf.format(date);
		  request.setAttribute("time", timeString);
		  request.setAttribute("week", dayOfWeekTime);
		request.setAttribute("clazz", clazz);
		request.setAttribute("birthdayList", birthdayList);
		return "user/studentBirthdayManager";
	}
	
	/**
	 * @param request
	 * @param response
	 * @return通过手机号码检查用户是否已存在
	 * @throws IOException
	 * @throws ServletException
	 */
	public String checkStudent(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		String mobile = request.getParameter("mobile");
		if (mobile != null && !"".equals(mobile)) {
			response.setContentType("text/html;charset=utf-8");
			response.setCharacterEncoding("UTF-8");
			response.setHeader("Cache-Control", "no-cache");

			PrintWriter out = response.getWriter();
			StringBuilder sb = new StringBuilder();

			studentDao = new StudentDao();
			List<User> studentList = studentDao.findStudentByMobile(mobile);
			if (studentList != null) {
				Iterator it = studentList.iterator();
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
