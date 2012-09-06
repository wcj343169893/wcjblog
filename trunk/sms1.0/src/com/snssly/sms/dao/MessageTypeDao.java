package com.snssly.sms.dao;

import java.util.List;

import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.log4j.Logger;

import com.snssly.sms.commons.DBHelper;
import com.snssly.sms.entity.MessageType;

public class MessageTypeDao extends DBHelper {
	private Logger logger = Logger.getLogger(this.getClass());

	/**
	 * 查询所有消息类型
	 * 
	 * @return
	 */
	public List<MessageType> getMessageTypeList() {
		this.sql = "select * from message_type";
		logger.info(this.sql);
		return super.query(new BeanListHandler<MessageType>(MessageType.class));
	}

	/**
	 * 根据编号查询消息类型
	 * 
	 * @param id
	 * @return
	 */
	public MessageType findById(Integer id) {
		this.sql = "select * from message_type where id=?";
		logger.info(this.sql);
		return super.query(new BeanHandler<MessageType>(MessageType.class), id);
	}

	/**
	 * 登陆者角色不同，所发送短信的类型不同
	 * 
	 * @param rid
	 * @return
	 */
	public List<MessageType> findType(Integer rid) {
		this.sql = "SELECT id,`name` FROM message_type WHERE rid LIKE '%" + rid
				+ "%' ";
		logger.info(this.sql);
		return super.query(new BeanListHandler<MessageType>(MessageType.class));
	}
}
