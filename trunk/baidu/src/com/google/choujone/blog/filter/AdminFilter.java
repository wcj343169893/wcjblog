package com.google.choujone.blog.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.choujone.blog.util.Tools;

public class AdminFilter implements Filter {
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		if (!Tools.isLogin(request)) {
			HttpServletResponse resp = (HttpServletResponse) response;
			UserService userService = UserServiceFactory.getUserService();
			//网站登陆页面
			String login_url = "/login.jsp";
			// google的登录界面
			login_url = userService
					.createLoginURL(((HttpServletRequest) request)
							.getRequestURI());
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