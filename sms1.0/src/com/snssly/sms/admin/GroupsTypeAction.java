package com.snssly.sms.admin;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.snssly.sms.dao.GroupsTypeDao;
import com.snssly.sms.entity.GroupsType;

public class GroupsTypeAction {
	GroupsTypeDao groupsTypeDao = null;
	
	public String list(HttpServletRequest request, HttpServletResponse response)
	throws IOException, ServletException {
		groupsTypeDao =  new GroupsTypeDao();
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
		maxCount = groupsTypeDao.getAllCount();
		List<GroupsType> groupsTypeList = new ArrayList<GroupsType>();
		if (maxCount > 0) {
			groupsTypeList = groupsTypeDao.getGroupsTypeList(page, count);
		}
		maxPage = (maxCount / count) + (maxCount % count == 0 ? 0 : 1);
		// 返回页面的数据
		request.setAttribute("groupsTypeList", groupsTypeList);
		return "base/groupsTypeManager";
	}
	
	/**
	 * -- 添加分组类型
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws ServletException
	 */
	public String addInit(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		GroupsType groupsType = (GroupsType) request.getAttribute("form");
		groupsTypeDao = new GroupsTypeDao();
		groupsTypeDao.add(groupsType);
		// 返回页面，与jsp的类似
		return "base/groupsType_list.html";
	}

	/**
	 * -- 修改分组类型
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws ServletException
	 */
	public String update(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		GroupsType groupsType = (GroupsType) request.getAttribute("form");
		if (groupsType != null && !groupsType.getName().equals("")) {
			GroupsTypeDao gDAO = new GroupsTypeDao();
			gDAO.update(groupsType);
		}
		return "base/groupsType_list.html";
	}

	/**
	 * -- 删除分组类型
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
			groupsTypeDao = new GroupsTypeDao();
			Integer tid = Integer.valueOf(par[0]);
			/*List<Groups> gid = groupsTypeDao.findGidByTid(tid);
			groupsTypeDao.deleteGroupsByTypeId(tid);
			groupsTypeDao.updateUserByTypeId(gid);*/
			groupsTypeDao.delete(tid);
		}
		request.setAttribute("params", null);
		return "base/groupsType_list.html";
	}

}
