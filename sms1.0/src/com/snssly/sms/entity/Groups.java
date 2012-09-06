package com.snssly.sms.entity;

import java.util.ArrayList;
import java.util.List;

public class Groups implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer tid;
	private String name;
	private Integer rid;// 角色编号
	private List<User> userList=new ArrayList<User>();// 用户列表

	public Groups() {
	}

	public Groups(Integer tid, String name) {
		this.tid = tid;
		this.name = name;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getTid() {
		return tid;
	}

	public void setTid(Integer tid) {
		this.tid = tid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getRid() {
		return rid;
	}

	public void setRid(Integer rid) {
		this.rid = rid;
	}

	public List<User> getUserList() {
		return userList;
	}

	public void setUserList(List<User> userList) {
		this.userList = userList;
	}

}
