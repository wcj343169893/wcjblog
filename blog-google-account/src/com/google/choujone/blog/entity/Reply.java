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
 * 功能描述：回复 2010-11-18
 */
@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class Reply implements Serializable {
	private static final long serialVersionUID = 1L;
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long id = -1L;
	@Persistent
	private Long bid = -1L;// 博客id(如果博客id为-1，则是给网站留言)
	@Persistent
	private String name = "";// 回复者
	@Persistent
	private String email = "";// 回复者邮箱
	@Persistent
	private String content = "";// 回复内容
//	@Persistent
//	private Text content2 = new Text("");
	@Persistent
	private String sdTime = "";// 留言时间
	@Persistent
	private String url = "";// 留言者url
	@Persistent
	private String replyMessage = "";// 我的回复
	@Persistent
	private String replyTime = "";// 我回复时间
	@Persistent
	private String visiter = "";// 访问者的信息

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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

//	public String getContent() {
//		String val = content;
//		if (content2 != null) {
//			val = content2.getValue();
//		}
//		return val;
//	}

//	public String getContent(int length, String str) {
//		String val = content;
//		if (content2 != null) {
//			val = content2.getValue().trim().replaceAll("\\<.*?>", "");
//			val = val.replaceAll(" ", "");
//			val = val.replaceAll("&nbsp;", "");
//			val = val.replaceAll("<br>", "");
//			val = val.replaceAll("<BR>", "");
//			if (length <= 0 || length > val.length()) {
//				length = val.length();
//			}
//			String ending = str != null ? str : "...";
//			val = val.substring(0, length) + ending;
//		}
//		return val;
//	}

	public void setContent(String content) {
		this.content = content;
	}

	public Long getBid() {
		return bid;
	}

//	public Text getContent2() {
//		return content2;
//	}
//
//	public void setContent2(Text content2) {
//		this.content2 = content2;
//	}

	public void setBid(Long bid) {
		this.bid = bid;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getReplyMessage() {
		return replyMessage;
	}

	public void setReplyMessage(String replyMessage) {
		this.replyMessage = replyMessage;
	}

	public String getReplyTime() {
		return replyTime;
	}

	public void setReplyTime(String replyTime) {
		this.replyTime = replyTime;
	}

	public String getSdTime() {
		return sdTime;
	}

	public void setSdTime(String sdTime) {
		this.sdTime = sdTime;
	}

	public String getVisiter() {
		return visiter;
	}

	public void setVisiter(String visiter) {
		this.visiter = visiter;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getContent() {
		return content;
	}

}
