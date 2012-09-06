package com.snssly.sms.admin;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.snssly.sms.commons.Env;
import com.snssly.sms.dao.GradeDao;
import com.snssly.sms.dao.GroupsDao;
import com.snssly.sms.dao.ParentsDao;
import com.snssly.sms.dao.RoleDAO;
import com.snssly.sms.dao.SchoolDAO;
import com.snssly.sms.dao.UserDao;
import com.snssly.sms.entity.Grade;
import com.snssly.sms.entity.Groups;
import com.snssly.sms.entity.Role;
import com.snssly.sms.entity.School;
import com.snssly.sms.entity.User;

/**
 * 用户处理
 * 
 */
public class UserAction {
	UserDao userDao = null;
	RoleDAO roleDao = null;
	GroupsDao groupsDao = null;
	GradeDao gradeDao = null;
	SchoolDAO schoolDao=null;

	/**
	 * 查询所有的用户
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws ServletException
	 */
	public String list(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String[] p = (String[]) request.getAttribute("params");// 分页
		Integer page = 0;
		Integer count = 15;
		Integer maxPage = 1;
		Integer maxCount = 0;
		// 判断传入的page
		if (p != null && p[0].matches("\\d+")) {
			page = Integer.valueOf(p[0]);
		}
		userDao = new UserDao();
		// 查询条件
		String sex = request.getParameter("sex") != null ? request
				.getParameter("sex").trim() : "2";// 按性别查询
		String wh = request.getParameter("ws") != null ? request.getParameter(
				"ws").trim() : "";// 按名字查询
		String rid = request.getParameter("rid") != null ? request
				.getParameter("rid").trim() : "0";// 按角色查询
		String gid = request.getParameter("gid") != null ? request
				.getParameter("gid").trim() : "0";// 按分组查询
		String isVisible = request.getParameter("isVisible") != null ? request
				.getParameter("isVisible").trim() : "2";// 按状态查询
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
			rid = p[2];
		}
		if (p != null && p.length > 3) {
			gid = p[3];
		}
		if (p != null && p.length > 4) {
			isVisible = p[4];
		}
		if (p != null && p.length > 5) {
			gradeId = p[5];
		}
		if (p != null && p.length > 6) {
			cid = p[6];
		}
		if (p != null && p.length > 7) {
			cond = p[7];
		}
		if (p != null && p.length > 8) {
			way = p[8];
		}
		if (p != null && p.length > 9) {
			wh = p[9];
			wh = new String(wh.getBytes("ISO-8859-1"), "GBK");
		}
		Integer s = Integer.parseInt(sex);
		Integer r = Integer.parseInt(rid);
		Integer g = Integer.parseInt(gid);
		Integer iv = Integer.parseInt(isVisible);
		Integer gd = Integer.parseInt(gradeId);
		Integer c = Integer.parseInt(cid);
		maxCount = userDao.getCount(wh, s, r, g, iv, gd, c);
		List<User> userList = new ArrayList<User>();
		if (maxCount > 0) {
			List<User> ul = userDao.getUserList(page, count, wh, s, r, g, iv,
					gd, c, cond,way);
			// 将搜索的关键词替换成其他颜色
			if (ul != null && ul.size() > 0 && wh != null
					&& !"".equals(wh.trim())) {
				for (User user : ul) {
					user.setName(user.getName().replaceAll(wh,
							"<span class='search_key'>" + wh + "</span>"));
					user.setNikeName(user.getNikeName().replaceAll(wh,
							"<span class='search_key'>" + wh + "</span>"));
					userList.add(user);
				}
			} else {
				userList = ul;
			}
		}
		roleDao = new RoleDAO();
		List<Role> roleList = roleDao.findAll();
		schoolDao=new SchoolDAO();
		List<School> schoolList=schoolDao.findAll();
		gradeDao = new GradeDao();
		List<Grade> gradeList = gradeDao.findAll();
		maxPage = (maxCount / count) + (maxCount % count == 0 ? 0 : 1);
		request.setAttribute("roleList", roleList);
		request.setAttribute("gradeList", gradeList);
		request.setAttribute("schoolList", schoolList);
		request.setAttribute("maxPage", maxPage);
		request.setAttribute("maxCount", maxCount);
		request.setAttribute("page", page);
		request.setAttribute("sex", sex);
		request.setAttribute("ws", URLEncoder.encode(wh, "GBK"));
		request.setAttribute("rid", rid);
		request.setAttribute("order", cond);
		request.setAttribute("sort", way);
		request.setAttribute("gid", gid);
		request.setAttribute("isVisible", isVisible);
		request.setAttribute("gradeId", gradeId);
		request.setAttribute("cid", cid);
		request.setAttribute("userList", userList);
		return "base/userAdmin";
	}

	/**
	 * 
	 * 删除用户
	 */
	public String delete(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		String[] p = (String[]) request.getAttribute("params");
		if (p != null && p[0].matches("\\d+")) {
			UserDao userDAO = new UserDao();
			Integer mid = Integer.valueOf(p[0]);
			userDAO.delete(mid);
			userDAO.deleteUP(mid);
		}
		request.removeAttribute("params");
		return "base/user_list.html";
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
		User user2 = null;
		if (_form_session.contains(_form_session_id)) {// 存在于session中
			System.out.println("正常操作");
			// user2.set姓名，生日，身份证，籍贯，手机号码
			User u = (User) request.getAttribute("form");
			userDao = new UserDao();
			if (u.getId() != null && !"".equals(u.getId()) && u.getId() > 0) {// 修改
				// 查询数据库，得到原有信息
				user2 = userDao.findUserById(u.getId());
				if (u.getMobile()!=null && !"".equals(u.getMobile())) {
					user2.setName(u.getMobile());
					Integer length = u.getMobile().length();
					user2.setPassword(Env.encode(u.getMobile().substring(length - 6,
							length)));
					user2.setMobile(u.getMobile());
				}
				user2.setNikeName(u.getNikeName());
				user2.setSex(u.getSex());
				user2.setRid(u.getRid());
				user2.setGid(u.getGid());
				user2.setBrithday(u.getBrithday());
				if (u.getRid() == 2) {//当角色为教师时，设置班级
					user2.setGradeId(u.getGradeId());
					user2.setCid(u.getCid());
				}else if (u.getRid()==3) {//当角色为学生时，设置班级，学号
					user2.setSnumber(u.getSnumber());
					user2.setGradeId(u.getGradeId());
					user2.setCid(u.getCid());
				}else {//当角色为其他用户时，不设置班级，学号
					user2.setSnumber(null);
					user2.setGradeId(null);
					user2.setCid(null);
				}
				//当角色为管理员时，不设置会员时间
				if (u.getRid()==1 || u.getRid()==5) {
					user2.setMemberEndTime(null);
				}else if (u.getRid() == 4) {
					//当父母续期会员时间时，同时更新学生会员时间
					ParentsDao parentsDao = new ParentsDao();
						List<User> s = parentsDao.findStudentByPuid(u.getId());
						Integer length = s.size();
						if (s != null) {
							for (Integer i=0; i < length; i++){
								User user=s.get(i);
								user.setMemberEndTime(u.getMemberEndTime());
								userDao.updateEndTime(user);
							}
						}
						user2.setMemberEndTime(u.getMemberEndTime());
				}else {
					user2.setMemberEndTime(u.getMemberEndTime());
				}
				user2.setSid(u.getSid());
				user2.setBirthplace(u.getBirthplace());
				user2.setIsVisible(u.getIsVisible());
			} else {// 注册
				if (u.getMobile()!=null && !"".equals(u.getMobile())) {
					u.setName(u.getMobile());
					Integer length = u.getMobile().length();
					u.setPassword(Env.encode(u.getMobile().substring(length - 6,
							length)));
				}
				Integer rid = u.getRid();
				//当用户为管理员和领导时，不添加班级和会员时间
				if (rid == 1 || rid ==5) {
					u.setCid(null);
					u.setGradeId(null);
					u.setMemberEndTime(null);
				}else{
					u.setMemberEndTime(u.getMemberEndTime());
				}
				
				//当用户为学生时，添加学号
				if (rid == 3) {
					u.setSnumber(u.getSnumber());
				}else {
					u.setSnumber(null);
				}
				user2 = u;
			}
			user2.setId(userDao.update(user2));
			for (int i = 0; i < _form_session.size(); i++) {
				if (_form_session.get(i).equals(_form_session_id)) {
					_form_session.remove(i);
				}
			}
		} else {
			System.out.println("不要重复提交啦！~~~谢谢合作");
		}
		request.getSession().setAttribute("_form_session", _form_session);
		return "base/user_updateInit-" + user2.getId();
	}

	public String updateInit(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		String[] p = (String[]) request.getAttribute("params");
		Integer roleId = 1;
		if (p != null && p[0].matches("\\d+")) {
			userDao = new UserDao();
			Integer id = Integer.valueOf(p[0]);
			User user = userDao.getUserById(id);
			roleId = user.getRid();
			request.setAttribute("user", user);
		}
		roleDao = new RoleDAO();
		List<Role> roleList = roleDao.findAll();
		schoolDao=new SchoolDAO();
		List<School> schoolList=schoolDao.findAll();
		groupsDao = new GroupsDao();
		List<Groups> groupsList = groupsDao.findGroupsByRole(roleId);
		gradeDao = new GradeDao();
		List<Grade> gradeList = gradeDao.findAll();
		request.setAttribute("now", Env.format(new Date(), "yyyy-MM-dd"));
		request.setAttribute("groupsList", groupsList);
		request.setAttribute("roleList", roleList);
		request.setAttribute("schoolList", schoolList);
		request.setAttribute("gradeList", gradeList);
		return "base/userUpdate";
	}

	/**
	 * 根据角色，查询用户
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws ServletException
	 */
	public String listByRid(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		Integer rid = Integer
				.parseInt(request.getParameter("rid") != null ? request
						.getParameter("rid") : "0");
		userDao = new UserDao();
		if (rid != null && !"".equals(rid)) {
			/**
			 * 现在的结果是：只按照角色查询了所有的用户，页面上没有分组，这样多很不好<br>
			 * 暂时这样，以后需要好好修改一下 <br>
			 * 方法一： <br>
			 * 角色--》分组--》用户 <br>
			 * 角色--》年级--》分组 --》用户 <br>
			 * 角色--》年级--》班级--》分组--》用户<br>
			 * 这种方法，对里面内容的判断有点多 <br>
			 * 方法二： <br>
			 * 分角色查詢，例如：if(rid=1){查询角色为1的相关信息} <br>
			 * 这种方法，一定要确定角色
			 * */
			// // 查询分组
			// GroupsDao groupDao = new GroupsDao();
			// List<Groups> groupList = groupDao.findGroupsByRole(rid);
			//			
			// // 查询所有年级
			// GradeDao gradeDao = new GradeDao();
			// List<Grade> gradeList = gradeDao.findGradeBySchool(1);//
			// 查询第一个学校的年级
			// // 分年级查询所有班级
			// ClazzDao clazzDao = new ClazzDao();
			// for (int i = 0; i < gradeList.size(); i++) {
			// gradeList.get(i).setClazzList(
			// clazzDao.findClazzByGrade(gradeList.get(i).getId()));
			// }
			List<User> userList = userDao.findByRid(rid);
			response.setContentType("text/html;charset=utf-8");
			response.setCharacterEncoding("UTF-8");
			response.setHeader("Cache-Control", "no-cache");

			PrintWriter out = response.getWriter();
			StringBuilder sb = new StringBuilder();
			int size = userList.size();
			int count = 1;
			for (User u : userList) {
				sb.append(u.getId() + ":" + u.getNikeName() + ":"
						+ u.getMobile());
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
	 * @param request
	 * @param response
	 * @return 通过手机号码检查用户是否已存在
	 * @throws IOException
	 * @throws ServletException
	 */
	public String checkUser(HttpServletRequest request,
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
