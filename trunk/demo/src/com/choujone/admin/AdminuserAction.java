package com.choujone.admin;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * -- 后台管理员登录退出
 * 
 * @author Administrator
 * 
 */
public class AdminuserAction {

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
		String name = request.getParameter("name");
		System.out.println(name + "登录");
		request.setAttribute("message", name+"登录成功");
		return "html/admin/memberList";
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
		request.getSession().removeAttribute("admin_info");
		response.sendRedirect("/index.jsp");
		return null;
	}
}
