package com.google.choujone.blog.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import com.google.choujone.blog.util.Tools;

public class AdminFilter implements Filter {
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		if (!Tools.isLogin(request)) {
			HttpServletResponse resp = (HttpServletResponse) response;
//			UserService userService = UserServiceFactory.getUserService();
			//直接跳转到google的登录界面
//			String login_url=userService.createLoginURL(((HttpServletRequest)request).getRequestURI());
			String login_url="/login.jsp";
			resp.sendRedirect(login_url);
			return;
		}
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {

	}

}