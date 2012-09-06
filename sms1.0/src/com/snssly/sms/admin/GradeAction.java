package com.snssly.sms.admin;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.XMLOutputter;

import com.snssly.sms.dao.GradeDao;
import com.snssly.sms.dao.GradeSubjectDao;
import com.snssly.sms.dao.SchoolDAO;
import com.snssly.sms.dao.SubjectDao;
import com.snssly.sms.entity.Grade;
import com.snssly.sms.entity.School;
import com.snssly.sms.entity.Subjects;

public class GradeAction {

	GradeDao gradeDao = null;
	SchoolDAO schooldao = null;

	/**
	 * 查询年级和学校 并完成分页功能
	 */
	public String list(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		// gradeDao = new GradeDao();
		// List<Grade> gradeList = gradeDao.findAll();
		// request.setAttribute("gradeList", gradeList);
		// return "";
		gradeDao = new GradeDao();
		SchoolDAO schoolDao = new SchoolDAO();
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
		maxCount = gradeDao.getAllCount();
		List<Grade> gradeList = new ArrayList<Grade>();
		if (maxCount > 0) {
			gradeList = gradeDao.getGradeList(page, count);
		}

		// 返回页面的数据

		List<School> schoolList = schoolDao.findAllAsc();
		// 查询所有科目
		SubjectDao sdao = new SubjectDao();
		request.setAttribute("subjectList", sdao.findAll());
		request.setAttribute("maxPage", maxPage);
		request.setAttribute("page", page);
		request.setAttribute("gradeList", gradeList);
		request.setAttribute("schoolList", schoolList);
		return "base/gradeManager";
	}

	// public String gradeASchool(HttpServletRequest request,
	// HttpServletResponse response)
	// throws IOException, ServletException {
	//		
	//
	// }
	/**
	 * 添加新的年级
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws ServletException
	 */
	public String add(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		Grade grade = (Grade) request.getAttribute("form");
		gradeDao = new GradeDao();
		gradeDao.add(grade);
		return "base/grade_list.html";
	}

	/**
	 * 修改年级信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws ServletException
	 */
	public String update(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		Grade grade = (Grade) request.getAttribute("form");
		gradeDao = new GradeDao();
		gradeDao.update(grade);
		return "base/grade_list.html";
	}

	/**
	 * 删除年级
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws ServletException
	 */
	public String delete(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		String[] p = (String[]) request.getAttribute("params");
		if (p != null && p[0].matches("\\d+")) {
			gradeDao = new GradeDao();
			Integer gid = Integer.valueOf(p[0]);
			gradeDao.delete(gid);
		}
		request.setAttribute("params", null);
		return "base/grade_list.html";
	}

	// 学校与年级的联动
	public String listBySid(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		// 得到sid
		Integer sid = Integer
				.parseInt(request.getParameter("sid") != null ? request
						.getParameter("sid") : "0");
		gradeDao = new GradeDao();

		if (sid != null && !"".equals(sid)) {
			List<Grade> gradeList = gradeDao.findGradeBySchool(sid);
			response.setContentType("text/html;charset=utf-8");
			response.setCharacterEncoding("UTF-8");
			response.setHeader("Cache-Control", "no-cache");

			PrintWriter out = response.getWriter();
			StringBuilder sb = new StringBuilder();
			int size = gradeList.size();
			int count = 1;
			for (Grade c : gradeList) {
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

	/**
	 * 查询本年级的所有科目
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws ServletException
	 */
	public String subject(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		// 得到gid
		Integer gid = Integer
				.parseInt(request.getParameter("gid") != null ? request
						.getParameter("gid") : "0");
		String sids = request.getParameter("sids") != null ? request
				.getParameter("sids") : "";
		if (gid != null && !"".equals(gid) && gid > 0) {
			SubjectDao sdao = new SubjectDao();
			response.setContentType("text/xml;charset=utf-8");
			response.setCharacterEncoding("UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			PrintWriter out = response.getWriter();
			List<Subjects> subjectsList = sdao.findAll();
			List<Subjects> gradeSubjectsList = sdao.getByGid(gid);

			Element rootElt = new Element("subjectsList"); // 创建跟节点，名称为subjectsList
			Element subjects = new Element("subjects");
			Element id = new Element("id");
			Element name = new Element("name");
			Element isChecked = new Element("isChecked");
			for (int i = 0; i < subjectsList.size(); i++) {
				Subjects sub = subjectsList.get(i);
				subjects = new Element("subjects");
				id = new Element("id");
				name = new Element("title");
				isChecked = new Element("isChecked");

				id.addContent(sub.getId() + "");
				name.addContent(sub.getName());
				boolean flag = false;
				for (int j = 0; j < gradeSubjectsList.size(); j++) {
					if (gradeSubjectsList.get(j).getId() == sub.getId()) {
						flag = true;
						break;
					}
				}
				isChecked.addContent(flag + "");
				subjects.addContent(id);
				subjects.addContent(name);
				subjects.addContent(isChecked);

				rootElt.addContent(subjects);
			}
			Document doc = new Document(rootElt);
			XMLOutputter outXML = new XMLOutputter(); // 定义新XML文档
			String xmlStr = outXML.outputString(doc);
			out.print(xmlStr);
			out.flush();
			out.close();
		} else if (sids != null && !"".equals(sids.trim())) {// 保存年级的科目
			response.setContentType("text/html;charset=utf-8");
			response.setCharacterEncoding("UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			PrintWriter out = response.getWriter();
			// 格式 年级编号-科目-科目-科目-科目
			String[] sid = sids.split("-");
			if (sid != null && sid.length > 0 && sid[0].matches("\\d+")) {
				gid = Integer.parseInt(sid[0]);// 得到年级编号
			}
			List<String> sidList = new ArrayList<String>();
			for (int i = 1; i < sid.length; i++) {
				sidList.add(sid[i]);
			}
			if (sidList.size() > 0) {
				GradeSubjectDao gsd = new GradeSubjectDao();
				gsd.delete(gid);
				gsd.add(gid, sidList);
				out.print("操作成功");
			} else {
				out.print("未选择科目");
			}
			out.flush();
			out.close();
		}
		return null;
	}
}
