package com.snssly.sms.commons;

/**
 * 静态配置
 * 
 */
public class Config {
	/**
	 * 登录存入session中的值(登录信息)
	 */
	public static final String LOGIN_SESSION = "_admin_login_session_";

	/**
	 * 验证码
	 */
	public static final String IMG_CODE = "_img_code_img_session";

	/**
	 * 存入session中的权限（用户的权限）
	 */
	public static final String LOGIN_MODULE_LIST_SESSION = "_module_list_session_";

	/**
	 * 存入servletContext中的权限（所有权限）
	 */
	public static final String LOGIN_MODULE_LIST_SERVLET_CONTEXT = "_module_list_servlet_context_";
	/**
	 * 存入servletContext中的角色（所有角色）
	 */
	public static final String LOGIN_ROLE_LIST_SERVLET_CONTEXT = "_role_list_servlet_context_";
	/**
	 * 存入servletContext中的角色权限
	 */
	public static final String LOGIN_ROLE_MODULE_LIST_SERVLET_CONTEXT = "_role_module_list_servlet_context_";
}
