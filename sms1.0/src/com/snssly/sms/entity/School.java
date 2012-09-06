package com.snssly.sms.entity;

// Generated 2010-12-24 16:34:07 by Hibernate Tools 3.2.4.GA

import java.util.Date;

import com.snssly.sms.commons.Env;

/**
 * School generated by hbm2java
 */
public class School implements java.io.Serializable {

	private Integer id;
	private String name;
	private String description;
	private String url;
	private Date createTime;
	private String introduction;

	public School() {
	}

	public School(String name, String description, String url, Date createTime) {
		this.name = name;
		this.description = description;
		this.url = url;
		this.createTime = createTime;
	}

	public String getIntroduction() {
		return Env.replace(description, 20)+"...";
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
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

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}
