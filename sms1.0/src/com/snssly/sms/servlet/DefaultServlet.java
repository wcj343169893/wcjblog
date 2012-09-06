package com.snssly.sms.servlet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;

import com.snssly.sms.commons.Action;
import com.snssly.sms.commons.Config;
import com.snssly.sms.commons.Env;
import com.snssly.sms.dao.ModuleDAO;
import com.snssly.sms.dao.OnlineDao;
import com.snssly.sms.dao.RoleDAO;
import com.snssly.sms.entity.Module;
import com.snssly.sms.entity.Role;
import com.snssly.sms.entity.User;

public class DefaultServlet extends HttpServlet {
	private Logger logger = Logger.getLogger(this.getClass());

	private static final long serialVersionUID = 1L;

	public static String CONFIG = null;

	public static String DEFAULT = null;

	public static String INTER = null;

	public static String COMMAND = null;
	public static OnlineDao onlineDao = new OnlineDao();

	/**
	 * 字符编码
	 */
	public static String ENCODE = null;

	// public static boolean isVerify = false;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (!verify(request)) {
			request.getRequestDispatcher("/admin/manager/commons/error404.jsp")
					.forward(request, response);
			return;
		}
		// 获取Servlet路径
		String path = request.getPathInfo();
		logger.info(request.getRemoteAddr() + "\t" + path);
		// 转换路径
		String[] keys = this.analyzePath(path, request);
		if (keys == null) {
			request.getRequestDispatcher("/admin/manager/commons/error404.jsp")
					.forward(request, response);
		} else {
			// 处理功能
			String value = this.process(keys, request, response);
			if (value != null && !value.equals("")) {
				request.setAttribute("inc", value);
				request.getRequestDispatcher(
						"/admin/manager/commons/default.jsp").forward(request,
						response);
			} else {
				// request.getRequestDispatcher(
				// "/admin/manager/commons/error404.jsp").forward(request,
				// response);
			}
		}
	}

	/*
	 * (non-Javadoc) -- 初始化参数
	 * 
	 * @see javax.servlet.GenericServlet#init()
	 */
	public void init() throws ServletException {
		CONFIG = this.getServletContext().getInitParameter("configFile");
		DEFAULT = this.getServletContext().getInitParameter("defaultPath");
		INTER = this.getServletContext().getInitParameter("inter");
		COMMAND = this.getServletContext().getInitParameter("command");
		ENCODE = this.getServletContext().getInitParameter("encode");
		Env.CONFIG = CONFIG;
		// 初始化数据
		this.initData();
	}

	/**
	 * -- 分析路径
	 * 
	 * @param path
	 * @return
	 */
	private String[] analyzePath(final String path, HttpServletRequest request) {
		String[] keys = new String[2];
		int length = path.length();
		if (path == null || length < 10) {
			return null;
		}
		if (!path.toLowerCase().matches(".*" + DEFAULT)) {
			return null;
		}
		String value = path.replaceAll("[" + COMMAND + "|\\" + INTER
				+ "|\\.]+.*", "");
		keys[0] = value.substring(1);
		value = null;
		value = path.replaceAll(".*_", "").replaceAll("\\W+.*", "");
		if (value != null && !value.equals("")) {
			keys[1] = value;
		}
		value = path.substring(1, length - DEFAULT.length() - 1).replaceAll(
				"\\" + INTER + "+", INTER);
		int ro = value.indexOf(INTER);
		if (ro <= 0) {
			return keys;
		}
		value = value.substring(ro + 1);
		if (value != null && !value.equals("")) {
			String[] params = value.split(INTER);
			request.setAttribute("params", params);
		}
		return keys;
	}

	/**
	 * -- DoGet process
	 * 
	 * @param keys
	 * @param request
	 * @param response
	 * @return
	 */
	private String process(final String[] keys, HttpServletRequest request,
			HttpServletResponse response) {
		if (keys != null && keys[1] == null) {
			String web = Env.read("WEBPATH");
			return Env.read(web, keys[0]);
		} else {
			// 调用功能中转器
			Action action = new Action();
			String value = action.execute(response, request, keys[0], keys[1]);
			if (value == null || value.equals(""))
				return null;
			value = "/" + value + "." + DEFAULT;
			String keys1[] = this.analyzePath(value, request);
			if (keys1 != null) {
				return this.process(keys1, request, response);
			}
		}
		return null;
	}

	/**
	 * The doPost method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to
	 * post.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 从参数中获得BEAN名称
		String formName = request.getParameter("formName");
		// 判断BEAN名称是否为空
		if (formName != null && !formName.equals("")) {
			if (!verify(request)) {
				request.getRequestDispatcher(
						"/admin/manager/commons/error404.jsp").forward(request,
						response);
				return;
			}
			// 获得BEAN的实例
			Object entity = Env.forName(Env.PACKAGE + formName);
			if (entity != null) {
				// 获得表单所有参数<paramName,paramValue>
				Map<String, String[]> paramMap = request.getParameterMap();
				// 转换所有HTML符号
				paramMap = this.replaceHTML(paramMap);
				try {
					// 将参数数据装入BEAN实例
					BeanUtils.populate(entity, paramMap);
					// 将BEAN实例装入request
					request.setAttribute("form", entity);
				} catch (IllegalAccessException e) {
					logger.error("Illegal access in Form:" + formName);
				} catch (InvocationTargetException e) {
					logger.error("The form cannot invocation target:"
							+ formName);
				}
			}
		}
		this.doGet(request, response);

	}

	/**
	 * 转换HTML符号
	 * 
	 * @param paramMap
	 * @return
	 */
	private Map<String, String[]> replaceHTML(Map<String, String[]> paramMap) {
		Map<String, String[]> params = new HashMap<String, String[]>();
		Iterator<String> iter = paramMap.keySet().iterator();
		while (iter.hasNext()) {
			String element = (String) iter.next();
			String[] s = paramMap.get(element);
			s[0] = s[0].replaceAll("&", "&amp;");
			s[0] = s[0].replaceAll("<", "&lt;");
			s[0] = s[0].replaceAll(">", "&gt;");
			s[0] = s[0].replaceAll("\n", "<br>");
			s[0] = s[0].replaceAll("'", "`");
			params.put(element, s);
		}
		return params;
	}

	/**
	 * 判断用户是否登录
	 * 
	 * @param request
	 * @return
	 */
	private boolean isLogin(HttpServletRequest request) {
		return request.getSession().getAttribute(Config.LOGIN_SESSION) != null;
	}

	/**
	 * 判断是否拥有足够的权限
	 * 
	 * @return
	 */
	private boolean verify(HttpServletRequest request) {
		boolean flag = false;
		String path = "";
		try {
			// 获取访问路径
			path = request.getPathInfo();
			String[] strs = analyzePath(path, request);
			// 从session中取出自己拥有的权限
			if (strs[0] != null && strs[0].indexOf("/") != -1
					&& isLogin(request)) {// 登录
				List<Module> moduleList_session = (List<Module>) request
						.getSession().getAttribute(
								Config.LOGIN_MODULE_LIST_SESSION);
				if (moduleList_session != null && moduleList_session.size() > 0) {
					// 比较小权限(menu)
					for (Module module_own : moduleList_session) {
						String url_commend = strs[0].substring(strs[0]
								.indexOf("/") + 1);
						if (url_commend != null
								&& module_own.getUrl().indexOf(url_commend) != -1) {
							// 判断小权限
							if (strs[1] != null) {
								// System.out.println(module_own.getUrl()+"   "+module_own.getMenu()+"   "+module_own.getMenu()
								// .indexOf(strs[1]));
								if (module_own.getMenu() != null
										&& module_own.getMenu()
												.indexOf(strs[1]) != -1) {
									flag = true;
									break;
								}
							} else {
								flag = true;
								break;
							}
						}
					}
				}
			} else if (strs[0] != null && strs[0].equals("index")
					&& isLogin(request)) {// 请求主页
				flag = true;
			} else if (strs[0] != null && strs[0].equals("adminuser")) {// 登录，放行
				flag = true;
			}
		} catch (Exception e) {
			flag = false;
		}
		if (!flag) {
			// 清理门户
			onlineDao.deleteOnline();
			logger.info("权限不足,访问路径是:" + path);
		} else {
			User user = (User) request.getSession().getAttribute(
					Config.LOGIN_SESSION);
			if (user != null) {
				// 更新登录状态
				onlineDao.updateOnline(user.getId(), Env.getIpAddr(request));
			}
		}
		return flag;
	}

	/**
	 * 初始化数据
	 */
	private void initData() {
		ServletContext app = this.getServletContext();
		ModuleDAO modDAO = new ModuleDAO();
		List<Module> moduleList = modDAO.findAll();
		app.setAttribute(Config.LOGIN_MODULE_LIST_SERVLET_CONTEXT, moduleList);
		RoleDAO roleDAO = new RoleDAO();
		List<Role> roleList = roleDAO.findAll();
		app.setAttribute(Config.LOGIN_ROLE_LIST_SERVLET_CONTEXT, roleList);
		app.setAttribute(Config.LOGIN_ROLE_MODULE_LIST_SERVLET_CONTEXT, roleDAO
				.findAllRM());
		// AdminuserDAO adDAO = new AdminuserDAO();
		// app.setAttribute("arlist", adDAO.findAR());
		// VirtualTypeDAO vtDAO = new VirtualTypeDAO();
		// app.setAttribute("vtype", vtDAO.findAll());
	}
}
