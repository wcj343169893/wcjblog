package com.google.choujone.blog.entity;

import java.io.Serializable;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

/**
 * choujone'blog<br>
 * 功能描述：友情链接 2010-11-18
 */
@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class Friends implements Serializable {

	private static final long serialVersionUID = 1L;
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long id = -1L;
	@Persistent
	private String name = "";// 朋友的名字
	@Persistent
	private String url = "";// 朋友的链接地址
	@Persistent
	private String description;// 描述
	@Persistent
	private String sdTime = "";// 创建时间
	@Persistent
	private Integer istop = 0;// 是否推荐
	@Persistent
	private Integer tid = 0;// 分类编号1：友情链接，2：我做的网站，3：商业链接

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getSdTime() {
		return sdTime;
	}

	public void setSdTime(String sdTime) {
		this.sdTime = sdTime;
	}

	public Integer getIstop() {
		return istop;
	}

	public void setIstop(Integer istop) {
		this.istop = istop;
	}

	public Integer getTid() {
		return tid;
	}

	public void setTid(Integer tid) {
		this.tid = tid;
	}
}
