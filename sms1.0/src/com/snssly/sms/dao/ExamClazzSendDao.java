package com.snssly.sms.dao;

import com.snssly.sms.commons.DBHelper;
import com.snssly.sms.entity.ExamClazzSend;

public class ExamClazzSendDao extends DBHelper {
	/**
	 * 新增考试发送记录
	 * 
	 * @param ecs
	 * @return 新增编号
	 */
	public Integer add(ExamClazzSend ecs) {
		this.sql = "INSERT INTO exam_clazz_send(esid,uid,cid,isZero,isAverage,isRemark,sendTime,status) values(?,?,?,?,?,?,?,?)";
		super.update(ecs.getEsid(), ecs.getUid(), ecs.getCid(),
				ecs.getIsZero(), ecs.getIsAverage(), ecs.getIsRemark(), ecs
						.getSendTime(), ecs.getStatus());
		return super.getLastInsertId();
	}
}
