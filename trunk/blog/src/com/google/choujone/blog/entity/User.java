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
 * 功能描述：用户信息 2010-11-18
 */
@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class User implements Serializable {
	private static final long serialVersionUID = 1L;
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long id = -1L;
	@Persistent
	private String pTitle = "";// 博客标题
	@Persistent
	private String url = "";// 博客访问地址
	@Persistent
	private String ctitle = "";// 子标题
	@Persistent
	private String notice = "";// 公告
	@Persistent
	private String name = "";// 登录名
	@Persistent
	private String password = "";// 登录密码
	@Persistent
	private String email = "";// 电子邮件
	@Persistent
	private String brithday = "";// 生日
	@Persistent
	private String address = "";// 地址
	@Persistent
	private String description = "";// 自我描述
	@Persistent
	private String blogDescription = "";// 博客描述
	@Persistent
	private String blogKeyword = "";// 博客关键字
	@Persistent
	private String style;// 博客前台样式
	@Persistent
	private Integer isWeather;// 是否显示天气
	@Persistent
	private Integer isCalendars;// 是否显示日历
	@Persistent
	private Integer isHotBlog;// 是否显示热门文章
	@Persistent
	private Integer isNewReply;// 是否显示最新评论
	@Persistent
	private Integer isLeaveMessage;// 是否显示留言
	@Persistent
	private Integer isStatistics;// 是否显示统计
	@Persistent
	private Integer isFriends;// 是否显示友情链接
	@Persistent
	private Integer isInfo;// 是否显示个人资料
	@Persistent
	private Integer isTags;// 是否显示tags
	@Persistent
	private Integer isType;// 是否显示文章类型
	@Persistent
	private Text preMessage = new Text("");// 留言寄语

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

	public String getBrithday() {
		return brithday;
	}

	public void setBrithday(String brithday) {
		this.brithday = brithday;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getpTitle() {
		return pTitle;
	}

	public void setpTitle(String pTitle) {
		this.pTitle = pTitle;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getCtitle() {
		return ctitle;
	}

	public void setCtitle(String ctitle) {
		this.ctitle = ctitle;
	}

	public String getNotice() {
		return notice;
	}

	public void setNotice(String notice) {
		this.notice = notice;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Integer getIsWeather() {
		return isWeather;
	}

	public void setIsWeather(Integer isWeather) {
		this.isWeather = isWeather;
	}

	public Integer getIsCalendars() {
		return isCalendars;
	}

	public void setIsCalendars(Integer isCalendars) {
		this.isCalendars = isCalendars;
	}

	public Integer getIsHotBlog() {
		return isHotBlog;
	}

	public void setIsHotBlog(Integer isHotBlog) {
		this.isHotBlog = isHotBlog;
	}

	public Integer getIsNewReply() {
		return isNewReply;
	}

	public void setIsNewReply(Integer isNewReply) {
		this.isNewReply = isNewReply;
	}

	public Integer getIsLeaveMessage() {
		return isLeaveMessage;
	}

	public void setIsLeaveMessage(Integer isLeaveMessage) {
		this.isLeaveMessage = isLeaveMessage;
	}

	public Integer getIsStatistics() {
		return isStatistics;
	}

	public void setIsStatistics(Integer isStatistics) {
		this.isStatistics = isStatistics;
	}

	public Integer getIsFriends() {
		return isFriends;
	}

	public void setIsFriends(Integer isFriends) {
		this.isFriends = isFriends;
	}

	public Integer getIsInfo() {
		return isInfo;
	}

	public void setIsInfo(Integer isInfo) {
		this.isInfo = isInfo;
	}

	public Integer getIsTags() {
		return isTags;
	}

	public void setIsTags(Integer isTags) {
		this.isTags = isTags;
	}

	public Integer getIsType() {
		return isType;
	}

	public void setIsType(Integer isType) {
		this.isType = isType;
	}

	public String getBlogDescription() {
		return blogDescription;
	}

	public void setBlogDescription(String blogDescription) {
		this.blogDescription = blogDescription;
	}

	public String getBlogKeyword() {
		return blogKeyword;
	}

	public void setBlogKeyword(String blogKeyword) {
		this.blogKeyword = blogKeyword;
	}

	public Text getPreMessage() {
		return preMessage;
	}

	public void setPreMessage(Text preMessage) {
		this.preMessage = preMessage;
	}

}
