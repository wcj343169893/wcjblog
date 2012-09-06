package com.snssly.sms.admin;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.snssly.sms.dao.ClazzDao;
import com.snssly.sms.dao.SchoolDAO;
import com.snssly.sms.entity.Clazz;
import com.snssly.sms.entity.School;

public class ClazzAction {

	ClazzDao clazzDao = null;
	/**
	 * 查询所有班级 ，得到学校信息
	 * 
	 * @return
	 */
	public String list(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		clazzDao = new ClazzDao();
		SchoolDAO schoolDao = new SchoolDAO();
		//设置分页用到的变量
		Integer page = 0;
		Integer count = 10;
		Integer maxPage = 1;
		Integer maxCount = 0;		
		//提取page
		String[] p = (String[])request.getAttribute("params");
		if (p != null && p[0].matches("\\d+")) {//判断是否是数字
			page = Integer.valueOf(p[0]);
		}		
		//查询该页页面显示的数据
		//数据的总条数
		maxCount = clazzDao.getAllCount();
		List<Clazz> clazzList = new ArrayList<Clazz>();
		if(maxCount > 0){			
			clazzList = clazzDao.getClazzList(page, count);
		}
		maxPage = (maxCount / count) + (maxCount % count == 0 ? 0 : 1);

		List<School> schoolList = schoolDao.findAllAsc();		
	
		request.setAttribute("maxPage", maxPage);
		request.setAttribute("page", page);		
		request.setAttribute("clazzList", clazzList);
		request.setAttribute("schoolList", schoolList);
		return "base/clazzManager";
//		return null;
	}

	public String listByGid(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		Integer gid = Integer
				.parseInt(request.getParameter("gid") != null ? request
						.getParameter("gid") : "0");
		clazzDao = new ClazzDao();
		if (gid != null && !"".equals(gid)) {
			List<Clazz> clazzList = clazzDao.findClazzByGrade(gid);
			response.setContentType("text/html;charset=utf-8");
			response.setCharacterEncoding("UTF-8");
			response.setHeader("Cache-Control", "no-cache");

			PrintWriter out = response.getWriter();
			StringBuilder sb = new StringBuilder();
			int size = clazzList.size();
			int count = 1;
			for (Clazz c : clazzList) {
				sb.append(c.getId() + ":" + c.getName());
				if (count < size) {
					count++;
					sb.append(",");
				}
			}
			out.print(sb.toString());
			out.flush();
			out.close();
		}
		return null;
	}


//	public String listASG(HttpServletRequest request,
//			HttpServletResponse response) throws IOException, ServletException {
//
//	}
	//添加班级
	public String add(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException{
		Clazz clazz = (Clazz)request.getAttribute("form");
		clazzDao = new ClazzDao();
		clazzDao.add(clazz);
		return "base/clazz_list.html";
	}
	//修改班级信息
	public String update(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException{
		Clazz clazz = (Clazz)request.getAttribute("form");
		clazzDao = new ClazzDao();
		clazzDao.update(clazz);
		return "base/clazz_list.html";
	}
	//删除班级 BY id
	public String delete(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException{
		String[] p = (String[])request.getAttribute("params");
		if(p != null && p[0].matches("\\d+")) {
			clazzDao = new ClazzDao();
			Integer id = Integer.valueOf(p[0]);
			clazzDao.delete(id);
		}
		request.setAttribute("params", null);
		return "base/clazz_listASG.html";
	}

}
