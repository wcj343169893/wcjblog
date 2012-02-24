package com.google.choujone.blog.entity;

import java.io.Serializable;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Text;
import com.google.choujone.blog.util.Tools;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class Blog implements Serializable {
	private static final long serialVersionUID = 1L;
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long id = -1L;
	@Persistent
	private Long tid = -1L;// 博客类型
	@Persistent
	private String title = "";
	@Persistent
	private Text content = new Text("");// 内容
	@Persistent
	private String tag = "";
	@Persistent
	private String sdTime = "";// 发布时间
	@Persistent
	private Integer count = 0;// 查看数
	@Persistent
	private Integer replyCount = 0;// 回复数
	@Persistent
	private Integer isVisible = 0;// 是否发表
	@Persistent
	private String moTime = "";// 最后修改时间

	public Blog() {
	}

	public Blog(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public String getTitle(int len) {
		if (len <= 0 || len > title.length())
			len = title.length();
		return Tools.FilterHTML(title.substring(0, len));
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Text getContent() {
		return this.content;
	}

	public Text getContent(int len) {
		if (content != null) {
			String val = content.getValue().trim().replaceAll("\\<.*?>", "");
			val = val.replaceAll(" ", "");
			val = val.replaceAll("<br>", "");
			val = val.replaceAll("<BR>", "");
			if (len <= 0 || len > val.length())
				len = val.length();
			return new Text(val.substring(0, len) + "...");
		}
		return new Text("");
	}

	public Text getContent(int len, String str) {
		if (content != null) {
			String val = content.getValue().trim().replaceAll("\\<.*?>", "");
			val = val.replaceAll(" ", "");
			val = val.replaceAll("&nbsp;", "");
			val = val.replaceAll("<br>", "");
			val = val.replaceAll("<BR>", "");
			if (len <= 0 || len > val.length()) {
				len = val.length();
			}
			String ending=str != null ? str : "...";
			return new Text(val.substring(0, len) + ending);
		}
		return new Text("");
	}

	public void setContent(Text content) {
		this.content = content;
	}

	public Long getTid() {
		return tid;
	}

	public void setTid(Long tid) {
		this.tid = tid;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getSdTime() {
		return sdTime;
	}

	public void setSdTime(String sdTime) {
		this.sdTime = sdTime;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public Integer getReplyCount() {
		return replyCount;
	}

	public void setReplyCount(Integer replyCount) {
		this.replyCount = replyCount;
	}

	public Integer getIsVisible() {
		return isVisible;
	}

	public void setIsVisible(Integer isVisible) {
		this.isVisible = isVisible;
	}

	public String getMoTime() {
		return moTime;
	}

	public void setMoTime(String moTime) {
		this.moTime = moTime;
	}

}
