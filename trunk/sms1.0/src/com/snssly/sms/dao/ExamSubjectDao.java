package com.snssly.sms.dao;

import java.util.List;

import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.log4j.Logger;

import com.snssly.sms.commons.DBHelper;
import com.snssly.sms.entity.Exam;
import com.snssly.sms.entity.ExamSubject;

public class ExamSubjectDao extends DBHelper {
	private Logger logger = Logger.getLogger(this.getClass());
	/**
	 * 新增考试信息 ，添加数据到exam_subjects表，第二步
	 * 
	 * @param exsu
	 */
	public void addGradeSubjects(ExamSubject exsu) {
		this.sql = "INSERT INTO `exam_subject`(`eid`,`subjects`,`total`,`createTime`) VALUES ( ?,?,?,now());";
		super.update(new Object[] { exsu.getEid(), exsu.getSubjects(),
				exsu.getTotal() });
	}

	/**
	 * 根据考试编号，科目编号查询考试科目中间表
	 * 
	 * @param eid
	 *            考试编号
	 * @param sid
	 *            科目编号
	 * @return
	 */
	public ExamSubject findByEidSid(Integer eid, Integer sid) {
		this.sql = "select * from exam_subject where eid=? and subjects=?";
		return super.query(new BeanHandler<ExamSubject>(ExamSubject.class),
				eid, sid);
	}
	
	/**
	 * 根据考试编号，删除考试-科目中间表的数据
	 * @param eid
	 */
	public void delete(Integer eid) {	
		this.sql = "DELETE FROM exam_subject WHERE eid = ?";
		super.update(new Object[]{eid});
	}
}
