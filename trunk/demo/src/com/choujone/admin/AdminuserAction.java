package com.choujone.admin;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * -- ��̨����Ա��¼�˳�
 * 
 * @author Administrator
 * 
 */
public class AdminuserAction {

	/**
	 * -- ����Ա��¼
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
		System.out.println(name + "��¼");
		request.setAttribute("message", name+"��¼�ɹ�");
		return "html/admin/memberList";
	}

	/**
	 * -- �˳���¼
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
