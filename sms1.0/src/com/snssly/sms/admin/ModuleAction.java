package com.snssly.sms.admin;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.snssly.sms.commons.Config;
import com.snssly.sms.dao.ModuleDAO;
import com.snssly.sms.dao.RoleDAO;
import com.snssly.sms.entity.Module;

/**
 * -- 模块管理
 * 
 * @author Administrator
 * 
 */
public class ModuleAction {

	/**
	 * -- 添加模块
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws ServletException
	 */
	public String add(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		Module mod = (Module) request.getAttribute("form");
		mod.setRemarks("s");
		ModuleDAO modDAO = new ModuleDAO();
		modDAO.add(mod);
		request.getSession().getServletContext().setAttribute(
				Config.LOGIN_MODULE_LIST_SERVLET_CONTEXT, modDAO.findAll());
		return "permiss/moduleManager";
	}

	/**
	 * -- 删除模块
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
			ModuleDAO modDAO = new ModuleDAO();
			Integer mid = Integer.valueOf(par[0]);
			modDAO.deleteByRoot(mid);
			modDAO.delete(mid);
			RoleDAO r = new RoleDAO();
			r.deleteRMByModule(mid);
			request.getSession().getServletContext().setAttribute(
					Config.LOGIN_MODULE_LIST_SERVLET_CONTEXT, modDAO.findAll());
		}
		request.setAttribute("params", null);
		return "permiss/moduleManager";
	}

	/**
	 * -- 更新模块
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws ServletException
	 */
	public String update(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		Module mod = (Module) request.getAttribute("form");
		mod.setRemarks("s");
		ModuleDAO modDAO = new ModuleDAO();
		modDAO.update(mod);
		request.getSession().getServletContext().setAttribute(
				Config.LOGIN_MODULE_LIST_SERVLET_CONTEXT, modDAO.findAll());
		return "permiss/mo" + "duleManager";
	}

}
