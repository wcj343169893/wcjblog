package com.snssly.sms.admin;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import com.snssly.sms.commons.Config;
import com.snssly.sms.commons.FileManager;
import com.snssly.sms.dao.ClazzDao;
import com.snssly.sms.dao.ExamDao;
import com.snssly.sms.dao.ExamRowNumDao;
import com.snssly.sms.dao.ExamScoresDAO;
import com.snssly.sms.dao.ExamSubjectDao;
import com.snssly.sms.dao.GradeDao;
import com.snssly.sms.dao.SubjectDao;
import com.snssly.sms.dao.UserDao;
import com.snssly.sms.dao.UserScoresDAO;
import com.snssly.sms.entity.Clazz;
import com.snssly.sms.entity.Exam;
import com.snssly.sms.entity.ExamRowNum;
import com.snssly.sms.entity.ExamScores;
import com.snssly.sms.entity.ExamSubject;
import com.snssly.sms.entity.Grade;
import com.snssly.sms.entity.Subjects;
import com.snssly.sms.entity.User;
import com.snssly.sms.entity.UserScores;

/**
 * --成绩录入管理
 * 
 * @author Administrator
 * 
 */
public class ExamScoresAction {
	ExamScoresDAO dao = null;

	/**
	 * 成绩录入显示列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws ServletException
	 */
	public String list(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		dao = new ExamScoresDAO();
		SubjectDao subjectDao = new SubjectDao();
		UserDao userDao = new UserDao();
		ExamDao examDao = new ExamDao();
		Integer eid = 0;
		// 得到eid，考试id
		String[] p = (String[]) request.getAttribute("params");
		if (p != null && p[0].matches("\\d+")) {// 判断是否是数字
			eid = Integer.valueOf(p[0]);
			//考试的信息
			Exam exam = examDao.findById(eid);
			// 从session中得到
			User user = (User) request.getSession().getAttribute(
					Config.LOGIN_SESSION);
			// Integer uid = user.getId();// 用户，即登陆者id
			Integer cid = user.getCid();// 用户的班级id，可得到年级
			// 完善显示表的列名，即科目名称
			List<Subjects> subjectList = subjectDao.findByEid(eid);
			// 学生列表
			List<User> userList = userDao.getUser(cid);
			// 生成List<ExamScores> esList
			Iterator it = userList.iterator();
			while (it.hasNext()) {
				User auser = (User) it.next();
				Integer id = auser.getId();
				List<ExamScores> esList = dao.getES(id, eid);
				auser.setEsList(esList);
			}
			request.setAttribute("exam", exam);
			request.setAttribute("subjectList", subjectList);
			request.setAttribute("userList", userList);
			request.setAttribute("eid", eid);
			return "exam/scoresManager";
		} else {
			return "exam/exam_listOther.html";
		}

	}

