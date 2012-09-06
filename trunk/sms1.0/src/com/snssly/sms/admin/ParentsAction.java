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

import com.snssly.sms.commons.Config;
import com.snssly.sms.commons.Env;
import com.snssly.sms.dao.ClazzDao;
import com.snssly.sms.dao.GradeDao;
import com.snssly.sms.dao.GroupsDao;
import com.snssly.sms.dao.ParentsDao;
import com.snssly.sms.dao.StudentDao;
import com.snssly.sms.entity.Clazz;
import com.snssly.sms.entity.Groups;
import com.snssly.sms.entity.User;

public class ParentsAction {
	ParentsDao parentsDao = null;
	GroupsDao groupsDao = null;
	GradeDao gradeDao = null;
	ClazzDao clazzDao = null;
	StudentDao studentDao = null;

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
		parentsDao = new ParentsDao();
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
		String cond = request.getParameter("order") != null ? request
						.getParameter("order").trim() : "1";// 按列查询
		String way = request.getParameter("sort") != null ? request
								.getParameter("sort").trim() : "desc";// 按升降序查询
		if (p != null && p.length > 1) {
			sex = p[1];
		}
		Integer cd = 1;
		if (p != null && p.length > 2) {
			cond = p[2];
			cd = Integer.parseInt(cond);
		}
		if (p != null && p.length > 3) {
			way = p[3];
		}
		if (p != null && p.length > 4) {
			wh = p[4];
			wh = new String(wh.getBytes("ISO-8859-1"), "GBK");
		}
		Integer s = Integer.parseInt(sex);
		List<User> parentsList = new ArrayList<User>();
		maxCount = parentsDao.getAllCount(wh, cid, s);
		if (maxCount > 0) {
			List<User> ul = parentsDao.getUserList(page, count, wh, cid, s,cd,way);
			// 将搜索的关键词替换成其他颜色
			if (ul != null && ul.size() > 0 && wh != null
					&& !"".equals(wh.trim())) {
				for (User u : ul) {
					u.setNikeName(u.getNikeName().replaceAll(wh,
							"<span class='search_key'>" + wh + "</span>"));
					u.setPnikeName(u.getPnikeName().replaceAll(wh,
							"<span class='search_key'>" + wh + "</span>"));
					parentsList.add(u);
				}
			} else {
				parentsList = ul;
			}
		}
		maxPage = (maxCount / count) + (maxCount % count == 0 ? 0 : 1);
		request.setAttribute("maxPage", maxPage);
		request.setAttribute("maxCount", maxCount);
		request.setAttribute("page", page);
		request.setAttribute("ws", URLEncoder.encode(wh, "GBK"));
		request.setAttribute("sex", s);
		request.setAttribute("order", cd);
		request.setAttribute("sort", way);
		request.setAttribute("parentsList", parentsList);
		request.setAttribute("clazz", clazz);
		return "user/parentsManager";
	}

	/**
	 * 
	 * 删除用户
	 */
	public String delete(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		String[] p = (String[]) request.getAttribute("params");
		ParentsDao parentsDAO = new ParentsDao();
		Integer cuid = 0;
		if (p != null && p[0].matches("\\d+")) {
			Integer puid = Integer.valueOf(p[0]);
			Integer length = parentsDAO.countParentByPuid(puid);
			// 根据ID 将user的状态设置为禁止
			if (length == 1) {
				parentsDAO.delete(puid);
			}
			if (p != null && p.length > 1 && p[1].matches("\\d+")) {
				Integer upid = Integer.valueOf(p[1]);
				// 删除关系
				parentsDAO.deleteUP(upid);
			}
			if (p != null && p.length > 2 && p[2].matches("\\d+")) {
				cuid = Integer.valueOf(p[2]);
			}
		}
		request.removeAttribute("params");
		// request.setAttribute("cuid", cuid);
		return "user/parents_updateInit-" + cuid;
		// return "user/parentsUpdate";
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
		Integer cuid = 0;
		if (_form_session.contains(_form_session_id)) {// 存在于session中
			System.out.println("正常操作");
			User parents = (User) request.getAttribute("form");
			cuid = parents.getCuid();
			ParentsDao parentsDao = new ParentsDao();
			Integer puid = parents.getPuid();
			Integer upid = parents.getUpid();
			Integer rid = 4;
			groupsDao = new GroupsDao();
			Groups groups = groupsDao.getGroups(rid);
			Integer gid = groups.getId();
			if (upid == null || "".equals(upid) || upid < 1) {
				if (puid == null || puid < 1) {
					parents.setName(parents.getMobile());
					Integer length = parents.getMobile().length();
					parents.setPassword(Env.encode(parents.getMobile()
							.substring(length - 6, length)));
					parents.setGid(gid);
					parents.setPuid(parentsDao.updateParents(parents));
				} else {
					parents.setPuid(puid);
				}
			} else {
				parents.setId(puid);
				parents.setName(parents.getMobile());
				Integer length = parents.getMobile().length();
				parents.setPassword(Env.encode(parents.getMobile().substring(
						length - 6, length)));
				parents.setGid(gid);
				parents.setPuid(parentsDao.updateParents(parents));
				parents.setCuid(cuid);
				parents.setPuid(puid);
				parents.setUpid(upid);
			}
			parentsDao.updateUP(parents);

			for (int i = 0; i < _form_session.size(); i++) {
				if (_form_session.get(i).equals(_form_session_id)) {
					_form_session.remove(i);
				}
			}
		} else {
			System.out.println("不要重复提交啦！~~~谢谢合作");
		}
		request.getSession().setAttribute("_form_session", _form_session);
		return "user/parents_updateInit-" + cuid;
	}

	/**
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws ServletException
	 */
	public String updateInit(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		String[] p = (String[]) request.getAttribute("params");
		Integer id = null;
		if (p != null && p[0].matches("\\d+")) {
			id = Integer.valueOf(p[0]);
		} else {
			String cuids = request.getAttribute("cuid") != null ? request
					.getAttribute("cuid").toString() : "0";
			System.out.println(cuids);
			id = Integer.parseInt(cuids);
		}
		User user = (User) request.getSession().getAttribute(
				Config.LOGIN_SESSION);
		Integer cid = user.getCid();
		clazzDao = new ClazzDao();
		Clazz clazz = clazzDao.findClazzByUid(cid);
		parentsDao = new ParentsDao();
		List<User> parents = parentsDao.findParentsById(id);
		request.setAttribute("parentsList", parents);
		studentDao = new StudentDao();
		User student = studentDao.findUserById(id);
		request.setAttribute("now", Env.format(new Date(), "yyyy-MM-dd"));
		request.setAttribute("student", student);
		request.setAttribute("clazz", clazz);
		return "user/parentsUpdate";
	}

	/**
	 * @param request
	 * @param response
	 * @return通过手机号码检查用户是否已存在
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

			parentsDao = new ParentsDao();
			List<User> parentsList = parentsDao.findParentsByMobile(mobile);
			if (parentsList != null) {
				Iterator it = parentsList.iterator();
				while (it.hasNext()) {
					User p = (User) it.next();
					sb.append(p.getPuid() + "_" + p.getPnikeName() + "_"
							+ p.getNikeName() +"_"+ p.getRid()+"_"+p.getRname()+",");
				}
				out.print(sb.toString().substring(0, sb.length()));
				out.flush();
				out.close();
			}
		}
		return null;
	}

}
