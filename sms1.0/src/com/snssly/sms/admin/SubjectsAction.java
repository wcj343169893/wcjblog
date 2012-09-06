package com.snssly.sms.admin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.snssly.sms.dao.SubjectDao;
import com.snssly.sms.entity.Subjects;

public class SubjectsAction {
	SubjectDao subjectDao = null;

	/**
	 * 查询所有分组
	 * 
	 * */
	public String list(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		subjectDao = new SubjectDao();
		// 设置分页用到的变量
		Integer page = 0;
		Integer count = 8;
		Integer maxPage = 1;
		Integer maxCount = 0;
		// 提取page
		String[] p = (String[]) request.getAttribute("params");
		if (p != null && p[0].matches("\\d+")) {// 判断是否是数字
			page = Integer.valueOf(p[0]);
		}
		// 查询该页页面显示的数据
		// 数据的总条数
		maxCount = subjectDao.getAllCount();
		List<Subjects> subjectsList = new ArrayList<Subjects>();
		if (maxCount > 0) {
			subjectsList = subjectDao.getSubjectsList(page, count);
		}
		maxPage = (maxCount / count) + (maxCount % count == 0 ? 0 : 1);
		// 返回页面的数据
		request.setAttribute("maxPage", maxPage);
		request.setAttribute("maxCount", maxCount);
		request.setAttribute("page", page);
		request.setAttribute("subjectsList", subjectsList);
		return "base/subjectsManager";
	}
	
	/**
	 * -- 添加分组
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws ServletException
	 */
	public String add(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		Subjects subjects = (Subjects) request.getAttribute("form");
		subjectDao = new SubjectDao();
		subjectDao.add(subjects);
		return "base/subjects_list.html";
	}
	/**
	 * -- 修改分组
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws ServletException
	 */
	public String update(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		Subjects subjects = (Subjects) request.getAttribute("form");
		if (subjects != null && !subjects.getName().equals("")) {
			subjectDao = new SubjectDao();
			subjectDao.update(subjects);
		}
		return "base/subjects_list.html";
	}
	
	/**
	 * -- 删除分组
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
			subjectDao = new SubjectDao();
			Integer id = Integer.valueOf(par[0]);
			subjectDao.delete(id);
			}
		request.setAttribute("params", null);
		return "base/subjects_list.html";
		}
}
