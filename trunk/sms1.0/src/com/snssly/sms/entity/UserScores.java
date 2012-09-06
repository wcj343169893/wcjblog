package com.snssly.sms.entity;

public class UserScores implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Integer uid;//用户id
	private String nikeName;// 名字
	private Integer snumber;// 名字
	private Integer sid;//科目id
	private String subjectName;//科目名称	
	private Double scores;//科目成绩

	public UserScores() {
	}

	public UserScores(Integer uid,String nikeName,Integer snumber, Integer sid,String subjectName,Double scores) {
		this.uid = uid;
		this.nikeName = nikeName;
		this.snumber = snumber;
		this.sid = sid;
		this.subjectName = subjectName;
		this.scores = scores;
	}

	public Integer getUid() {
		return uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

	public String getNikeName() {
		return nikeName;
	}

	public void setNikeName(String nikeName) {
		this.nikeName = nikeName;
	}

	public Integer getSid() {
		return sid;
	}

	public void setSid(Integer sid) {
		this.sid = sid;
	}

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	public Double getScores() {
		return scores;
	}

	public void setScores(Double scores) {
		this.scores = scores;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Integer getSnumber() {
		return snumber;
	}

	public void setSnumber(Integer snumber) {
		this.snumber = snumber;
	}
}