	// 提交学生的考试成绩，先全部删除，后插入
	public String update(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException,
			ParseException {
		// 插入数据库
		String examScores = request.getParameter("examScores");
		String[] examScore = examScores.split(",");
		String str = "";
		List scoresList = new ArrayList();
		Integer l = examScore.length;
		for (Integer i = 0; i < l; i++) {
			String[] aexamScore = examScore[i].split("_");
			// System.out.println(aexamScore[0]);
			// System.out.println(aexamScore[1]);
			// System.out.println(aexamScore[2]);
			scoresList.add(aexamScore[0]);
			scoresList.add(aexamScore[2]);
			scoresList.add(null);
			scoresList.add(aexamScore[1]);
			str += "(?,?,?,?),";
		}
		str = str.substring(0, str.length() - 1);
		Object[] params = new Object[scoresList.size()];
		for (int i = 0; i < scoresList.size(); i++) {
			params[i] = scoresList.get(i);
		}
		dao = new ExamScoresDAO();
		// 删除历史数据
		User login_user = (User) request.getSession().getAttribute(
				Config.LOGIN_SESSION);
		Integer cid = login_user.getCid();// 得到班级编号
		Integer eid = Integer
				.parseInt(request.getParameter("eid") != null ? request
						.getParameter("eid").trim() : "0");
		dao.delete(eid, cid);
		// 新增新数据
		dao.add(str, params);
		return "exam/scores_list-" + eid;
	}

	// 删除此次考试全部成绩
	public String delete(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException,
			ParseException {
		dao = new ExamScoresDAO();
		User login_user = (User) request.getSession().getAttribute(
				Config.LOGIN_SESSION);
		Integer cid = login_user.getCid();// 得到班级编号
		Integer eid = 0;
		Integer page = 0;
		// 得到eid，考试id,get传参
		String[] p = (String[]) request.getAttribute("params");
		if (p != null && p[0].matches("\\d+")) {// 判断是否是数字
			eid = Integer.valueOf(p[0]);
		}
		if (p != null && p[1].matches("\\d+")) {// 判断是否是数字
			page = Integer.valueOf(p[1]);
		}
		dao.delete(eid, cid);
		return "exam/exam_list-" + page + ".html";
	}

	// 成绩统计的初始化页面
	public String statistics(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException,
			ParseException {
		dao = new ExamScoresDAO();
		ExamDao examDao = new ExamDao();
		Integer logo = 0;
		String mark = "";
		User login_user = (User) request.getSession().getAttribute(
				Config.LOGIN_SESSION);
		Integer uid = login_user.getId();
		Integer rid = login_user.getRid();

		String[] p = (String[]) request.getAttribute("params");
		if (p != null && p[0].matches("\\d+")) {// 判断是否是数字
			logo = Integer.valueOf(p[0]);
		}

		List<Exam> examList = examDao.list(uid);
		request.setAttribute("examList", examList);

		if (p == null || logo == 0) {
			mark = "exam/subjectStatistics";// 科目分段统计
		} else if (logo == 1) {
			mark = "exam/totalStatistics";// 总分分段统计
		}
		return mark;
	}

	// 科目分段统计
	public String subject(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException,
			ParseException {
		dao = new ExamScoresDAO();
		User login_user = (User) request.getSession().getAttribute(
				Config.LOGIN_SESSION);
		Integer cid = login_user.getCid();// 班级id
		Integer eid = Integer
				.parseInt(request.getParameter("eid") != null ? request
						.getParameter("eid") : "0");
		if (eid != null && !"".equals(eid)) {
			List<ExamSubject> esList = dao.getESid(eid);
			response.setContentType("text/html;charset=utf-8");
			response.setCharacterEncoding("UTF-8");
			response.setHeader("Cache-Control", "no-cache");

			PrintWriter out = response.getWriter();
			StringBuilder sb = new StringBuilder();

			Iterator it = esList.iterator();
			while (it.hasNext()) {
				ExamSubject es = (ExamSubject) it.next();
				Integer esid = es.getId();
				String sname = es.getSname();
				Integer total = es.getTotal();

				List<ExamScores> essList = dao.getAvgCount(esid, cid, total);

				ExamScores examScores = new ExamScores();
				examScores = essList.get(0);

				double avg = examScores.getAvg() != null ? examScores.getAvg()
						: 0;
				Integer perfect = examScores.getPerfect() != null ? examScores
						.getPerfect() : 0;
				Integer good = examScores.getGood() != null ? examScores
						.getGood() : 0;
				Integer inter = examScores.getInter() != null ? examScores
						.getInter() : 0;
				Integer bad = examScores.getBad() != null ? examScores.getBad()
						: 0;

				sb.append(sname + "_" + avg + "_" + perfect + "_" + good + "_"
						+ inter + "_" + bad + "_");
			}
			out.print(sb.toString());
			out.flush();
			out.close();
		}
		return null;
	}

	// 总分分段统计
	public String total(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, ParseException {
		dao = new ExamScoresDAO();
		User login_user = (User) request.getSession().getAttribute(
				Config.LOGIN_SESSION);
		Integer cid = login_user.getCid();// 班级id，cid
		Integer eid = Integer.parseInt(request.getParameter("eid") != null
				&& !"".equals(request.getParameter("eid").toString()) ? request
				.getParameter("eid") : "0");// 考试id，eid
		Integer start = Integer
				.parseInt(request.getParameter("start") != null
						&& !"".equals(request.getParameter("start").toString()) ? request
						.getParameter("start")
						: "0");// 分段开始
		Integer end = Integer.parseInt(request.getParameter("end") != null
				&& !"".equals(request.getParameter("end").toString()) ? request
				.getParameter("end") : "0");// 分段结束
		Integer inter = request.getParameter("inter") != null
				&& !"".equals(request.getParameter("inter").toString()) ? Integer
				.parseInt(request.getParameter("inter"))
				: (end - start);// 分段间隔
		if (eid != null && !"".equals(eid) && start != null
				&& !"".equals(start) && end != null && !"".equals(end)) {
			response.setContentType("text/html;charset=utf-8");
			response.setCharacterEncoding("UTF-8");
			response.setHeader("Cache-Control", "no-cache");

			PrintWriter out = response.getWriter();
			StringBuilder sb = new StringBuilder();

			Integer numR = (end - start) % inter;// 余数
			Integer num = (end - start) / inter;
			if (numR == 0) { // 结束宇开始的差能被间隔整除
				for (Integer i = 0; i < num; i++) {
					Integer starts = start + inter * i;
					Integer ends = start + inter * (i + 1);
					List<ExamScores> list = dao.getSum(cid, eid, ends, starts);
					Integer count = list.get(0).getCount();
					sb.append(starts + "-" + ends + "_" + count + "_");
				}
			}
			if (numR != 0) { // 结束宇开始的差不能被间隔整除
				for (Integer i = 0; i < num; i++) {
					Integer starts = start + inter * i;
					Integer ends = start + inter * (i + 1);
					List<ExamScores> list = dao.getSum(cid, eid, ends, starts);
					Integer count = list.get(0).getCount();
					sb.append(starts + "-" + ends + "_" + count + "_");
				}
				Integer starts = start + inter * num;
				List<ExamScores> list = dao.getSum(cid, eid, end, starts);
				Integer count = list.get(0).getCount();
				sb.append(starts + "-" + end + "_" + count + "_");
			}
			out.print(sb.toString());
			out.flush();
			out.close();
		}
		return null;
	}

	// 成绩分析的初始化页面
	public String analysis(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException,
			ParseException {
		UserDao userDao = new UserDao();
		GradeDao gradeDao = new GradeDao();
		ClazzDao clazzDao = new ClazzDao();
		Integer logo = 0;
		String mark = "";
		String[] p = (String[]) request.getAttribute("params");
		if (p != null && p[0].matches("\\d+")) {// 判断是否是数字
			logo = Integer.valueOf(p[0]);
		}
		User user = new User();
		List<User> userList = new ArrayList();
		User login_user = (User) request.getSession().getAttribute(
				Config.LOGIN_SESSION);
		Integer uid = login_user.getId();
		Integer rid = login_user.getRid();// 角色id，判断是家长还是老师
		Integer cid = login_user.getCid();
		// 根据p和logo为条件判断显示的页面
		if (p == null) { // p为空，不同角色显示的页面
			if (rid == 5 || rid == 1) {
				mark = "exam/leadAnalysis";// 领导看到的页面
			} else if (rid != 5) {
				mark = "exam/totalAnalysis";// 总分成绩分析
			}
		} else if (p != null) { // p不为空，不同logo显示的页面
			if (logo == 0) {
				mark = "exam/totalAnalysis";// 总分成绩分析
			} else if (logo == 1) {
				SubjectDao subjectDao = new SubjectDao();
				List<Subjects> subjectList = subjectDao.getByCid(cid);
				request.setAttribute("subjectList", subjectList);
				mark = "exam/subjectAnalysis";// 科目成绩分析
			} else if (logo == 2) {
				mark = "exam/leadAnalysis";// 领导,管理员看到的页面
			}
		}

		// 根据角色不同，不同页面的显示内容
		if (rid == 2) {// 角色是老师
			userList = userDao.getUser(cid);
			Grade grade = gradeDao.findGradeByCid(cid);
			Clazz clazz = clazzDao.findClazzById(cid);
			request.setAttribute("grade", grade);
			request.setAttribute("clazz", clazz);
		} else if (rid == 4) {// 角色是家长,只能看到自己的孩子,多个子女
			userList = userDao.getUserByPuid(uid);
		} else if (rid == 3) {// 角色是学生，只能看到自己的
			user = userDao.findUserById(uid);
			userList.add(user);
		} else if (rid == 5 || rid == 1) {
			List<Grade> gradeList = gradeDao.findAll();
			request.setAttribute("gradeList", gradeList);
		}

		request.setAttribute("userList", userList);
		request.setAttribute("user", user);
		request.setAttribute("rid", rid);
		return mark;
	}

	// 学生科目分析
	public String subjectAnalysis(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException,
			ParseException {
		dao = new ExamScoresDAO();
		ClazzDao clazzDao = new ClazzDao();
		User login_user = (User) request.getSession().getAttribute(
				Config.LOGIN_SESSION);
		Integer rid = login_user.getRid();// 角色id
		Integer cid = 0;

		Integer uid = Integer.parseInt(request.getParameter("uid") != null
				&& !"".equals(request.getParameter("uid").toString()) ? request
				.getParameter("uid") : "0");// 学生id，uid
		//根据学生id，uid，得到cid
		Clazz clazz = clazzDao.findClazzByUidOnly(uid);
		cid = clazz.getId();
		Integer sid = Integer.parseInt(request.getParameter("sid") != null
				&& !"".equals(request.getParameter("sid").toString()) ? request
				.getParameter("sid") : "0");// 登陆者所在年级的学科id，sid
		Integer way = Integer.parseInt(request.getParameter("way"));// 统计方式，0
		// 按成绩 1 按名次
		String start = request.getParameter("start");// 起始时间
		String end = request.getParameter("end");// 结束时间
		if (start.compareTo(end) > 0) {
			String temp = end;
			end = start;
			start = temp;
		}
		if (uid != null && !"".equals(uid)) {
			response.setContentType("text/html;charset=utf-8");
			response.setCharacterEncoding("UTF-8");
			response.setHeader("Cache-Control", "no-cache");

			PrintWriter out = response.getWriter();
			StringBuilder sb = new StringBuilder();

			ExamDao examDao = new ExamDao();
			List<Exam> examList = examDao.getExamBySid(sid, start, end, cid);
			if (way == 0) {// 按成绩
				for (Exam exam : examList) {
					Integer eid = exam.getId();
					List<ExamScores> es = dao.getSubjectByUid(uid, eid, sid);
					if (es.isEmpty()) {
						sb.append(exam.getName() + "_" + exam.getExamTime()
								+ "_0_");
					} else {
						ExamScores essum = es.get(0);
						sb.append(exam.getName() + "_" + exam.getExamTime()
								+ "_" + essum.getSum() + "_");
					}
				}
			} else if (way == 1) {// 按名次
				Iterator it = examList.iterator();
				while (it.hasNext()) {
					Exam exam = (Exam) it.next();
					Integer eid = exam.getId();
					List<ExamScores> es = dao.getRankBySubject(cid, eid, sid,
							uid);
					if (es.isEmpty()) {
						sb.append(exam.getName() + "_" + exam.getExamTime()
								+ "_0_");
					} else {
						ExamScores essum = es.get(0);
						sb.append(exam.getName() + "_" + exam.getExamTime()
								+ "_" + essum.getRank() + "_");
					}
				}
			}
			out.print(sb.toString());
			out.flush();
			out.close();
		}
		return null;
	}

	// 学生总分分析
	public String totalAnalysis(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException,
			ParseException {
		dao = new ExamScoresDAO();
		ClazzDao clazzDao = new ClazzDao();
		User login_user = (User) request.getSession().getAttribute(
				Config.LOGIN_SESSION);
		Integer rid = login_user.getRid();// 角色id
		Integer cid = 0;

		Integer uid = Integer.parseInt(request.getParameter("uid") != null
				&& !"".equals(request.getParameter("uid").toString()) ? request
				.getParameter("uid") : "0");// 学生id，uid
		//根据学生id，uid，得到cid
		Clazz clazz = clazzDao.findClazzByUidOnly(uid);
		cid = clazz.getId();
		Integer way = Integer.parseInt(request.getParameter("way"));// 统计方式，0
		// 按成绩 1 按名次
		String start = request.getParameter("start");// 起始时间
		String end = request.getParameter("end");// 结束时间
		if (start.compareTo(end) > 0) {
			String temp = end;
			end = start;
			start = temp;
		}
		if (uid != null && !"".equals(uid)) {
			response.setContentType("text/html;charset=utf-8");
			response.setCharacterEncoding("UTF-8");
			response.setHeader("Cache-Control", "no-cache");

			PrintWriter out = response.getWriter();
			StringBuilder sb = new StringBuilder();

			ExamDao examDao = new ExamDao();
			List<Exam> examList = examDao.getExamByTime(start, end, cid);
			if (way == 0) {// 按成绩
				Iterator it = examList.iterator();
				while (it.hasNext()) {
					Exam exam = (Exam) it.next();
					Integer eid = exam.getId();
					List<ExamScores> es = dao.getSumByUid(cid, eid, uid);
					if (es.isEmpty()) {
						sb.append(exam.getName() + "_" + exam.getExamTime()
								+ "_0_");
					} else {
						ExamScores essum = es.get(0);
						sb.append(exam.getName() + "_" + exam.getExamTime()
								+ "_" + essum.getSum() + "_");
					}
				}
			} else if (way == 1) {// 按名次
				Iterator it = examList.iterator();
				while (it.hasNext()) {
					Exam exam = (Exam) it.next();
					Integer eid = exam.getId();
					List<ExamScores> es = dao.getRankByUid(cid, eid, uid);
					if (es.isEmpty()) {
						sb.append(exam.getName() + "_" + exam.getExamTime()
								+ "_0_");
					} else {
						ExamScores essum = es.get(0);
						sb.append(exam.getName() + "_" + exam.getExamTime()
								+ "_" + essum.getRank() + "_");
					}
				}
			}
			out.print(sb.toString());
			out.flush();
			out.close();
		}
		return null;
	}

	// 领导的成绩分析
	public String leadAnalysis(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException,
			ParseException {
		dao = new ExamScoresDAO();
		ClazzDao clazzDao = new ClazzDao();
		ExamDao examDao = new ExamDao();
		ExamRowNumDao examRowNumDao = new ExamRowNumDao();

		User login_user = (User) request.getSession().getAttribute(
				Config.LOGIN_SESSION);
		Integer uid = login_user.getId();
		Integer gid = Integer.parseInt(request.getParameter("gid") != null
				&& !"".equals(request.getParameter("gid").toString()) ? request
				.getParameter("gid") : "0");
		String start = request.getParameter("start");// 起始时间
		String end = request.getParameter("end");// 结束时间
		if (start.compareTo(end) > 0) {
			String temp = end;
			end = start;
			start = temp;
		}
		// 该年级在一个时间段的所有考试
		List<Exam> examList = examDao.getExamByGid(gid, start, end);
		// 所选年级的所有班级
		List<Clazz> clazzList = clazzDao.findClazzByGid(gid);

		if (uid != null && !"".equals(uid)) {
			response.setContentType("text/html;charset=utf-8");
			response.setCharacterEncoding("UTF-8");
			response.setHeader("Cache-Control", "no-cache");

			PrintWriter out = response.getWriter();
			StringBuilder sb = new StringBuilder();

			// 拼凑字符串
			for (Clazz clazz : clazzList) {
				sb.append(clazz.getId() + "_" + clazz.getName() + "_");
			}
			sb.append("+");

			for (Exam exam : examList) {
				sb.append(exam.getName() + "_" + exam.getExamTime() + "_");
				Integer eid = exam.getId();
				List<ExamRowNum> examRowNumList = examRowNumDao.findAll(eid,
						gid);
				for (Clazz clazz : clazzList) {
					Integer i = 0;
					Integer cid = clazz.getId();
					Integer length = examRowNumList.size() + 1;
					System.out.println(length);
					for (ExamRowNum examRowNum : examRowNumList) {
						Integer cidOther = examRowNum.getCid();
						i++;
						if (cid == cidOther) {
							length = i;
						}
					}
					sb.append(length + "_");
				}
				sb.append(",");
			}
			System.out.println(sb);

			out.print(sb.toString());
			out.flush();
			out.close();
		}
		return null;
	}

	/**
	 * 根据年级，联动班级
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws ServletException
	 */
	public String listByGid(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		ClazzDao clazzDao = new ClazzDao();
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

	/**
	 * 根据班级得到所有学生
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws ServletException
	 */
	public String listByCid(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		UserDao userDao = new UserDao();
		Integer cid = Integer
				.parseInt(request.getParameter("cid") != null ? request
						.getParameter("cid") : "0");
		if (cid != null && !"".equals(cid)) {
			List<User> userList = userDao.getUser(cid);
			response.setContentType("text/html;charset=utf-8");
			response.setCharacterEncoding("UTF-8");
			response.setHeader("Cache-Control", "no-cache");

			PrintWriter out = response.getWriter();
			StringBuilder sb = new StringBuilder();
			int size = userList.size();
			int count = 1;
			for (User user : userList) {
				sb.append(user.getId() + ":" + user.getNikeName());
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
	 * 根据年级得到所有科目，联动
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws ServletException
	 */
	public String listSubjectByGrade(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		SubjectDao subjectDao = new SubjectDao();
		Integer gid = Integer
				.parseInt(request.getParameter("gid") != null ? request
						.getParameter("gid") : "0");
		if (gid != null && !"".equals(gid)) {
			List<Subjects> subjectList = subjectDao.getByGid(gid);
			if (subjectList.size() > 0) {
				response.setContentType("text/html;charset=utf-8");
				response.setCharacterEncoding("UTF-8");
				response.setHeader("Cache-Control", "no-cache");

				PrintWriter out = response.getWriter();
				StringBuilder sb = new StringBuilder();
				int size = subjectList.size();
				int count = 1;
				for (Subjects subjects : subjectList) {
					sb.append(subjects.getId() + ":" + subjects.getName());
					if (count < size) {
						count++;
						sb.append(",");
					}
				}
				out.print(sb.toString());
				out.flush();
				out.close();
			}
		}
		return null;
	}

	/**
	 * 导出成绩弹出框的科目
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws ServletException
	 */
	public String listSubjectExcel(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		SubjectDao subjectDao = new SubjectDao();
		Integer eid = Integer
				.parseInt(request.getParameter("eid") != null ? request
						.getParameter("eid") : "0");
		if (eid != null && !"".equals(eid)) {
			List<Subjects> subjectList = subjectDao.findByEid(eid);
			if (subjectList.size() > 0) {
				response.setContentType("text/html;charset=utf-8");
				response.setCharacterEncoding("UTF-8");
				response.setHeader("Cache-Control", "no-cache");

				PrintWriter out = response.getWriter();
				StringBuilder sb = new StringBuilder();

				for (Subjects subjects : subjectList) {
					sb
							.append(subjects.getId() + ":" + subjects.getName()
									+ ",");
				}

				out.print(sb.toString());
				out.flush();
				out.close();
			}
		}
		return null;
	}

	/**
	 * 导出成绩Excel
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws ServletException
	 */
	public String makeExcel(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		// 用到的Dao
		SubjectDao subjectDao = new SubjectDao();
		GradeDao gradeDao = new GradeDao();
		ClazzDao clazzDao = new ClazzDao();
		ExamDao examDao = new ExamDao();
		ExamSubjectDao examSubjectDao = new ExamSubjectDao();
		UserScoresDAO userScoresDao = new UserScoresDAO();

		Integer eid = 0;
		Integer way = 0;
		Integer subjectId = 0;
		String title = "";
		String str = "(";

		User login_user = (User) request.getSession().getAttribute(
				Config.LOGIN_SESSION);
		Integer cid = login_user.getCid();
		String[] p = (String[]) request.getAttribute("params");
		if (p != null && p[0].matches("\\d+")) {// 判断是否是数字
			eid = Integer.valueOf(p[0]);
		}
		if (p != null && p[1].matches("\\d+")) {
			way = Integer.valueOf(p[1]);
		}
		p[1] = cid + "";
		Integer len = p.length;
		Exam exam = examDao.findById(eid);
		Grade grade = gradeDao.findGradeByCid(cid);// 年级信息
		Clazz clazz = clazzDao.findClazzById(cid);// 班级信息
		// 根据不同的way选择不同的方法
		if (way == 0) {// 本班
			try {
				if (p != null && p.length >= 3) {
					// 文档名，表格表头
					title = grade.getName()+"_"+clazz.getName() + "_"+exam.getName() + "_";
					for (Integer i = 2; i < len; i++) {
						if (p != null && p[i].matches("\\d+")) {// 判断是否是数字
							subjectId = Integer.valueOf(p[i]);// 科目id
						}
						Subjects subject = subjectDao
								.findSubjectById(subjectId);
						title += subject.getName() + "-";
						str += "?,";
					}
					str = str.substring(0, str.length() - 1) + ")";
					title = title.substring(0, title.length() - 1) + "考试成绩";
					String path = request.getSession().getServletContext()
							.getRealPath("/");
					File file = new File(path + "exam/" + title + ".xls");
					// 生成空白Excel
					WritableWorkbook book = Workbook.createWorkbook(file);
					WritableSheet sheet = book.createSheet(exam.getName(), 0);

					//Excel格式
					sheet.mergeCells(0, 0, len, 0);// 合并单元格
					sheet.setColumnView(0,15); 
					sheet.setColumnView(1,15); 
					//字符串字体
					WritableFont font=new WritableFont(WritableFont.createFont("宋体"),12,WritableFont.NO_BOLD );
					WritableCellFormat format=new WritableCellFormat(font); 
					format.setAlignment(jxl.format.Alignment.CENTRE); 
					WritableCellFormat formatAlignCENTRE = new WritableCellFormat();
					formatAlignCENTRE.setAlignment(jxl.format.Alignment.CENTRE); 					
					WritableCellFormat formatAlignRight = new WritableCellFormat();
					formatAlignRight.setAlignment(jxl.format.Alignment.RIGHT); 					
					Label sheetTitle = new Label(0, 0, title,format);// 表格题目

					Integer i = 0;

					Map<Integer, Integer> subjectsMap = new HashMap<Integer, Integer>();
					// 列名
					sheet.addCell(sheetTitle);
					Label studentNum = new Label(0, 1, "学号",formatAlignCENTRE);
					sheet.addCell(studentNum);
					Label studentName = new Label(1, 1, "姓名",formatAlignCENTRE);
					sheet.addCell(studentName);
					for (i = 2; i < len; i++) {
						if (p != null && p[i].matches("\\d+")) {// 判断是否是数字
							subjectId = Integer.valueOf(p[i]);// 科目id
						}
						Subjects subject = subjectDao
								.findSubjectById(subjectId);
						Label clazzName = new Label(i, 1, subject.getName(),formatAlignCENTRE);
						sheet.addCell(clazzName);
						subjectsMap.put(subject.getId(), i);
					}
					Label clazzName = new Label(i, 1, "总分",formatAlignCENTRE);
					sheet.addCell(clazzName);

					List<UserScores> userScoresList = userScoresDao.getByCid(
							str, p);

					Integer row = 2;
					double sum = 0;
					Integer uid = 0;
					jxl.write.Number sumscores = null;
					for (UserScores userScores : userScoresList) {
						if (!userScores.getUid().equals(uid)) {
							if (sum != 0) {
								sumscores = new jxl.write.Number(subjectsMap
										.size() + 2, row, sum);
								sheet.addCell(sumscores);
								sum = 0;
								row++;
							}
							// 学号
							jxl.write.Number snumber = new jxl.write.Number(0,
									row, userScores.getSnumber());
							sheet.addCell(snumber);
							// 姓名
							Label nikeName = new Label(1, row, userScores
									.getNikeName(),formatAlignRight);
							sheet.addCell(nikeName);
						}
						// 各科分数
						jxl.write.Number scores = new jxl.write.Number(
								subjectsMap.get(userScores.getSid()), row,
								userScores.getScores());
						sheet.addCell(scores);

						sum += userScores.getScores();
						uid = userScores.getUid();// 上一个的uid
					}
					// 最后一个总分
					sumscores = new jxl.write.Number(subjectsMap.size() + 2,
							row, sum);
					sheet.addCell(sumscores);
					book.write();
					book.close();
					FileManager.download(request, response, file);// 下载文件
				}
			} catch (Exception e) {
				System.out.println(e);
			}
		} else if (way == 1) {// 本年级
			try {
				if (p != null && p.length >= 3) {
					// 文档名，表格表头
					title = grade.getName()+ "_"+exam.getName() + "_";
					for (Integer i = 2; i < len; i++) {
						if (p != null && p[i].matches("\\d+")) {// 判断是否是数字
							subjectId = Integer.valueOf(p[i]);// 科目id
						}
						Subjects subject = subjectDao
						.findSubjectById(subjectId);
						title += subject.getName() + "-";
						str += "?,";
					}
					str = str.substring(0, str.length() - 1) + ")";
					title = title.substring(0, title.length() - 1) + "考试成绩";
					String path = request.getSession().getServletContext()
					.getRealPath("/");
					File file = new File(path + "exam/" + title + ".xls");
					// 生成空白Excel
					WritableWorkbook book = Workbook.createWorkbook(file);
					WritableSheet sheet = book.createSheet(exam.getName(), 0);
					//Excel格式
					sheet.mergeCells(0, 0, len, 0);// 合并单元格
					sheet.setColumnView(0,15); 
					sheet.setColumnView(1,15); 
					//字符串字体
					WritableFont font=new WritableFont(WritableFont.createFont("宋体"),12,WritableFont.NO_BOLD );
					WritableCellFormat format=new WritableCellFormat(font); 
					format.setAlignment(jxl.format.Alignment.CENTRE); 
					WritableCellFormat formatAlignCENTRE = new WritableCellFormat();
					formatAlignCENTRE.setAlignment(jxl.format.Alignment.CENTRE); 					
					WritableCellFormat formatAlignRight = new WritableCellFormat();
					formatAlignRight.setAlignment(jxl.format.Alignment.RIGHT); 					
					Label sheetTitle = new Label(0, 0, title,format);// 表格题目
					
					Integer i = 0;
					
					Map<Integer, Integer> subjectsMap = new HashMap<Integer, Integer>();
					// 列名
					sheet.addCell(sheetTitle);
					Label studentNum = new Label(0, 1, "学号",formatAlignCENTRE);
					sheet.addCell(studentNum);
					Label studentName = new Label(1, 1, "姓名",formatAlignCENTRE);
					sheet.addCell(studentName);
					for (i = 2; i < len; i++) {
						if (p != null && p[i].matches("\\d+")) {// 判断是否是数字
							subjectId = Integer.valueOf(p[i]);// 科目id
						}
						Subjects subject = subjectDao
						.findSubjectById(subjectId);
						Label clazzName = new Label(i, 1, subject.getName(),formatAlignCENTRE);
						sheet.addCell(clazzName);
						subjectsMap.put(subject.getId(), i);
					}
					Label clazzName = new Label(i, 1, "总分",formatAlignCENTRE);
					sheet.addCell(clazzName);
					
					Integer gid = grade.getId();
					p[1] = gid+"";
					
					List<UserScores> userScoresList = userScoresDao.getByGid(
							str, p);
					
					Integer row = 2;
					double sum = 0;
					Integer uid = 0;
					jxl.write.Number sumscores = null;
					for (UserScores userScores : userScoresList) {
						if (!userScores.getUid().equals(uid)) {
							if (sum != 0) {
								sumscores = new jxl.write.Number(subjectsMap
										.size() + 2, row, sum);
								sheet.addCell(sumscores);
								sum = 0;
								row++;
							}
							// 学号
							jxl.write.Number snumber = new jxl.write.Number(0,
									row, userScores.getSnumber());
							sheet.addCell(snumber);
							// 姓名
							Label nikeName = new Label(1, row, userScores
									.getNikeName(),formatAlignRight);
							sheet.addCell(nikeName);
						}
						// 各科分数
						jxl.write.Number scores = new jxl.write.Number(
								subjectsMap.get(userScores.getSid()), row,
								userScores.getScores());
						sheet.addCell(scores);
						
						sum += userScores.getScores();
						uid = userScores.getUid();// 上一个的uid
					}
					// 最后一个总分
					sumscores = new jxl.write.Number(subjectsMap.size() + 2,
							row, sum);
					sheet.addCell(sumscores);
					book.write();
					book.close();
					FileManager.download(request, response, file);// 下载文件
				}
			} catch (Exception e) {
				System.out.println(e);
			}

		}

		return null;
	}
	/**
	 * 根据学生得到科目
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws ServletException
	 */
	public String listSubjectByUser(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		SubjectDao subjectDao = new SubjectDao();
		GradeDao gradeDao = new GradeDao();
		//根据uid得到gid
		Integer uid = Integer
		.parseInt(request.getParameter("uid") != null ? request
				.getParameter("uid") : "0");
		if (uid != 0) {			
			Grade grade = gradeDao.findGradeByUid(uid);
			Integer gid = grade.getId();
			if (gid != null && !"".equals(gid)) {
				List<Subjects> subjectList = subjectDao.getByGid(gid);
				if (subjectList.size() > 0) {
					response.setContentType("text/html;charset=utf-8");
					response.setCharacterEncoding("UTF-8");
					response.setHeader("Cache-Control", "no-cache");
					
					PrintWriter out = response.getWriter();
					StringBuilder sb = new StringBuilder();
					int size = subjectList.size();
					int count = 1;
					for (Subjects subjects : subjectList) {
						sb.append(subjects.getId() + ":" + subjects.getName());
						if (count < size) {
							count++;
							sb.append(",");
						}
					}
					out.print(sb.toString());
					out.flush();
					out.close();
				}
			}
		}
		return null;
	}
}
