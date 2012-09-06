package com.snssly.sms.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MessageType implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String name;
	private Date createTime;
	private String rid;// 允许使用此短信类型的角色
	private List<Integer> ridList;
	private Integer count;
	private String sendSmsRid;// 允许发送短信的角色
	private List<Integer> smsRidList;

	public MessageType() {
	}

	/**
	 * 得到允许发送短信的角色编号
	 * 
	 * @return
	 */
	public List<Integer> getSmsRidList() {
		if (sendSmsRid != null && !"".equals(sendSmsRid.trim())) {
			String[] sendSmsRids = sendSmsRid.split(",");
			if (sendSmsRids != null && sendSmsRids.length > 0) {
				smsRidList = new ArrayList<Integer>();
				for (int i = 0; i < sendSmsRids.length; i++) {
					if (sendSmsRids[i] != null
							&& !"".equals(sendSmsRids[i].trim())) {
						smsRidList.add(Integer.parseInt(sendSmsRids[i].trim()));
					}
				}
			}
		}
		return smsRidList;
	}

	public void setSmsRidList(List<Integer> smsRidList) {
		this.smsRidList = smsRidList;
	}

	public String getRid() {
		return rid;
	}

	public void setRid(String rid) {
		this.rid = rid;
	}

	/**
	 * 获取到支持的数组
	 * 
	 * @return
	 */
	public List<Integer> getRidList() {
		if (rid != null && !"".equals(rid.trim())) {
			String[] rids = rid.split(",");
			if (rids != null && rids.length > 0) {
				ridList = new ArrayList<Integer>();
				for (int i = 0; i < rids.length; i++) {
					if (rids[i] != null && !"".equals(rids[i].trim())) {
						ridList.add(Integer.parseInt(rids[i].trim()));
					}
				}
			}
		}
		return ridList;
	}

	public void setRidList(List<Integer> ridList) {
		this.ridList = ridList;
	}

	public MessageType(String name, Date createTime) {
		this.name = name;
		this.createTime = createTime;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
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

	public String getSendSmsRid() {
		return sendSmsRid;
	}

	public void setSendSmsRid(String sendSmsRid) {
		this.sendSmsRid = sendSmsRid;
	}

}
