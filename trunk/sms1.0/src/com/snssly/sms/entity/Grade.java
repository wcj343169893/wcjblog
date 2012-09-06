package com.snssly.sms.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Grade implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Integer id;
	private String name;
	private Integer schoolid;
	private Date createTime;
	private List<Clazz> clazzList=new ArrayList<Clazz>();// 班级列表
	private List<Groups> groupsList=new ArrayList<Groups>();// 年级分组

	public Grade() {
	}

	public Grade(String name, Integer schoolid, Date createTime) {
		this.name = name;
		this.schoolid = schoolid;
		this.createTime = createTime;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getSchoolid() {
		return this.schoolid;
	}

	public void setSchoolid(Integer schoolid) {
		this.schoolid = schoolid;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public List<Clazz> getClazzList() {
		return clazzList;
	}

	public void setClazzList(List<Clazz> clazzList) {
		this.clazzList = clazzList;
	}

	public List<Groups> getGroupsList() {
		return groupsList;
	}

	public void setGroupsList(List<Groups> groupsList) {
		this.groupsList = groupsList;
	}

}
