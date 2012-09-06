package com.snssly.sms.entity;

import java.util.Date;
import java.util.List;

public class Sendlist implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer mid;// 消息编号
	private Integer uid;// 接受者编号
	private Date sendTime;// 发送时间,定时触发
	private Integer status;// 发送状态0.正在发送中,1.发送成功，其他代码则为错误代码
	private Integer isRead;// 是否阅读0.未读，1.已读，-1已删除,-1彻底删除
	private Integer isReply;// 是否回复
	private Integer isParent;// 是否是发送给家长的

	// 临时字段
	private String mobile;// 需要发送的手机号码(也许是家长的电话，也许是学生的电话，也许是教师的电话)
	private String nikeName;
	private String message;// 发送内容
	private List<String> mobileList;// 接收短信的手机号码

	public Sendlist() {
	}

	/**
	 * 构造函数
	 * 
	 * @param uid
	 *            用户编号
	 * @param mobile
	 *            手机号码
	 * @param nikeName
	 *            姓名
	 */
	public Sendlist(Integer uid, String mobile, String nikeName) {
		this.uid = uid;
		this.mobile = mobile;
		this.nikeName = nikeName;
	}

	public Sendlist(Integer mid, Integer uid, Date sendTime, Integer status,
			Integer isRead) {
		this.mid = mid;
		this.uid = uid;
		this.sendTime = sendTime;
		this.status = status;
		this.isRead = isRead;
	}

	public Sendlist(Integer mid, Integer uid, Integer isReply) {
		this.mid = mid;
		this.uid = uid;
		this.isReply = isReply;
	}

	public Integer getIsParent() {
		return isParent;
	}

	public void setIsParent(Integer isParent) {
		this.isParent = isParent;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getMid() {
		return this.mid;
	}

	public void setMid(Integer mid) {
		this.mid = mid;
	}

	public Integer getUid() {
		return this.uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

	public Date getSendTime() {
		return this.sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getIsRead() {
		return this.isRead;
	}

	public void setIsRead(Integer isRead) {
		this.isRead = isRead;
	}

	public String getNikeName() {
		return nikeName;
	}

	public void setNikeName(String nikeName) {
		this.nikeName = nikeName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public List<String> getMobileList() {
		return mobileList;
	}

	public void setMobileList(List<String> mobileList) {
		this.mobileList = mobileList;
	}

	public Integer getIsReply() {
		return isReply;
	}

	public void setIsReply(Integer isReply) {
		this.isReply = isReply;
	}

}
