package com.google.choujone.blog.entity;

import java.io.Serializable;
import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

/**
 * 采集配置类 choujone'blog<br>
 * 功能描述： 2012-2-11
 */
@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class Spider implements Serializable {
	private static final long serialVersionUID = 1L;
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long id = -1L;
	@Persistent
	private String name = "";// 采集名称
	@Persistent
	private String web_host = "";
	// 网站编码
	@Persistent
	private String charset = "utf-8";
	// 列表地址
	@Persistent
	private String web_list_url = "";
	// 列表开始位置和结束位置
	@Persistent
	private String web_list_begin = "";
	@Persistent
	private String web_list_end = "";
	// 内容标题
	@Persistent
	private String web_content_title = "";
	// 内容开始位置和结束位置
	@Persistent
	private String web_content_begin = "";
	@Persistent
	private String web_content_end = "";
	// 内容保留标签
	@Persistent
	private String clear_content_reg = "";
	@Persistent
	private String tids = "";
	// 执行时间
	@Persistent
	private String spider_start ="";
	// 采集次数
	@Persistent
	private Integer count = 0;
	// 采集成功总数
	@Persistent
	private Integer sumCount = 0;
	// 是否开始 0为停止
	@Persistent
	private Integer start = 0;
	// 创建时间
	@Persistent
	private String sdTime = "";
	// 最后修改时间
	@Persistent
	private String edTime = "";
	//是否显示
	@Persistent
	private Integer isVisible = 0;
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

	public String getWeb_host() {
		return web_host;
	}

	public void setWeb_host(String webHost) {
		web_host = webHost;
	}

	public String getCharset() {
		return charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}

	public String getWeb_list_url() {
		return web_list_url;
	}

	public void setWeb_list_url(String webListUrl) {
		web_list_url = webListUrl;
	}

	public String getWeb_list_begin() {
		return web_list_begin;
	}

	public void setWeb_list_begin(String webListBegin) {
		web_list_begin = webListBegin;
	}

	public String getWeb_list_end() {
		return web_list_end;
	}

	public void setWeb_list_end(String webListEnd) {
		web_list_end = webListEnd;
	}

	public String getWeb_content_title() {
		return web_content_title;
	}

	public void setWeb_content_title(String webContentTitle) {
		web_content_title = webContentTitle;
	}

	public String getWeb_content_begin() {
		return web_content_begin;
	}

	public void setWeb_content_begin(String webContentBegin) {
		web_content_begin = webContentBegin;
	}

	public String getWeb_content_end() {
		return web_content_end;
	}

	public void setWeb_content_end(String webContentEnd) {
		web_content_end = webContentEnd;
	}

	public String getClear_content_reg() {
		return clear_content_reg;
	}

	public void setClear_content_reg(String clearContentReg) {
		clear_content_reg = clearContentReg;
	}

	public String getTids() {
		return tids;
	}

	public void setTids(String tids) {
		this.tids = tids;
	}

	
	public String getSpider_start() {
		return spider_start;
	}

	public void setSpider_start(String spiderStart) {
		spider_start = spiderStart;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public Integer getSumCount() {
		return sumCount;
	}

	public void setSumCount(Integer sumCount) {
		this.sumCount = sumCount;
	}

	public Integer getStart() {
		return start;
	}

	public void setStart(Integer start) {
		this.start = start;
	}

	public String getSdTime() {
		return sdTime;
	}

	public void setSdTime(String sdTime) {
		this.sdTime = sdTime;
	}

	public String getEdTime() {
		return edTime;
	}

	public void setEdTime(String edTime) {
		this.edTime = edTime;
	}

	public Integer getIsVisible() {
		return isVisible;
	}

	public void setIsVisible(Integer isVisible) {
		this.isVisible = isVisible;
	}

}
