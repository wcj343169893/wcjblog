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

import com.snssly.sms.commons.Env;
import com.snssly.sms.dao.ClazzDao;
import com.snssly.sms.dao.GradeDao;
import com.snssly.sms.dao.GroupsDao;
import com.snssly.sms.dao.RoleDAO;
import com.snssly.sms.dao.TeacherDao;
import com.snssly.sms.entity.Clazz;
import com.snssly.sms.entity.Grade;
import com.snssly.sms.entity.Groups;
import com.snssly.sms.entity.User;

public class TeacherAction {
	TeacherDao teacherDao = null;
	GroupsDao groupsDao = null;
	GradeDao gradeDao = null;
	ClazzDao clazzDao = null;
	RoleDAO roleDao = null;

	/**
	 * 查询所有分组
	 * 
	 **/
	public String list(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		teacherDao = new TeacherDao();
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
		String gradeId = request.getParameter("gradeId") != null ? request
				.getParameter("gradeId").trim() : "0";// 按年级查询
		String cid = request.getParameter("cid") != null ? request
				.getParameter("cid").trim() : "0";// 按班级查询
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
		if (p != null && p.length > 3) {
			gradeId = p[3];
		}
		if (p != null && p.length > 4) {
			cid = p[4];
		}
		Integer cd=0;
		String order = "id";
		if (p != null && p.length > 5) {
			cond = p[5];
			cd = Integer.parseInt(cond);
			if (cd == 0) {
				 order = "id";
			}else if (cd==1) {
				 order = "nikeName";
			}else if (cd==2) {
				 order = "sex";
			}else if (cd==3) {
				 order = "cid";
			}else if (cd==4) {
				 order = "gid";
			}else if (cd==5) {
				 order = "sid";
			}else if (cd==6) {
				 order = "brithday";
			}else if (cd==7) {
				 order = "birthplace";
			}else if (cd==8) {
				 order = "mobile";
			}
		}
		if (p != null && p.length > 6) {
			way = p[6];
		}
		if (p != null && p.length > 7) {
			wh = p[7];
			wh = new String(wh.getBytes("ISO-8859-1"), "GBK");
		}
		Integer s = Integer.parseInt(sex);
		Integer g = Integer.parseInt(gid);
		Integer gd = Integer.parseInt(gradeId);
		Integer c = Integer.parseInt(cid);
		List<User> teacherList = new ArrayList<User>();
		maxCount = teacherDao.getAllCount(wh, s, g, gd, c);
		if (maxCount > 0) {
			List<User> ul = teacherDao
					.getUserList(page, count, wh, s, g, gd, c,order,way);
			// 将搜索的关键词替换成其他颜色
			if (ul != null && ul.size() > 0 && wh != null
					&& !"".equals(wh.trim())) {
				for (User u : ul) {
					u.setNikeName(u.getNikeName().replaceAll(wh,
							"<span class='search_key'>" + wh + "</span>"));
					teacherList.add(u);
				}
			} else {
				teacherList = ul;
			}
		}
		groupsDao = new GroupsDao();
		List<Groups> groupsList = groupsDao.findGroupsByRole(2);
		gradeDao = new GradeDao();
		List<Grade> gradeList = gradeDao.findAll();
		maxPage = (maxCount / count) + (maxCount % count == 0 ? 0 : 1);
		request.setAttribute("maxPage", maxPage);
		request.setAttribute("maxCount", maxCount);
		request.setAttribute("page", page);
		request.setAttribute("ws", URLEncoder.encode(wh, "GBK"));
		request.setAttribute("sex", s);
		request.setAttribute("gid", gid);
		request.setAttribute("gradeId", gd);
		request.setAttribute("cid", cid);
		request.setAttribute("order", cd);
		request.setAttribute("sort", way);
		request.setAttribute("gradeList", gradeList);
		request.setAttribute("groupsList", groupsList);
		request.setAttribute("teacherList", teacherList);
		return "user/teacherManager";
	}

