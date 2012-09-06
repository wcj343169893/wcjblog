package com.snssly.sms.admin;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.snssly.sms.commons.Config;
import com.snssly.sms.dao.ClazzDao;
import com.snssly.sms.dao.ExamDao;
import com.snssly.sms.dao.ExamSubjectDao;
import com.snssly.sms.dao.SubjectDao;
import com.snssly.sms.entity.Clazz;
import com.snssly.sms.entity.Exam;
import com.snssly.sms.entity.ExamSubject;
import com.snssly.sms.entity.Subjects;
import com.snssly.sms.entity.User;

public class ExamAction {

	ExamDao examDao = null;

	/**
	 * 查询所有班级 ，得到学校信息
	 * 
	 * @return
	 */
	public String addInit(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		User user = (User) request.getSession().getAttribute(
				Config.LOGIN_SESSION);
		Integer eid = 0;
		examDao = new ExamDao();
		SubjectDao subjectDao = new SubjectDao();
		Exam exam = new Exam();		
//			初始化考试时间为当前时间
		Date date = new Date();
		SimpleDateFormat from = new SimpleDateFormat("yyyy-MM-dd");
		String examTime = from.format(date);		
		List<Subjects> subjects_checked = new ArrayList<Subjects>();
		String[] p = (String[]) request.getAttribute("params");
		if (p != null && p[0].matches("\\d+")) {
			eid = Integer.valueOf(p[0]);
			exam = examDao.findById(eid);
			subjects_checked = subjectDao.findByEid(eid);			
		}	
		
		Integer uid = user.getId();// 从session得到
		List<Subjects> subjectList = subjectDao.list(uid);
		request.setAttribute("eid", eid);
		request.setAttribute("examTime", examTime);
		request.setAttribute("subjectList", subjectList);
		request.setAttribute("subjects_checked", subjects_checked);
		request.setAttribute("exam", exam);
		return "exam/examAddInit";
	}

	// 新增考试信息
	public String add(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, ParseException {
		examDao = new ExamDao();
		ClazzDao clazzDao = new ClazzDao();
		ExamSubjectDao examSubjectDao = new ExamSubjectDao();
		Integer eid = 0;
		User user = (User) request.getSession().getAttribute(
				Config.LOGIN_SESSION);
		Integer uid = user.getId();// 从session得到
		List<Clazz> clazzList = clazzDao.findAllByUid(uid);
		Clazz clazz = null;
		if (clazzList != null && clazzList.size() > 0) {
			clazz = clazzList.get(0);
		}
		Integer gid = clazz.getGid();
		
		Integer eidInit = Integer.parseInt(request.getParameter("eid"));
		//第一次提交
		if(eidInit==0){
			// 新增数据到表exam，并得到新增数据的id，即eid
			// Exam exam = (Exam)request.getAttribute("form");
			Exam exam = new Exam();
			// 得到examTime，并转化为Date
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String examTime = request.getParameter("examTime");
			Date date = null;
			date = sdf.parse(examTime);
			// 把值set入exam
			exam.setName(request.getParameter("name"));
			exam.setExamTime(date);
			exam.setUid(uid);
			exam.setGid(gid);
			eid = examDao.add(exam);			
		}else if(eidInit!=0){//第二次提交
			eid = eidInit;
			//修改exam表
			Exam exam = examDao.findById(eid);
			// 得到examTime，并转化为Date
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String examTime = request.getParameter("examTime");
			Date date = null;
			date = sdf.parse(examTime);
			// 把值set入exam
			exam.setName(request.getParameter("name"));
			exam.setExamTime(date);
			examDao.update(exam);
			//删除第一步插入exam_subject表里的数据
			examSubjectDao.delete(eid);
		}

		// 考试与科目的关联，插入表exam_subjects
		ExamSubject exsu = new ExamSubject();
		String subject = request.getParameter("subjects");
		String[] subjects = subject.split(",");
		for (Integer i = 0; i < subjects.length - 1; i++) {
			String[] subsplit = subjects[i].split("_");
			exsu.setEid(eid);
			exsu.setSubjects(Integer.parseInt(subsplit[0]));
			exsu.setTotal(Integer.parseInt(subsplit[1]));
			ExamSubjectDao esd = new ExamSubjectDao();
			esd.addGradeSubjects(exsu);
		}
		return "exam/exam_addInit-" + eid;
	}

	/**
	 * 登录用户所在年级的考试列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws ServletException
	 * @throws ParseException
	 */
	public String list(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, ParseException {
		// 从session得到uid
		examDao = new ExamDao();
		SubjectDao subjectDao = new SubjectDao();
		User user = (User) request.getSession().getAttribute(
				Config.LOGIN_SESSION);
		Integer uid = user.getId();
		Integer logo = 0;
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
		maxCount = examDao.getAllCount(uid);
		List<Exam> examList = new ArrayList<Exam>();
		if (maxCount > 0) {
			examList = examDao.getExamList(page, count, uid);
		}
		maxPage = (maxCount / count) + (maxCount % count == 0 ? 0 : 1);
		// List<Exam> examList = examDao.list(uid);
		Iterator it = examList.iterator();
		while (it.hasNext()) {
			Exam exam = (Exam) it.next();
			Integer eid = exam.getId();
			List<Subjects> subjectsList = subjectDao.findByEid(eid);
			exam.setSubjectsList(subjectsList);
		}
		request.setAttribute("examList", examList);
		request.setAttribute("maxPage", maxPage);
		request.setAttribute("page", page);
		request.setAttribute("logo", logo);
		return "exam/examManager";
	}

	/**
	 * 页面左边，成绩录入之后的考试列表，成绩未录入的考试
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws ServletException
	 * @throws ParseException
	 */
	public String listOther(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException,
			ParseException {
		// 从session得到uid
		examDao = new ExamDao();
		SubjectDao subjectDao = new SubjectDao();
		User user = (User) request.getSession().getAttribute(
				Config.LOGIN_SESSION);
		Integer uid = user.getId();
		Integer cid = user.getCid();
		Integer logo = 1;
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
		maxCount = examDao.getCount(uid, cid);
		List<Exam> examList = new ArrayList<Exam>();
		if (maxCount > 0) {
			examList = examDao.getExamListOther(page, count, uid, cid);
		}
		maxPage = (maxCount / count) + (maxCount % count == 0 ? 0 : 1);
		// List<Exam> examList = examDao.list(uid);
		Iterator it = examList.iterator();
		while (it.hasNext()) {
			Exam exam = (Exam) it.next();
			Integer eid = exam.getId();
			List<Subjects> subjectsList = subjectDao.findByEid(eid);
			exam.setSubjectsList(subjectsList);
		}
		request.setAttribute("examList", examList);
		request.setAttribute("maxPage", maxPage);
		request.setAttribute("page", page);
		request.setAttribute("logo", logo);
		return "exam/examManager";
	}
}
