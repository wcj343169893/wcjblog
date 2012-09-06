package com.snssly.sms.entity;

/**
 * 父子关系表
 * 
 */
public class UserParent {
	private Integer id;
	private Integer cuid;// 子女编号，外联到user表
	private Integer puid;// 父母编号，外联到user表
	private String relationship;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCuid() {
		return cuid;
	}

	public void setCuid(Integer cuid) {
		this.cuid = cuid;
	}

	public Integer getPuid() {
		return puid;
	}

	public void setPuid(Integer puid) {
		this.puid = puid;
	}

	public String getRelationship() {
		return relationship;
	}

	public void setRelationship(String relationship) {
		this.relationship = relationship;
	}

}
