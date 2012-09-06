package com.snssly.sms.entity;

// Generated 2010-12-24 16:34:07 by Hibernate Tools 3.2.4.GA

import java.util.Date;

/**
 * ExamClazzSend generated by hbm2java
 */
public class ExamClazzSend implements java.io.Serializable {

	private Integer id;
	private Integer esid;
	private Integer uid;
	private Integer cid;
	private Integer isZero;
	private Integer isAverage;
	private Integer isRemark;
	private Date sendTime;
	private Integer status;

	public ExamClazzSend() {
	}

	public ExamClazzSend(Integer esid, Integer uid, Integer cid,
			Integer isZero, Integer isAverage, Integer isRemark, Date sendTime,
			Integer status) {
		this.esid = esid;
		this.uid = uid;
		this.cid = cid;
		this.isZero = isZero;
		this.isAverage = isAverage;
		this.isRemark = isRemark;
		this.sendTime = sendTime;
		this.status = status;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getEsid() {
		return this.esid;
	}

	public void setEsid(Integer esid) {
		this.esid = esid;
	}

	public Integer getUid() {
		return this.uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

	public Integer getCid() {
		return this.cid;
	}

	public void setCid(Integer cid) {
		this.cid = cid;
	}

	public Integer getIsZero() {
		return this.isZero;
	}

	public void setIsZero(Integer isZero) {
		this.isZero = isZero;
	}

	public Integer getIsAverage() {
		return this.isAverage;
	}

	public void setIsAverage(Integer isAverage) {
		this.isAverage = isAverage;
	}

	public Integer getIsRemark() {
		return this.isRemark;
	}

	public void setIsRemark(Integer isRemark) {
		this.isRemark = isRemark;
	}

	public Date getSendTime() {
		return this.sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}
