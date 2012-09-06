package com.snssly.sms.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.log4j.Logger;

import com.snssly.sms.commons.DBHelper;
import com.snssly.sms.entity.Sendlist;

/**
 * 发送列表
 * 
 */
public class SendListDao extends DBHelper {
	private Logger logger = Logger.getLogger(this.getClass());

	/**
	 * 根据消息编号查询发送列表
	 * 
	 * @param mid
	 *            消息编号
	 * @return
	 */
	public List<Sendlist> findListByMid(Integer mid) {
		this.sql = "SELECT sl.*,u.nikeName nikeName,u.mobile mobile FROM sendlist sl LEFT JOIN user u ON sl.uid=u.id where sl.mid=?";
		logger.info(this.sql);
		return super.query(new BeanListHandler<Sendlist>(Sendlist.class), mid);
	}

	/**
	 * 添加发送列表
	 * 
	 * @param sendLists
	 */
	public void add(List<Sendlist> sendLists) {
		this.sql = "INSERT INTO sendlist(mid,uid,sendTime,status,isParent) VALUES";
		Integer size = sendLists.size();
		List<Object> list = new ArrayList<Object>();
		for (int i = 0; i < size; i++) {
			this.sql += "(?,?,?,?,?)";
			Sendlist sl = sendLists.get(i);
			list.add(sl.getMid());
			list.add(sl.getUid());
			list.add(sl.getSendTime());
			list.add(sl.getStatus());
			list.add(sl.getIsParent());
			if (i < size - 1) {
				this.sql += ",";
			}
		}
		super.update(list.toArray());
	}

	/**
	 * 保存单条发送记录
	 * 
	 * @param sendLists
	 *            发送记录
	 */
	public void add(Sendlist sendLists) {
		this.sql = "INSERT INTO sendlist(mid,uid,sendTime,isParent) VALUES (?,?,?,?)";
		super.update(sendLists.getMid(), sendLists.getUid(), sendLists
				.getSendTime(),sendLists.getIsParent());
	}

	/**
	 * 更新发送状态
	 * 
	 * @param sendLists
	 */
	public void updateStatus(Sendlist sendLists) {
		this.sql = "update sendlist set status=? where mid=?";
		super.update(sendLists.getStatus(), sendLists.getMid());
	}

	/**
	 * 更新阅读状态
	 * 
	 * @param sendLists
	 */
	public void updateRead(Sendlist sendLists) {
		this.sql = "update sendlist set isRead=? where id=?";
		super.update(sendLists.getIsRead(), sendLists.getId());
	}

	/**
	 * 更新阅读状态
	 * 
	 * @param id
	 * @param isRead
	 *            0.未读，1.已读，-1已删除,-2彻底删除
	 */
	public void updateRead(Integer id, Integer isRead) {
		this.sql = "update sendlist set isRead=? where id=?";
		super.update(isRead, id);
	}

	/**
	 * 根据消息编号和用户编号更新状态
	 * 
	 * @param mid
	 *            消息编号
	 * @param isRead
	 *            0.未读，1.已读，-1已删除,-2彻底删除
	 * @param uid
	 *            用户编号
	 */
	public void updateRead(Integer mid, Integer uid, Integer isRead) {
		this.sql = "update sendlist set isRead=? where mid=? and uid=?";
		super.update(isRead, mid, uid);
	}

	/**
	 *更新回复状态
	 * 
	 * @param sendlist
	 */
	public void updateReply(Sendlist sendlist) {
		this.sql = "update sendlist set isReply=? where mid=? and uid=?";
		super.update(sendlist.getIsReply(), sendlist.getMid(),sendlist.getUid());
	}

	/**
	 * 修改状态为
	 * 
	 * @param mid
	 * @return
	 */
	public boolean delete(Integer mid) {
		this.sql = "update sendlist set isRead=-1 where mid=?";
		return super.update(mid);
	}
	public boolean delete2(Integer mid) {
		this.sql = "delete from sendlist where mid=?";
		return super.update(mid);
	}

	/**
	 * 根据用户id，时间段查询用户接受的信息条数
	 * 
	 * @return
	 */
	// public Integer getCount(String start, String end,Integer uid) {
	// this.sql =
	// "SELECT COUNT(*) FROM sendlist WHERE uid=? AND sendTime>=? AND sendTime<=?";
	// return super.count(start, end,uid);
	// }
	public Integer getCount(String start, String end, Integer uid) {
		ArrayList<Object> obj = new ArrayList<Object>();	
		obj.add(uid);
		this.sql = "SELECT count(*) FROM sendlist WHERE uid=? AND (isRead=0 OR isRead=1) AND `status`=1";
		if (start != null && !"".equals(start.trim())) {
			this.sql += " AND sendTime>=?";
			obj.add(start);
		}
		if (end != null && !"".equals(end.trim())) {
			this.sql += " AND sendTime<=?";
			obj.add(end);
		}
		return super.count(obj.toArray());
	}
}
