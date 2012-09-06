package com.snssly.sms.entity;

// Generated 2010-12-24 16:34:07 by Hibernate Tools 3.2.4.GA

import java.util.Date;

public class Message implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;// 编号
	private String title;// 标题
	private Integer tid;// 类型编号
	private String tname;// 类型名称
	private Integer isModel;// 是否为模板
	private Integer uid;// 发送者编号
	private String nikeName;// 用户昵称
	private String mobile;// 用户电话号码
	private Date sendTime;// 发送时间
	private String content;// 内容
	private Integer status;// 短信发送状态

	private Date createTime;
	private Integer isSend;// 是否发送，0.存为草稿，1.已发送
	private Integer isRead;// 接收者是否阅读(0.未读，1.已读)
	private Integer isDelete;// 接收者是否删除(0.显示，1.删除)
	private Integer isVisible;// 发送者是否删除(0.显示，1.删除)

	// 临时字段
	private String st;// 接收页面上传来的发送时间
	private Integer zero;// 是否发送为零的分数
	private Integer avgs;// 是否发送平均分
	private Integer description;// 是否发送成绩备注
	private Integer rank;// 是否发送名次
	private Integer total;// 是否发送总分
	private String toUser;// 接收对象
	private Integer isSignature;// 是否短信签名
	private String userIds;// 收件人列表
	private Integer isMessage;// 发送短信
	private Integer isLeaveMessage;// 私人留言
	private Integer isReply;// 是否回复
	private Integer replyId;// 回复编号
	private Integer messageCount;// 不同类型的短信发送数量

	public Message() {
	}

	public Message(String title, Integer tid, Integer isModel, Integer uid,
			String content, Date createTime, Integer isSend) {
		this.title = title;
		this.tid = tid;
		this.isModel = isModel;
		this.uid = uid;
		this.content = content;
		this.createTime = createTime;
		this.isSend = isSend;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getTid() {
		return this.tid;
	}

	public void setTid(Integer tid) {
		this.tid = tid;
	}

	public Integer getIsModel() {
		return this.isModel;
	}

	public void setIsModel(Integer isModel) {
		this.isModel = isModel;
	}

	public Integer getUid() {
		return this.uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getTname() {
		return tname;
	}

	public void setTname(String tname) {
		this.tname = tname;
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

	public Date getSendTime() {
		return sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getIsSend() {
		return isSend;
	}

	public void setIsSend(Integer isSend) {
		this.isSend = isSend;
	}

	public Integer getIsRead() {
		return isRead;
	}

	public void setIsRead(Integer isRead) {
		this.isRead = isRead;
	}

	public Integer getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}

	public Integer getIsVisible() {
		return isVisible;
	}

	public void setIsVisible(Integer isVisible) {
		this.isVisible = isVisible;
	}

	public String getSt() {
		return st;
	}

	public void setSt(String st) {
		this.st = st;
	}

	public Integer getZero() {
		return zero;
	}

	public void setZero(Integer zero) {
		this.zero = zero;
	}

	public Integer getAvgs() {
		return avgs;
	}

	public void setAvgs(Integer avgs) {
		this.avgs = avgs;
	}

	public Integer getDescription() {
		return description;
	}

	public void setDescription(Integer description) {
		this.description = description;
	}

	public Integer getRank() {
		return rank;
	}

	public void setRank(Integer rank) {
		this.rank = rank;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public String getToUser() {
		return toUser;
	}

	public void setToUser(String toUser) {
		this.toUser = toUser;
	}

	public Integer getIsSignature() {
		return isSignature;
	}

	public void setIsSignature(Integer isSignature) {
		this.isSignature = isSignature;
	}

	public String getUserIds() {
		return userIds;
	}

	public void setUserIds(String userIds) {
		this.userIds = userIds;
	}

	public Integer getIsMessage() {
		return isMessage;
	}

	public void setIsMessage(Integer isMessage) {
		this.isMessage = isMessage;
	}

	public Integer getIsLeaveMessage() {
		return isLeaveMessage;
	}

	public void setIsLeaveMessage(Integer isLeaveMessage) {
		this.isLeaveMessage = isLeaveMessage;
	}

	public Integer getMessageCount() {
		return messageCount;
	}

	public void setMessageCount(Integer messageCount) {
		this.messageCount = messageCount;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getIsReply() {
		return isReply;
	}

	public void setIsReply(Integer isReply) {
		this.isReply = isReply;
	}

	public Integer getReplyId() {
		return replyId;
	}

	public void setReplyId(Integer replyId) {
		this.replyId = replyId;
	}

}
