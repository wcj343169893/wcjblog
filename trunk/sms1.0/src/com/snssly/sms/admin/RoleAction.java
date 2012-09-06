package com.snssly.sms.admin;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.snssly.sms.commons.Config;
import com.snssly.sms.dao.RoleDAO;
import com.snssly.sms.entity.Role;

/**
 * -- 角色功能处理
 * 
 * @author Administrator
 * 
 */
public class RoleAction {

	/**
	 * -- 修改角色关联
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws ServletException
	 */
	public String updateRM(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		String[] par = (String[]) request.getAttribute("params");
		if (par == null)
			return "html/permiss/roleManager";
		RoleDAO rDAO = new RoleDAO();
		Integer rid = null;
		if (par[0].matches("\\d+")) {
			rid = Integer.valueOf(par[0]);
			rDAO.deleteRMByRole(rid);
		}
		for (int i = 1; i < par.length && rid != null; i++) {
			Integer mid = null;
			if (par[i].matches("\\d+")) {
				mid = Integer.valueOf(par[i]);
				rDAO.addRM(rid, mid);
			}
		}
		request.getSession().getServletContext()
				.setAttribute(Config.LOGIN_ROLE_MODULE_LIST_SERVLET_CONTEXT,
						rDAO.findAllRM());
		return "permiss/roleManager";
	}

	/**
	 * -- 添加角色
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws ServletException
	 */
	public String addRole(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		Role role = (Role) request.getAttribute("form");
		if (role != null && !role.getName().equals("")) {
			role.setRemarks(role.getName());
			RoleDAO rDAO = new RoleDAO();
			rDAO.add(role);
			request.getSession().getServletContext().setAttribute(
					Config.LOGIN_ROLE_LIST_SERVLET_CONTEXT, rDAO.findAll());
			request.getSession().getServletContext().setAttribute(
					Config.LOGIN_ROLE_MODULE_LIST_SERVLET_CONTEXT,
					rDAO.findAllRM());
		}
		return "permiss/roleManager";
	}

	/**
	 * -- 删除角色
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws ServletException
	 */
	public String deleteRole(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		String[] par = (String[]) request.getAttribute("params");
		if (par != null && par[0].matches("\\d+")) {
			RoleDAO rDAO = new RoleDAO();
			rDAO.deleteRMByRole(Integer.valueOf(par[0]));
			rDAO.delete(Integer.valueOf(par[0]));
			// AdminuserDAO adDAO = new AdminuserDAO();
			// adDAO.deleteARByRole(Integer.valueOf(par[0]));
			request.getSession().getServletContext().setAttribute("roles",
					rDAO.findAll());
			// request.getSession().getServletContext().setAttribute("arlist",
			// adDAO.findAR());
			request.getSession().getServletContext().setAttribute(
					Config.LOGIN_ROLE_MODULE_LIST_SERVLET_CONTEXT,
					rDAO.findAllRM());
			request.getSession().getServletContext().setAttribute(
					Config.LOGIN_ROLE_LIST_SERVLET_CONTEXT, rDAO.findAll());
		}
		return "permiss/roleManager";
	}

	/**
	 * -- 修改角色
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws ServletException
	 */
	public String updateRole(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		Role role = (Role) request.getAttribute("form");
		if (role != null && !role.getName().equals("")) {
			role.setRemarks(role.getName());
			RoleDAO rDAO = new RoleDAO();
			rDAO.update((role));
			request.getSession().getServletContext().setAttribute(
					Config.LOGIN_ROLE_LIST_SERVLET_CONTEXT, rDAO.findAll());
			request.getSession().getServletContext().setAttribute(
					Config.LOGIN_ROLE_MODULE_LIST_SERVLET_CONTEXT,
					rDAO.findAllRM());
		}
		return "permiss/roleManager";
	}
}
