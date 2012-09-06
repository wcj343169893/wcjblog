package com.snssly.sms.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.log4j.Logger;

import com.snssly.sms.commons.DBHelper;
import com.snssly.sms.entity.Exam;

public class ExamDao extends DBHelper {
	private Logger logger = Logger.getLogger(this.getClass());

	public List<Exam> list(Integer uid) {
		this.sql = "SELECT e.* FROM exam e LEFT JOIN clazz c ON c.gid =e.gid LEFT JOIN `user` u ON c.id=u.cid WHERE u.id=?";
		logger.info(this.sql);
		return super.query(new BeanListHandler<Exam>(Exam.class), uid);
	}

	 //修改考试信息
	 public void update(Exam exam){
	 this.sql = "UPDATE `sms_db`.`exam` SET `name`=?,`examTime`=?,`createTime`= now() WHERE `id`=?";
	 super.update(new Object[]{exam.getName(),exam.getExamTime(),exam.getId()});
	 }
	/**
	 * 新增考试信息 ，添加数据到exam表，第一步
	 * 
	 * @param exam
	 */
	public Integer add(Exam exam) {
		// public void add(Exam exam) {
		this.sql = "INSERT INTO `exam`(`name`,`examTime`,`uid`,`gid`,`createTime`) VALUES (?,?,?,?,now());";
		super.update(new Object[] { exam.getName(), exam.getExamTime(),
				exam.getUid(), exam.getGid() });
		return super.getLastInsertId();
	}

	/**
	 * 显示数据的总条数
	 * 
	 * @return
	 */
	public Integer getAllCount(Integer uid) {
		this.sql = "SELECT count(*) FROM exam e LEFT JOIN clazz c ON c.gid =e.gid LEFT JOIN `user` u ON c.id=u.cid WHERE u.id=?";
		logger.info(this.sql);
		return super.count(uid);
	}

	/**
	 * 考试列表，完整的
	 * 
	 * @param page
	 * @param count
	 * @param uid
	 * @return
	 */
	public List<Exam> getExamList(Integer page, Integer count, Integer uid) {
		this.sql = "SELECT e.* FROM exam e LEFT JOIN clazz c ON c.gid =e.gid LEFT JOIN `user` u ON c.id=u.cid WHERE u.id=? order by id desc limit "
				+ (page * count) + "," + count;
		logger.info(this.sql);
		return super.query(new BeanListHandler<Exam>(Exam.class), uid);
	}

	/**
	 * 考试列表，成绩未录入的
	 * 
	 * @param page
	 * @param count
	 * @param uid
	 * @return
	 */
	public List<Exam> getExamListOther(Integer page, Integer count,
			Integer uid, Integer cid) {
		this.sql = "SELECT e.* FROM exam e LEFT JOIN clazz c ON c.gid =e.gid LEFT JOIN `user` u ON c.id=u.cid WHERE u.id=? AND e.id NOT IN (SELECT eid FROM exam_subject es,exam_scores ess WHERE es.id=ess.examSubId AND ess.uid IN (SELECT id  FROM `user` WHERE cid=? AND rid=3 AND isVisible=0) GROUP BY eid) ORDER BY id DESC limit "
				+ (page * count) + "," + count;
		logger.info(this.sql);
		return super.query(new BeanListHandler<Exam>(Exam.class), uid, cid);
	}

	/**
	 * 显示数据的总条数(成绩未录入的)
	 * 
	 * @return
	 */
	public Integer getCount(Integer uid, Integer cid) {
		this.sql = "SELECT count(*) FROM exam e LEFT JOIN clazz c ON c.gid =e.gid LEFT JOIN `user` u ON c.id=u.cid WHERE u.id=? AND e.id NOT IN (SELECT eid FROM exam_subject es,exam_scores ess WHERE es.id=ess.examSubId AND ess.uid IN (SELECT id  FROM `user` WHERE cid=? AND rid=3 AND isVisible=0) GROUP BY eid)";
		logger.info(this.sql);
		return super.count(uid, cid);
	}

	/**
	 * 成绩分析，按总分 ，得到eid
	 * 
	 * @param start
	 * @param end
	 * @param cid
	 * @return
	 */
	public List<Exam> getExamByTime(String start, String end, Integer cid) {
		ArrayList<Object> obj = new ArrayList<Object>();
		obj.add(cid);
		this.sql = "SELECT e.id,e.`name`,e.examTime FROM exam e LEFT JOIN clazz c ON c.gid=e.gid  WHERE c.id=? ORDER BY examTime ";
		if (start != null && !"".equals(start.trim())) {
			this.sql += " AND examTime>=?";
			obj.add(start);
		}
		if (end != null && !"".equals(end.trim())) {
			this.sql += " AND examTime<=?  ";
			obj.add(end);
		}
		logger.info(this.sql);
		return super
				.query(new BeanListHandler<Exam>(Exam.class), obj.toArray());
	}

	/**
	 * 成绩分析，按各科，得到eid
	 * 
	 * @param sid
	 * @param start
	 * @param end
	 * @param cid
	 * @return
	 */
	public List<Exam> getExamBySid(Integer sid, String start, String end,
			Integer cid) {
		ArrayList<Object> obj = new ArrayList<Object>();
		obj.add(sid);
		obj.add(cid);
		this.sql = "SELECT e.* FROM exam e LEFT JOIN clazz c ON c.gid=e.gid LEFT JOIN exam_subject es ON es.eid=e.id LEFT JOIN subjects s ON s.id=es.subjects "
				+ "WHERE s.id=? AND c.id=?";
		if (start != null && !"".equals(start.trim())) {
			this.sql += " AND examTime>=?";
			obj.add(start);
		}
		if (end != null && !"".equals(end.trim())) {
			this.sql += " AND examTime<=?  ";
			obj.add(end);
		}
		this.sql += " ORDER BY e.examTime ";
		logger.info(this.sql);
		return super
				.query(new BeanListHandler<Exam>(Exam.class), obj.toArray());
	}

	/**
	 * 根据班级编号查询考试
	 * 
	 * @param cid
	 *            班级编号
	 * @return
	 */
	public List<Exam> findByCid(Integer cid) {
		this.sql = "SELECT e.* FROM exam e LEFT JOIN grade g ON e.gid=g.id	LEFT JOIN clazz c ON g.id=c.gid	WHERE c.id=?";
		logger.info(this.sql);
		return super.query(new BeanListHandler<Exam>(Exam.class), cid);
	}

	/**
	 * 得到某一时间段内的考试，根据年级id gid，
	 * 
	 * @param gid
	 * @param start
	 * @param end
	 * @return
	 */
	public List<Exam> getExamByGid(Integer gid, String start, String end) {
		ArrayList<Object> obj = new ArrayList<Object>();
		obj.add(gid);
		this.sql = "SELECT id,`name`,examTime FROM exam WHERE gid=?";
		if (start != null && !"".equals(start.trim())) {
			this.sql += " AND examTime>=?";
			obj.add(start);
		}
		if (end != null && !"".equals(end.trim())) {
			this.sql += " AND examTime<=?  ";
			obj.add(end);
		}
		this.sql += " ORDER BY examTime ";
		logger.info(this.sql);
		return super
				.query(new BeanListHandler<Exam>(Exam.class), obj.toArray());
	}

	/**
	 * 根据编号查询考试
	 * 
	 * @param id
	 * @return
	 */
	public Exam findById(Integer id) {
		this.sql = "SELECT * FROM exam WHERE id=?";
		logger.info(this.sql);
		return super.query(new BeanHandler<Exam>(Exam.class), id);
	}
}
