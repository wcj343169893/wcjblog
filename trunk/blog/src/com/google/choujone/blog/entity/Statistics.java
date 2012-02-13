package com.google.choujone.blog.entity;

import java.util.HashMap;
import java.util.Map;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Text;

/**
 * choujone'blog<br>
 * 功能描述：统计 2012-2-13
 */
@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class Statistics {

	private static final long serialVersionUID = 1L;
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long id = -1L;
	/**
	 * 博客总条数
	 */
	@Persistent
	private Integer blog_size = 0;
	/**
	 * 可以显示的博客数量
	 */
	@Persistent
	private Integer blog_visible_size = 0;
	/**
	 * 博客类型条数
	 */
	@Persistent
	private Integer blogType_size = 0;
	/**
	 * 友情链接条数
	 */
	@Persistent
	private Integer friends_size = 0;
	/**
	 * 回复条数
	 */
	@Persistent
	private Integer reply_size = 0;
	/**
	 * 采集规则条数
	 */
	@Persistent
	private Integer spider_size = 0;

	/**
	 * 浏览总数
	 */
	@Persistent
	private Integer scan_count = 0;
	/**
	 *留言总数
	 */
	@Persistent
	private Integer message_count = 0;

	/**
	 * 博客类型对应的条数
	 */
	@Persistent
	private String blogType_blog_size = "";

	/**
	 * 每篇博客的回复数量
	 */
	@Persistent
	private Text blog_reply_size = new Text("");

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getBlog_size() {
		return blog_size;
	}

	public void setBlog_size(Integer blogSize) {
		blog_size = blogSize;
	}

	public Integer getBlog_visible_size() {
		return blog_visible_size;
	}

	public void setBlog_visible_size(Integer blogVisibleSize) {
		blog_visible_size = blogVisibleSize;
	}

	public Integer getBlogType_size() {
		return blogType_size;
	}

	public void setBlogType_size(Integer blogTypeSize) {
		blogType_size = blogTypeSize;
	}

	public Integer getFriends_size() {
		return friends_size;
	}

	public void setFriends_size(Integer friendsSize) {
		friends_size = friendsSize;
	}

	public Integer getReply_size() {
		return reply_size;
	}

	public void setReply_size(Integer replySize) {
		reply_size = replySize;
	}

	public Integer getSpider_size() {
		return spider_size;
	}

	public void setSpider_size(Integer spiderSize) {
		spider_size = spiderSize;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Integer getScan_count() {
		return scan_count;
	}

	public void setScan_count(Integer scanCount) {
		scan_count = scanCount;
	}

	public Integer getMessage_count() {
		return message_count;
	}

	public void setMessage_count(Integer messageCount) {
		message_count = messageCount;
	}

	public String getBlogType_blog_size() {
		return blogType_blog_size;
	}

	public void setBlogType_blog_size(String blogTypeBlogSize) {
		blogType_blog_size = blogTypeBlogSize;
	}

	public Text getBlog_reply_size() {
		return blog_reply_size;
	}

	public void setBlog_reply_size(Text blogReplySize) {
		blog_reply_size = blogReplySize;
	}

}
