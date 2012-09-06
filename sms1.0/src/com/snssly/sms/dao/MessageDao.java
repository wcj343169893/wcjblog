package com.snssly.sms.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.log4j.Logger;

import com.snssly.sms.commons.DBHelper;
import com.snssly.sms.entity.Message;

public class MessageDao extends DBHelper {
	private Logger logger = Logger.getLogger(this.getClass());

	/**
	 * 新增 默认值：不是模板，显示
	 * 
	 * @param message
	 * @return
	 */
	public Integer add(Message message) {
		this.sql = "INSERT INTO message (title,tid,uid,content,createTime,sendTime,isSend)VALUES(?,?,?,?,now(),?,?)";
		logger.info(this.sql);
		super.update(message.getTitle(), message.getTid(), message.getUid(),
				message.getContent(), message.getSendTime(), message
						.getIsSend());
		return super.getLastInsertId();
	}

	/**
	 * 更新信息
	 * 
	 * @param message
	 * @return
	 */
	public boolean update(Message message) {
		this.sql = "update message set title=?,tid=?,content=?,sendTime=?,isSend=? where id=?";
		logger.info(this.sql);
		return super.update(message.getTitle(), message.getTid(), message
				.getContent(), message.getSendTime(), message.getIsSend(),
				message.getId());
	}

	/**
	 * 更新消息状态
	 * 
	 * @param message
	 * @return
	 */
	public boolean updateStatus(Message message) {
		this.sql = "update message set status = ?  where id=?";
		logger.info(this.sql);
		return super.update(message.getStatus(), message.getId());
	}

	/**
	 * 修改消息状态
	 * 
	 * @param message
	 * @return
	 */
	public boolean visible(Message message) {
		this.sql = "update message set isVisible=? where id=?";
		logger.info(this.sql);
		return super.update(message.getIsVisible(), message.getId());
	}

	/**
	 * 删除消息
	 * 
	 * @param message
	 * @return
	 */
	public boolean delete(Message message) {
		this.sql = "delete from message where id=?";
		logger.info(this.sql);
		return super.update(message.getId());
	}

	/**
	 * 更新消息状态0.显示，1.删除,-1彻底删除
	 * 
	 * @param id
	 * @param isVisible
	 * @return
	 */
	public boolean delete(Integer id, Integer isVisible) {
		this.sql = "update message set isVisible=? where id=?";
		logger.info(this.sql);
		return super.update(isVisible, id);
	}

	/**
	 * 根据编号查询信息
	 * 
	 * @param id
	 * @return
	 */
	public Message findById(Integer id) {
		this.sql = "SELECT m.*,u.nikeName nikeName ,mt.name tname , u.mobile mobile FROM message m LEFT JOIN user u ON m.uid=u.id LEFT JOIN message_type mt ON m.tid=mt.id where m.id=?";
		return super.query(new BeanHandler<Message>(Message.class), id);
	}

	/**
	 * 查询所有
	 * 
	 * @return
	 */
	public List<Message> getMessageList() {
		this.sql = "select * from message";
		logger.info(this.sql);
		return super.query(new BeanListHandler<Message>(Message.class));
	}

	/**
	 * 分页查询所有消息
	 * 
	 * @param page
	 * @param count
	 * @return
	 */
	public List<Message> getMessageList(Integer page, Integer count,
			String condition) {
		this.sql = "SELECT m.*,u.nikeName nikeName FROM message m LEFT JOIN user u ON u.id=m.uid "
				+ condition
				+ " order by m.id desc limit "
				+ (page * count)
				+ "," + count;
		logger.info(this.sql);
		return super.query(new BeanListHandler<Message>(Message.class));
	}

	/**
	 * 根据类型编号和角色编号查询模板
	 * 
	 * @param tid
	 *            短信类型
	 * @param rid
	 *            角色编号
	 * @return
	 */
	public List<Message> findByTidRid(Integer tid, Integer rid) {
		this.sql = "SELECT m.* FROM message m LEFT JOIN message_type mt ON m.tid=mt.id WHERE isModel=1 AND mt.rid LIKE '%"
				+ rid + "%' AND mt.id=?";
		return super.query(new BeanListHandler<Message>(Message.class), tid);
	}

	/**
	 * 查询所有的信息
	 * 
	 * @param condition
	 *            条件
	 * @return
	 */
	public Integer getAllCount(String condition) {
		this.sql = "select count(m.id) from message m " + condition;
		logger.info(this.sql);
		return super.count();
	}

	/**
	 * 根据用户名，查询用户未读信息
	 * 
	 * @param adm
	 * @return
	 */
	public List<Message> getMessage(Integer id) {
		this.sql = "SELECT m.id,m.title,u.nikeName,u.mobile,s.sendTime "
				+ "FROM message  m " + "LEFT JOIN sendlist s ON s.mid=m.id "
				+ "LEFT JOIN `user` u ON m.uid=u.id "
				+ "LEFT JOIN `user` lu ON s.uid=lu.id "
				+ "WHERE s.isRead=0 AND s.status=1 AND lu.id=?";
		logger.info(this.sql);
		return super.query(new BeanListHandler<Message>(Message.class), id);
	}

	/**
	 * 根据用户名，查询用户未读信息条数
	 * 
	 * @param adm
	 * @return
	 */
	public Integer getCount(Integer id) {
		this.sql = "SELECT COUNT(*) FROM sendlist WHERE isRead=0 AND status=0 AND uid=?";
		return super.count(id);
	}