	/**
	 * 
	 * 删除用户
	 */
	public String delete(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		String[] p = (String[]) request.getAttribute("params");
		if (p != null && p[0].matches("\\d+")) {
			TeacherDao teacherDAO = new TeacherDao();
			Integer mid = Integer.valueOf(p[0]);
			teacherDAO.delete(mid);
		}
		request.removeAttribute("params");
		return "user/teacher_list.html";
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
		User teacher = null;
		if (_form_session.contains(_form_session_id)) {// 存在于session中
			System.out.println("正常操作");
			teacherDao = new TeacherDao();
			User t = (User) request.getAttribute("form");
			if (t.getId() != null && !"".equals(t.getId()) && t.getId() > 0) {
				teacher = teacherDao.findUserById(t.getId());
				teacher.setName(t.getMobile());
				teacher.setNikeName(t.getNikeName());
				teacher.setSex(t.getSex());
				teacher.setGradeId(t.getGradeId());
				teacher.setCid(t.getCid());
				teacher.setSnumber(t.getSnumber());
				teacher.setGid(t.getGid());
				teacher.setBrithday(t.getBrithday());
				teacher.setSid(t.getSid());
				teacher.setBirthplace(t.getBirthplace());
				teacher.setMobile(t.getMobile());
				Integer length = t.getMobile().length();
				teacher.setPassword(Env.encode(t.getMobile().substring(length - 6,
						length)));
			} else {
				t.setName(t.getMobile());
				Integer length = t.getMobile().length();
				t.setPassword(Env.encode(t.getMobile().substring(length - 6,
						length)));
				teacher = t;
			}
			teacher.setId(teacherDao.update(teacher));
			for (int i = 0; i < _form_session.size(); i++) {
				if (_form_session.get(i).equals(_form_session_id)) {
					_form_session.remove(i);
				}
			}
		} else {
			System.out.println("不要重复提交啦！~~~谢谢合作");
		}
		request.getSession().setAttribute("_form_session", _form_session);
		return "user/teacher_updateInit-" + teacher.getId();
	}

	public String updateInit(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {

		Integer rid = 2;
		String[] p = (String[]) request.getAttribute("params");
		if (p != null && p[0].matches("\\d+")) {
			teacherDao = new TeacherDao();
			Integer tid = Integer.valueOf(p[0]);
			User t = teacherDao.findUserById(tid);
			request.setAttribute("teacher", t);
		}
		groupsDao = new GroupsDao();
		List<Groups> groupsList = groupsDao.findGroupsByRole(rid);
		gradeDao = new GradeDao();
		List<Grade> gradeList = gradeDao.findAll();
		request.setAttribute("now", Env.format(new Date(), "yyyy-MM-dd"));
		request.setAttribute("groupsList", groupsList);
		request.setAttribute("gradeList", gradeList);
		return "user/teacherUpdate";
	}

	public String listByGid(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		Integer gid = Integer
				.parseInt(request.getParameter("gid") != null ? request
						.getParameter("gid") : "0");
		clazzDao = new ClazzDao();
		if (gid != null && !"".equals(gid)) {
			List<Clazz> clazzList = clazzDao.findClazzByGrade(gid);
			response.setContentType("text/html;charset=utf-8");
			response.setCharacterEncoding("UTF-8");
			response.setHeader("Cache-Control", "no-cache");

			PrintWriter out = response.getWriter();
			StringBuilder sb = new StringBuilder();
			int size = clazzList.size();
			int count = 1;
			for (Clazz c : clazzList) {
				sb.append(c.getId() + ":" + c.getName());
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
	 * 生日显示
	 * 
	 * */
	public String birthday(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		teacherDao = new TeacherDao();
		List<List<User>> birthdayList = new ArrayList<List<User>>();
		List<User> userList = null;
		for (int i = 1; i <= 12; i++) {
			userList = teacherDao.getBirthday(i);
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
		request.setAttribute("birthdayList", birthdayList);
		return "user/teacherBirthdayManager";
	}
	
	/**
	 * @param request
	 * @param response
	 * @return通过手机号码检查用户是否已存在
	 * @throws IOException
	 * @throws ServletException
	 */
	public String checkTeacher(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		String mobile = request.getParameter("mobile");
		if (mobile != null && !"".equals(mobile)) {
			response.setContentType("text/html;charset=utf-8");
			response.setCharacterEncoding("UTF-8");
			response.setHeader("Cache-Control", "no-cache");

			PrintWriter out = response.getWriter();
			StringBuilder sb = new StringBuilder();

			teacherDao = new TeacherDao();
			List<User> teacerList = teacherDao.findTeacherByMobile(mobile);
			if (teacerList != null) {
				Iterator it = teacerList.iterator();
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
