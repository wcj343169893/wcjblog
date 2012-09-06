package com.snssly.sms.admin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.snssly.sms.dao.SchoolDAO;
import com.snssly.sms.entity.School;

/**
 * --学校信息管理
 * 
 * @author Administrator
 * 
 */
public class SchoolAction {
	SchoolDAO schooldao = null;

	/**
	 * 显示所有学校 并完成分页功能
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws ServletException
	 */
	public String list(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		schooldao = new SchoolDAO();

		// 设置分页用到的变量
		Integer page = 0;
		Integer count = 10;
		Integer maxPage = 1;
		Integer maxCount = 0;
		// 提取page
		String[] p = (String[]) request.getAttribute("params");
		if (p != null && p[0].matches("\\d+")) {// 判断是否是数字
			page = Integer.valueOf(p[0]);
		}
		// 查询该页页面显示的数据
		// 数据的总条数
		maxCount = schooldao.getAllCount();
		List<School> schoolList = new ArrayList<School>();
		if (maxCount > 0) {
			schoolList = schooldao.getSchoolList(page, count);
		}
		maxPage = (maxCount / count) + (maxCount % count == 0 ? 0 : 1);
		// 返回页面的数据
		request.setAttribute("schoolList", schoolList);
		request.setAttribute("maxPage", maxPage);
		request.setAttribute("page", page);
		return "base/schoolManager";
	}

	/**
	 * -- 添加学校
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws ServletException
	 */
	public String add(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		School school = (School) request.getAttribute("form");
		schooldao = new SchoolDAO();
		school.setDescription(replaceHTML(school.getDescription()));
		schooldao.add(school);
		// 返回页面，与jsp的类似
		return "base/school_list.html";
	}

	/**
	 * 转换字符串
	 * 
	 * @param s
	 * @return
	 */
	private String replaceHTML(String s) {
		s = s.replaceAll("&amp;", "&");
		s = s.replaceAll("&lt;", "<");
		s = s.replaceAll("&gt;", ">");
		s = s.replaceAll("<br>", "\n");
		s = s.replaceAll("`", "'");
		return s;
	}

	/**
	 * 加载学校修改信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws ServletException
	 */
	public String update(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		School school = null;
		schooldao = new SchoolDAO();
		// schooldao.update(school);
		String[] p = (String[]) request.getAttribute("params");
		if (p != null && p[0].matches("\\d+")) {
			school = schooldao.getSchoolDetail(Integer.parseInt(p[0]));
		}
		request.removeAttribute("params");
		request.setAttribute("school", school);
		return list(request, response);
	}

	/**
	 * -- 删除学校
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
			schooldao = new SchoolDAO();
			Integer mid = Integer.valueOf(par[0]);
			schooldao.delete(mid);
		}
		request.setAttribute("params", null);
		return "base/school_list.html";
	}

	/**
	 * -- 学校消息
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws ServletException
	 */

	public String info(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		Integer id = 1;
		schooldao = new SchoolDAO();
		School school = schooldao.getSchoolDetail(id);
		request.setAttribute("school", school);
		return "self/schoolInfo";
	}
}
