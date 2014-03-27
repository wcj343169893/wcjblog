package com.google.choujone.blog.entity;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

/**
 * choujone'blog<br>
 * 功能描述：微微网络电话id
 */
@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class Weiwei {
	private static final long serialVersionUID = 1L;
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long id = -1L;
	@Persistent
	private Integer no = 0;// 微微编号
	@Persistent
	private String sdTime = "";// 创建时间
	@Persistent
	private String ip = "";// 请求ip

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getNo() {
		return no;
	}

	public void setNo(Integer no) {
		this.no = no;
	}

	public String getSdTime() {
		return sdTime;
	}

	public void setSdTime(String sdTime) {
		this.sdTime = sdTime;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
