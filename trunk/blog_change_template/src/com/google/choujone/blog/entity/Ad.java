package com.google.choujone.blog.entity;

import java.io.Serializable;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Text;

/**
 * choujone'blog<br>
 * 功能描述：广告 <br>
 * 2010-11-24
 */
@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class Ad implements Serializable {

	private static final long serialVersionUID = 1L;
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long id = -1L;
	@Persistent
	private String name = "";// 广告的名字
	@Persistent
	private String description;// 描述
	@Persistent
	private Long pid = -1L;// 广告位编号
	@Persistent
	private Text code = new Text("");// 广告代码
	@Persistent
	private String sdTime = "";// 创建时间
	@Persistent
	private String beginTime = "";// 投放开始时间
	@Persistent
	private String endTime = "";// 投放结束时间
	@Persistent
	private Integer isVisible = 0;// 是否展示

	@Persistent
	private Integer count = 0;// 查看数

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

	public Text getCode() {
		return code;
	}

	public void setCode(Text code) {
		this.code = code;
	}

	public String getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public Integer getIsVisible() {
		return isVisible;
	}

	public void setIsVisible(Integer isVisible) {
		this.isVisible = isVisible;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public Long getPid() {
		return pid;
	}

	public void setPid(Long pid) {
		this.pid = pid;
	}

}
