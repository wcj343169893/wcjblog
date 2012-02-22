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

import com.google.choujone.blog.util.Config;

public class WebFilter implements Filter {

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1,
			FilterChain chain) throws IOException, ServletException {
		// TODO 不过滤admin 只过滤前台代码
		if (!Config.isClose()) {
			chain.doFilter(arg0, arg1);
			return;
		}
		HttpServletRequest req = (HttpServletRequest) arg0;
		String url = req.getRequestURI();
		String filter = "album,index.jsp,leaveMessage.jsp,blog_detail.jsp,blog,login.jsp";
		String[] filters = filter.split(",");
		String temp[] = url.split("/");
		boolean isFilter = temp.length==0;
		for (String string : filters) {
			if (url != null && url.indexOf(string) != -1) {
				isFilter = true;
				break;
			}
		}
		if (!isFilter) {
			chain.doFilter(arg0, arg1);
		} else {
			HttpServletResponse resp = (HttpServletResponse) arg1;
			resp.sendRedirect("/close.jsp");
		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

}
