package com.snssly.sms.entity;

// Generated 2010-12-24 16:34:07 by Hibernate Tools 3.2.4.GA

/**
 * GradeSubjects generated by hbm2java
 */
public class GradeSubjects implements java.io.Serializable {

	private Integer id;
	private Integer sid;
	private Integer gid;

	public GradeSubjects() {
	}

	public GradeSubjects(Integer sid, Integer gid) {
		this.sid = sid;
		this.gid = gid;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getSid() {
		return this.sid;
	}

	public void setSid(Integer sid) {
		this.sid = sid;
	}

	public Integer getGid() {
		return this.gid;
	}

	public void setGid(Integer gid) {
		this.gid = gid;
	}

}
