package com.snssly.sms.servlet;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class EncodeServlet implements Filter {

	private FilterConfig filterConfig = null;
	public void destroy() {
		// TODO Auto-generated method stub
		filterConfig = null;
	}

	/* (non-Javadoc) 字符编码过滤
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		String encode = filterConfig.getInitParameter("encode");
		if(encode!=null&&!encode.equals("")){
			request.setCharacterEncoding(encode);
			response.setCharacterEncoding(encode);
		}else{
			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("utf-8");
		}
		chain.doFilter(request, response);
	}

	public void init(FilterConfig config) throws ServletException {
		// TODO Auto-generated method stub
		this.filterConfig=config;
	}

}