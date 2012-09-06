package com.snssly.sms.entity;


// Generated 2010-12-24 16:34:07 by Hibernate Tools 3.2.4.GA



/**
 * 临时实体，领导成绩分析，各班级的名次
 * @author Administrator
 *
 */
public class ExamRowNum implements java.io.Serializable {
	private Integer cid;
	private double avgsumscore;	
	
	public ExamRowNum() {
	}

	public ExamRowNum(Integer rownum, Integer cid, double avgsumscore) {
		this.cid = cid;
		this.avgsumscore = avgsumscore;
	}

	public Integer getCid() {
		return cid;
	}

	public void setCid(Integer cid) {
		this.cid = cid;
	}

	public double getAvgsumscore() {
		return avgsumscore;
	}

	public void setAvgsumscore(double avgsumscore) {
		this.avgsumscore = avgsumscore;
	}

}
