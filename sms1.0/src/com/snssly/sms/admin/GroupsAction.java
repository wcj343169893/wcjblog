package com.snssly.sms.admin;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.snssly.sms.dao.GroupsDao;
import com.snssly.sms.dao.GroupsTypeDao;
import com.snssly.sms.dao.RoleDAO;
import com.snssly.sms.entity.Groups;
import com.snssly.sms.entity.GroupsType;
import com.snssly.sms.entity.Role;

public class GroupsAction {
	GroupsDao groupsDao = null;
	GroupsTypeDao groupsTypeDao = null;
	RoleDAO roleDAO = null;

	/**
	 * 查询所有分组
	 * 
	 * */

	public String list(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		groupsDao = new GroupsDao();

		// 设置分页用到的变量
		Integer page = 0;
		Integer count = 10;
		Integer maxPage = 1;
		Integer maxCount = 0;
		// 提取page
		String[] p = (String[]) request.getAttribute("params");
		if (p != null && p[0].matches("\\d+")) {// 判断是否是数字
			page = Integer.valueOf(p[0]);
		}
		// 查询该页页面显示的数据
		// 数据的总条数
		maxCount = groupsDao.getAllCount();
		List<Groups> groupsList = new ArrayList<Groups>();
		if (maxCount > 0) {
			groupsList = groupsDao.getGroupsList(page, count);
		}
		maxPage = (maxCount / count) + (maxCount % count == 0 ? 0 : 1);
		// 返回页面的数据
		roleDAO = new RoleDAO();
		List<Role> roleList = roleDAO.findAll();//查询角色
		groupsTypeDao = new GroupsTypeDao();
		List<GroupsType> groupsTypeList = groupsTypeDao.findAll();//查询分组类型
		request.setAttribute("roleList", roleList);
		request.setAttribute("groupsTypeList", groupsTypeList);
		request.setAttribute("maxPage", maxPage);
		request.setAttribute("page", page);
		request.setAttribute("groupsList", groupsList);
		return "base/groupsManager";
	}

	/**
	 * -- 根据角色查询分组
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
		groupsDao = new GroupsDao();
		if (rid != null && !"".equals(rid)) {
			List<Groups> groupsList = groupsDao.findGroupsByRole(rid);
			response.setContentType("text/html;charset=utf-8");
			response.setCharacterEncoding("UTF-8");
			response.setHeader("Cache-Control", "no-cache");

			PrintWriter out = response.getWriter();
			StringBuilder sb = new StringBuilder();
			int size = groupsList.size();
			int count = 1;
			for (Groups g : groupsList) {
				sb.append(g.getId() + ":" + g.getName());
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
	 * -- 添加分组
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws ServletException
	 */
	public String addInit(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		Groups groups = (Groups) request.getAttribute("form");
		groupsDao = new GroupsDao();
		groupsDao.add(groups);
		// 返回页面，与jsp的类似
		return "base/groups_list.html";
	}

	/**
	 * -- 修改分组
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws ServletException
	 */
	public String update(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		Groups groups = (Groups) request.getAttribute("form");
		if (groups != null && !groups.getName().equals("")) {
			GroupsDao gDAO = new GroupsDao();
			gDAO.update(groups);
		}
		return "base/groups_list.html";
	}

	/**
	 * -- 删除分组
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws ServletException
	 */
	public String delete(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		String[] par = (String[]) request.getAttribute("params");
		if (par != null && par[0].matches("\\d+")) {
			groupsDao = new GroupsDao();
			Integer id = Integer.valueOf(par[0]);
			groupsDao.delete(id);
		}
		request.setAttribute("params", null);
		return "base/groups_list.html";
	}

}
