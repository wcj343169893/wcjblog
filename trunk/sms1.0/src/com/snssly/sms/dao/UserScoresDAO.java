package com.snssly.sms.dao;

import java.util.List;

import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.log4j.Logger;

import com.snssly.sms.commons.DBHelper;

import com.snssly.sms.entity.UserScores;

/**
 * 学校信息管理的所有操作
 * 
 * @author Administrator
 * 
 */
public class UserScoresDAO extends DBHelper {
	private Logger logger = Logger.getLogger(this.getClass());

	/**
	 * 班级的一次考试的所有成绩
	 * 
	 * @return
	 */
	public List<UserScores> getByCid(String str,Object[] params) {
		this.sql = "SELECT u.id uid,u.nikeName,u.snumber,s.id sid, s.name subjectName,es.scores FROM `user` u "
			+ "LEFT JOIN exam_scores es ON es.uid=u.id "
			+ "LEFT JOIN exam_subject esub ON es.examSubId=esub.id "
			+ "LEFT JOIN subjects s ON s.id=esub.subjects "
			+ "WHERE esub.eid=? AND u.cid=? AND s.id IN "
			+ str
			+ " ORDER BY uid,sid";
		logger.info(this.sql);
		return super.query(new BeanListHandler<UserScores>(UserScores.class),
				params);
	}
	/**
	 * 年级的一次考试的所有成绩
	 * 
	 * @return
	 */
	public List<UserScores> getByGid(String str,Object[] params) {
		this.sql = "SELECT u.id uid,u.nikeName,u.snumber,s.id sid, s.name subjectName,es.scores ,u.cid FROM `user` u "
			+"LEFT JOIN exam_scores es ON es.uid=u.id "
			+"LEFT JOIN exam_subject esub ON es.examSubId=esub.id "
			+"LEFT JOIN subjects s ON s.id=esub.subjects "
			+"WHERE esub.eid=? AND u.cid IN (SELECT id FROM clazz WHERE gid=?) AND s.id IN "
			+ str
			+" ORDER BY cid,uid,sid"; 
		logger.info(this.sql);
		return super.query(new BeanListHandler<UserScores>(UserScores.class),
				params);
	}
}
