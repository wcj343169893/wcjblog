package com.snssly.sms.entity;

import java.text.SimpleDateFormat;
import java.sql.Date;
import java.util.List;

public class User {
	private Integer id;// 系统编号
	private String name;// 登录名
	private String password;// 密码
	private String nikeName;// 名字
	private Integer cid;// 班级编号
	private Integer gradeId;// 年级编号
	private Integer rid;// 角色编号
	private Integer snumber;// 学号
	private Integer gid;// 分组编号
	private Integer sex;// 性别0.男，1.女
	private Date brithday;// 生日
	private String sid;// 身份证编号
	private String birthplace;// 籍贯
	private Date registTime;// 注册时间
	private Integer isVisible;// 是否启用0.启用，1.禁用
	private Date lastLoginTime;// 最后登录时间
	private String mobile;// 手机号码
	private String rname;// 角色名字
	private String gname;// 学生分组名字
	private String cname;// 学生班名字
	private String gdname;// 学生年级名字
	private String pnikeName;// 家长姓名
	private String relationship;// 关系
	private Date memberEndTime;// 会员到期时间

	private Integer upid;// user_parent中的ID
	private Integer cuid; // 学生在user_parent中的ID
	private Integer puid;// 父亲在user_parent中的ID

	private List<ExamScores> esList;// 该生考试成绩列表
	private Integer level;// 角色等级
	private List<Message> messageList;// 统计信息，短信统计

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNikeName() {
		return nikeName;
	}

	public void setNikeName(String nikeName) {
		this.nikeName = nikeName;
	}

	public Integer getCid() {
		return cid;
	}

	public void setCid(Integer cid) {
		this.cid = cid;
	}

	public Integer getGradeId() {
		return gradeId;
	}

	public void setGradeId(Integer gradeId) {
		this.gradeId = gradeId;
	}

	public Integer getRid() {
		return rid;
	}

	public void setRid(Integer rid) {
		this.rid = rid;
	}

	public Integer getSnumber() {
		return snumber;
	}

	public void setSnumber(Integer snumber) {
		this.snumber = snumber;
	}

	public Integer getGid() {
		return gid;
	}

	public void setGid(Integer gid) {
		this.gid = gid;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public Date getBrithday() {
		return brithday;
	}

	public void setBrithday(Date brithday) {
		this.brithday = brithday;
	}

	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}

	public String getBirthplace() {
		return birthplace;
	}

	public void setBirthplace(String birthplace) {
		this.birthplace = birthplace;
	}

	public Date getRegistTime() {
		return registTime;
	}

	public void setRegistTime(Date registTime) {
		this.registTime = registTime;
	}

	public Integer getIsVisible() {
		return isVisible;
	}

	public void setIsVisible(Integer isVisible) {
		this.isVisible = isVisible;
	}

	public Date getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getRname() {
		return rname;
	}

	public void setRname(String rname) {
		this.rname = rname;
	}

	public String getGname() {
		return gname;
	}

	public void setGname(String gname) {
		this.gname = gname;
	}

	public String getCname() {
		return cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

	public String getGdname() {
		return gdname;
	}

	public void setGdname(String gdname) {
		this.gdname = gdname;
	}

	public String getPnikeName() {
		return pnikeName;
	}

	public void setPnikeName(String pnikeName) {
		this.pnikeName = pnikeName;
	}

	public String getRelationship() {
		return relationship;
	}

	public void setRelationship(String relationship) {
		this.relationship = relationship;
	}

	public Integer getUpid() {
		return upid;
	}

	public void setUpid(Integer upid) {
		this.upid = upid;
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

	public List<ExamScores> getEsList() {
		return esList;
	}

	public void setEsList(List<ExamScores> esList) {
		this.esList = esList;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public List<Message> getMessageList() {
		return messageList;
	}

	public void setMessageList(List<Message> messageList) {
		this.messageList = messageList;
	}

	public Date getMemberEndTime() {
		return memberEndTime;
	}

	public void setMemberEndTime(Date memberEndTime) {
		this.memberEndTime = memberEndTime;
	}

}
