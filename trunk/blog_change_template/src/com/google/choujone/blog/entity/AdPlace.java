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
 * 功能描述：广告位 <br>
 * 2010-11-24
 */
@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class AdPlace implements Serializable {

	private static final long serialVersionUID = 1L;
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long id = -1L;
	@Persistent
	private String name = "";// 广告位的名字
	@Persistent
	private String description = "";// 描述
	@Persistent
	private Text code = new Text("");// 默认代码
	@Persistent
	private String sdTime = "";// 创建时间
	@Persistent
	private Integer isVisible = 0;// 是否展示

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

	public Text getCode() {
		return code;
	}

	public void setCode(Text code) {
		this.code = code;
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

	public Integer getIsVisible() {
		return isVisible;
	}

	public void setIsVisible(Integer isVisible) {
		this.isVisible = isVisible;
	}

}
