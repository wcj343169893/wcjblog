package com.snssly.sms.dao;

import java.util.List;

import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.log4j.Logger;

import com.snssly.sms.commons.DBHelper;
import com.snssly.sms.entity.ExamScores;
import com.snssly.sms.entity.ExamSubject;

/**
 * 学校信息管理的所有操作
 * 
 * @author Administrator
 * 
 */
public class ExamScoresDAO extends DBHelper {
	private Logger logger = Logger.getLogger(this.getClass());

	/**
	 * 根据用户编号，考试编号和考试的科目查询考试成绩
	 * 
	 * @param uid
	 *            用户编号
	 * @param eid
	 *            考试编号
	 * @param sids
	 *            科目编号 例如:1,2,3
	 * @param isZero
	 *            是否查询为零的分数 true：不查询为零的分数 ;false：查询为零的分数
	 * @return
	 */
	public List<ExamScores> getES(Integer uid, Integer eid, String sids,
			boolean isZero) {
		this.sql = "SELECT es.*,e.name ename,s.name sname FROM exam_scores es   "
				+ "LEFT JOIN exam_subject esu ON es.examSubId=esu.id    "
				+ "LEFT JOIN subjects s ON esu.subjects=s.id   "
				+ "LEFT JOIN exam e ON esu.eid= e.id   "
				+ "WHERE es.uid=? AND esu.eid=? "
				+ "AND esu.subjects IN ("
				+ sids + ")	";
		if (isZero) {
			this.sql += " and scores > 0";
		}
		logger.info(this.sql);
		return super.query(new BeanListHandler<ExamScores>(ExamScores.class),
				uid, eid);
	}

	/**
	 * 根据uid，查询考试分数
	 * 
	 * @param uid
	 * @param eid
	 * @return
	 */
	public List<ExamScores> getES(Integer uid, Integer eid) {
		this.sql = "SELECT esc.* FROM exam_scores esc WHERE	esc.uid=? AND esc.examSubId IN (SELECT id FROM exam_subject WHERE eid=?) ORDER BY esc.examSubId";
		logger.info(this.sql);
		return super.query(new BeanListHandler<ExamScores>(ExamScores.class),
				uid, eid);
	}

	/**
	 * 新增考试成绩
	 * 
	 * @param es
	 */
	public void add(String str, Object[] params) {
		this.sql = "INSERT INTO `exam_scores`(`uid`,`scores`,`remark`,`examSubId`) VALUES "
				+ str;
		logger.info(this.sql);
		super.update(params);
	}

	/**
	 * 删除考试成绩，根据考试id（eid），班级（cid）
	 * 
	 * @param eid
	 * @param cid
	 */
	public void delete(Integer eid, Integer cid) {
		this.sql = "DELETE FROM  exam_scores WHERE examSubId IN (SELECT id FROM exam_subject WHERE eid=?) AND uid IN (SELECT id  FROM `user` WHERE cid=? AND rid=3 AND isVisible=0)";
		super.update(eid, cid);
	}

	/**
	 * 考试科目的编号esid，和,根据eid,
	 * 
	 * @param eid
	 * @return
	 */
	public List<ExamSubject> getESid(Integer eid) {
		this.sql = "SELECT es.id,es.total,s.name sname FROM exam_subject es LEFT JOIN exam e ON e.id=es.eid LEFT JOIN subjects s ON s.id=subjects WHERE e.id=?";
		logger.info(this.sql);
		return super.query(new BeanListHandler<ExamSubject>(ExamSubject.class),
				eid);
	}

	/**
	 * 考试科目的平均值，各成绩阶段的人数
	 * 
	 * @param esid
	 * @param cid
	 * @param total
	 * @return
	 */
	public List<ExamScores> getAvgCount(Integer esid, Integer cid, Integer total) {
		this.sql = "SELECT AVG(scores) AS 'avg',"
				+ "SUM(CASE WHEN scores>=? AND scores<=? THEN 1 ELSE 0 END) AS 'perfect',"
				+ "SUM(CASE WHEN scores>=? AND scores<? THEN 1 ELSE 0 END) AS 'good',"
				+ "SUM(CASE WHEN scores>=? AND scores<? THEN 1 ELSE 0 END) AS 'inter',"
				+ "SUM(CASE WHEN scores<? THEN 1 ELSE 0 END) AS 'bad' "
				+ "FROM exam_scores WHERE examSubId=? AND uid IN (SELECT id FROM `user` WHERE cid=? AND isVisible=0)";
		logger.info(this.sql);
		return super.query(new BeanListHandler<ExamScores>(ExamScores.class),
				0.8 * total, total, 0.6 * total, 0.8 * total, 0.4 * total,
				0.6 * total, 0.4 * total, esid, cid);
	}

