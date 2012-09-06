package com.snssly.sms.dao;

import java.util.List;

import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.log4j.Logger;

import com.snssly.sms.commons.DBHelper;
import com.snssly.sms.entity.ExamRowNum;

public class ExamRowNumDao extends DBHelper {
	private Logger logger = Logger.getLogger(this.getClass());	
	
	/**
	 * 得到临时实体，（班级编号，班级平均值），班级按到平均值的由大到小的排序
	 * @param eid
	 * @param gid
	 * @return
	 */
	public List<ExamRowNum> findAll(Integer eid ,Integer gid){
		this.sql = "SELECT c.id cid,AVG(m.sumscore) avgsumscore FROM (SELECT u.id,u.cid cid,SUM(ess.scores) sumscore "
				+"FROM `user` u  LEFT JOIN exam_scores ess ON ess.uid=u.id "
				+"LEFT JOIN exam_subject es ON es.id=examSubId  "
				+"LEFT JOIN exam e ON e.id=es.eid  "
				+"WHERE isVisible=0 AND e.id=? GROUP BY u.id) m "
				+"LEFT JOIN clazz c ON c.id=m.cid WHERE c.id IN (SELECT id FROM clazz WHERE gid=?) GROUP BY c.id "
				+"ORDER BY avgsumscore DESC";
		logger.info(this.sql);
		return super.query(new BeanListHandler<ExamRowNum>(ExamRowNum.class),eid,gid);
		
	}
}
