package com.snssly.sms.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.snssly.sms.commons.DBHelper;

/**
 * 年级科目操作
 * 
 * 
 */
public class GradeSubjectDao extends DBHelper {

	private Logger logger = Logger.getLogger(this.getClass());

	/**
	 * 添加年级科目
	 * 
	 * @param gid
	 * @param sids
	 */
	public void add(Integer gid, List<String> sids) {
		Integer size = sids.size();
		List<String> paramList = new ArrayList<String>();
		if (size > 0) {
			this.sql = "insert into grade_subjects(gid,sid)values ";
			for (int i = 0; i < size; i++) {
				this.sql += "(?,?)";
				if (i < size - 1) {
					this.sql += ",";
				}
				paramList.add(gid + "");
				paramList.add(sids.get(i));
			}
			logger.info(this.sql);
			super.update(paramList.toArray());
		}
	}

	/**
	 * 删除年级的科目
	 * 
	 * @param gid
	 */
	public void delete(Integer gid) {
		this.sql = "delete from grade_subjects where gid=?";
		logger.info(this.sql);
		super.update(gid);
	}

}