	/**
	 * 总分分段统计
	 * 
	 * @param cid
	 * @param eid
	 * @param end
	 * @param start
	 * @return
	 */
	public List<ExamScores> getSum(Integer cid, Integer eid, Integer end,
			Integer start) {
		this.sql = "SELECT COUNT(*) `count` FROM (SELECT SUM(ess.scores) sumscore "
				+ "FROM `user` u  LEFT JOIN exam_scores ess ON ess.uid=u.id "
				+ "LEFT JOIN exam_subject es ON es.id=examSubId LEFT JOIN exam e ON e.id=es.eid "
				+ "WHERE cid=? AND isVisible=0 AND e.id=? GROUP BY u.id) m "
				+ "WHERE m.sumscore>=? AND m.sumscore<?";
		logger.info(this.sql);
		return super.query(new BeanListHandler<ExamScores>(ExamScores.class),
				cid, eid, start, end);
	}

	/**
	 * 得到具体学生的具体考试的总分
	 * 
	 * @param cid
	 * @param eid
	 * @param uid
	 * @return
	 */
	public List<ExamScores> getSumByUid(Integer cid, Integer eid, Integer uid) {
		this.sql = "SELECT m.sumscore `sum` FROM (SELECT u.id,SUM(ess.scores) sumscore "
				+ "FROM `user` u  LEFT JOIN exam_scores ess ON ess.uid=u.id "
				+ "LEFT JOIN exam_subject es ON es.id=examSubId "
				+ "LEFT JOIN exam e ON e.id=es.eid "
				+ "WHERE cid=? AND isVisible=0 AND e.id=? GROUP BY u.id) m WHERE m.id=?";
		logger.info(this.sql);
		return super.query(new BeanListHandler<ExamScores>(ExamScores.class),
				cid, eid, uid);
	}

	/**
	 * 得到具体学生的具体考试的名次
	 * 
	 * @param cid
	 * @param eid
	 * @param uid
	 * @return
	 */
	public List<ExamScores> getRankByUid(Integer cid, Integer eid, Integer uid) {
		this.sql = "SELECT COUNT(*)+1 rank FROM (SELECT u.id,SUM(ess.scores) sumscore "
				+ "FROM `user` u  LEFT JOIN exam_scores ess ON ess.uid=u.id "
				+ "LEFT JOIN exam_subject es ON es.id=examSubId "
				+ "LEFT JOIN exam e ON e.id=es.eid "
				+ "WHERE cid=? AND isVisible=0 AND e.id=? GROUP BY u.id) m "
				+ "WHERE m.sumscore>(SELECT sumscore FROM (SELECT u.id,SUM(ess.scores) sumscore "
				+ "FROM `user` u  LEFT JOIN exam_scores ess ON ess.uid=u.id "
				+ "LEFT JOIN exam_subject es ON es.id=examSubId "
				+ "LEFT JOIN exam e ON e.id=es.eid "
				+ "WHERE cid=? AND isVisible=0 AND e.id=? GROUP BY u.id) m WHERE m.id=?)";
		logger.info(this.sql);
		return super.query(new BeanListHandler<ExamScores>(ExamScores.class),
				cid, eid, cid, eid, uid);
	}

	/**
	 * 得到具体学生的有某一科的具体考试的分数
	 * 
	 * @param sid
	 * @param eid
	 * @param uid
	 * @return
	 */
	public List<ExamScores> getSubjectByUid(Integer uid, Integer eid,
			Integer sid) {
		this.sql = "SELECT ess.scores sum FROM exam_scores ess LEFT JOIN exam_subject es ON ess.examSubId=es.id "
				+ "LEFT JOIN exam e ON e.id=es.eid LEFT JOIN subjects s ON s.id=es.subjects "
				+ "WHERE ess.uid=? AND e.id=? AND s.id=?";
		logger.info(this.sql);
		return super.query(new BeanListHandler<ExamScores>(ExamScores.class),
				uid, eid, sid);
	}