	/**
	 * 不同类型的短信在某一时间段的发送数量
	 * 
	 * @param rid
	 * @param uid
	 * @param start
	 * @param end
	 * @return
	 */
	public Integer getCountByRid(Integer tid, Integer uid, String start,
			String end) {
		ArrayList<Object> obj = new ArrayList<Object>();
		obj.add(tid);
		obj.add(uid);
		this.sql = "SELECT COUNT(*) messageCount FROM message WHERE tid=? AND uid=? and isSend=1 ";
		if (start != null && !"".equals(start.trim())) {
			this.sql += " AND createTime>=?";
			obj.add(start);
		}
		if (end != null && !"".equals(end.trim())) {
			this.sql += " AND createTime<=?";
			obj.add(end);
		}
		return super.count(obj.toArray());
	}

	/**
	 * 分页查询接收信息(收件箱用)
	 * 
	 * @param page
	 * @param count
	 * @param uid
	 * @return
	 */
	public List<Message> findInByPage(Integer page, Integer count, Integer uid) {
		this.sql = "SELECT m.*,sl.isRead,sl.sendTime,u.nikeName nikeName,sl.isReply isReply,sl.isRead isRead FROM message m "
				+ "LEFT JOIN sendlist sl ON m.id=sl.mid "
				+ "left join user u on u.id=m.uid "
				+ "WHERE sl.status = 1 and sl.uid=? AND sl.isRead in(0,1) AND m.isSend=1 ORDER BY m.id DESC limit "
				+ (page * count) + "," + count;
		logger.info(this.sql);
		return super.query(new BeanListHandler<Message>(Message.class), uid);
	}

	/**
	 * 收件箱条数(查询接收成功的信息)
	 * 
	 * @param uid
	 * @return
	 */
	public Integer findInCount(Integer uid) {
		this.sql = "SELECT count(*) FROM message m "
				+ "LEFT JOIN sendlist sl ON m.id=sl.mid  "
				+ "WHERE sl.status = 1 and sl.uid=? AND sl.isRead in(0,1) AND m.isSend=1 ORDER BY m.id DESC ";
		logger.info(this.sql);
		return super.count(uid);
	}

	/**
	 * 分页查询已发信息(发件箱)
	 * 
	 * @param page
	 * @param count
	 * @param uid
	 * @return
	 */
	public List<Message> findOutByPage(Integer page, Integer count, Integer uid) {
		this.sql = "SELECT m.*,u.nikeName nikeName FROM message m LEFT JOIN sendlist sl ON sl.mid=m.id LEFT JOIN user u ON u.id=sl.uid WHERE m.uid=? AND m.isSend=1 AND m.isVisible =0 GROUP BY m.id order by m.id desc limit "
				+ (page * count) + "," + count;
		logger.info(this.sql);
		return super.query(new BeanListHandler<Message>(Message.class), uid);
	}

	/**
	 * 查询发件条数
	 * 
	 * @param uid
	 * @return
	 */
	public Integer findOutCount(Integer uid) {
		this.sql = "SELECT COUNT(id) FROM message m WHERE m.uid=? AND m.isSend=1 AND m.isVisible =0";
		logger.info(this.sql);
		return super.count(uid);
	}

	/**
	 * 查询未发送的信息(草稿箱)
	 * 
	 * @param page
	 * @param count
	 * @param uid
	 * @return
	 */
	public List<Message> findDraftByPage(Integer page, Integer count,
			Integer uid) {
		this.sql = "SELECT m.*,u.nikeName nikeName FROM message m LEFT JOIN sendlist sl ON sl.mid=m.id LEFT JOIN user u ON u.id=sl.uid WHERE m.uid=? AND m.isSend=0 AND m.isVisible =0 GROUP BY m.id order by m.id desc limit "
				+ (page * count) + "," + count;
		logger.info(this.sql);
		return super.query(new BeanListHandler<Message>(Message.class), uid);
	}

	/**
	 * 草稿箱总条数
	 * 
	 * @param uid
	 * @return
	 */
	public Integer findDraftCount(Integer uid) {
		this.sql = "SELECT COUNT(id) FROM message WHERE isSend=0 AND uid=?";
		logger.info(this.sql);
		return super.count(uid);
	}

	/**
	 * 查询已删除的信息(垃圾箱)
	 * 
	 * @param page
	 * @param count
	 * @param uid
	 * @return
	 */
	public List<Message> findRubbishByPage(Integer page, Integer count,
			Integer uid) {
		this.sql = "SELECT m.*,u.nikeName nikeName FROM message m LEFT JOIN sendlist sl ON sl.mid=m.id LEFT JOIN user u ON m.uid=u.id WHERE (m.isVisible=1 OR sl.isRead=-1) AND (m.uid=? OR sl.uid=?) GROUP BY m.id order by m.id desc limit "
				+ (page * count) + "," + count;
		logger.info(this.sql);
		return super.query(new BeanListHandler<Message>(Message.class), uid,
				uid);
	}

	/**
	 * 垃圾箱总条数
	 * 
	 * @param uid
	 * @return
	 */
	public Integer findRubbishCount(Integer uid) {
		this.sql = "SELECT COUNT(msl.id) FROM (SELECT m.id id FROM message m LEFT JOIN sendlist sl ON sl.mid=m.id WHERE (m.isVisible=1 OR sl.isRead=-1) AND (m.uid=? OR sl.uid=?)  GROUP BY m.id) msl ";
		logger.info(this.sql);
		return super.count(uid, uid);
	}
}
