package com.snssly.sms.admin;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *在线支付处理
 */
public class PayAction {
	/**
	 * 加载支付页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws ServletException
	 */
	public String init(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		return "self/payinit";
	}
}