	/**
	 * 得到具体学生的有某一科的具体考试的名次
	 * 
	 * @param cid
	 * @param eid
	 * @param sid
	 * @param uid
	 * @return
	 */
	public List<ExamScores> getRankBySubject(Integer cid, Integer eid,
			Integer sid, Integer uid) {
		this.sql = "SELECT COUNT(*)+1 rank FROM (SELECT ess.uid,ess.scores FROM exam_scores ess LEFT JOIN exam_subject es ON ess.examSubId=es.id "
				+ "LEFT JOIN exam e ON e.id=es.eid LEFT JOIN subjects s ON s.id=es.subjects "
				+ "WHERE e.id=? AND s.id=? AND ess.uid IN (SELECT id FROM `user` WHERE rid=3 AND isVisible=0 AND cid=?)) m "
				+ "WHERE m.scores>(SELECT ess.scores FROM exam_scores ess LEFT JOIN exam_subject es ON ess.examSubId=es.id "
				+ "LEFT JOIN exam e ON e.id=es.eid LEFT JOIN subjects s ON s.id=es.subjects "
				+ "WHERE ess.uid=? AND e.id=? AND s.id=?)";
		logger.info(this.sql);
		return super.query(new BeanListHandler<ExamScores>(ExamScores.class),
				eid, sid, cid, uid, eid, sid);
	}

	/**
	 * 查询学生在某次考试中的年级名次
	 * 
	 * @param uid
	 *            学生编号
	 * @param eid
	 *            考试编号
	 * @return 名次
	 */
	public Integer getRankByUidEid(Integer uid, Integer eid) {
		this.sql = "SELECT COUNT(*)+1 FROM (SELECT SUM(scores) sumscores FROM exam_scores es LEFT JOIN exam_subject esub ON es.examSubId=esub.id	WHERE  esub.eid=? GROUP BY uid ORDER BY sumscores DESC) ss WHERE ss.sumscores>(SELECT SUM(scores) FROM exam_scores es	LEFT JOIN exam_subject esub ON es.examSubId=esub.id WHERE uid=? AND esub.eid=? GROUP BY uid )";
		return super.count(eid, uid, eid);
	}

	/**
	 * 查询学生在某次考试中的班级名次
	 * 
	 * @param uid
	 * @param eid
	 * @param cid
	 * @return
	 */
	public Integer getRankByUidEid(Integer uid, Integer eid, Integer cid) {
		this.sql = "SELECT COUNT(*)+1 FROM (SELECT SUM(scores) sumscores FROM exam_scores es LEFT JOIN exam_subject esub ON es.examSubId=esub.id LEFT JOIN user u ON es.uid=u.id	WHERE  esub.eid=? AND u.cid=? GROUP BY uid ORDER BY sumscores DESC) ss WHERE ss.sumscores>(SELECT SUM(scores) FROM exam_scores es	LEFT JOIN exam_subject esub ON es.examSubId=esub.id LEFT JOIN user u ON es.uid=u.id WHERE uid=? AND esub.eid=? AND u.cid=? GROUP BY uid )";
		return super.count(eid, cid, uid, eid, cid);
	}

	/**
	 * 查询学生在某次考试中的平均分
	 * 
	 * @param uid
	 * @param eid
	 * @return
	 */
	public Integer getAvgByUidEid(Integer uid, Integer eid) {
		this.sql = "SELECT AVG(scores) FROM exam_scores es	LEFT JOIN exam_subject esub ON es.examSubId=esub.id	WHERE uid=? AND esub.eid=? GROUP BY uid ";
		return super.count(uid, eid);
	}

	/**
	 * 查询学生在某次考试中的总分
	 * 
	 * @param uid
	 * @param eid
	 * @return
	 */
	public Integer getSumByUidEid(Integer uid, Integer eid) {
		this.sql = "SELECT sum(scores) FROM exam_scores es	LEFT JOIN exam_subject esub ON es.examSubId=esub.id	WHERE uid=? AND esub.eid=? GROUP BY uid ";
		return super.count(uid, eid);
	}
}
