package com.snssly.sms.dao;

import java.util.List;

import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.log4j.Logger;

import com.snssly.sms.commons.DBHelper;
import com.snssly.sms.entity.OnlineUser;
import com.snssly.sms.entity.Role;
import com.snssly.sms.entity.User;

public class StatDao extends DBHelper {
	private Logger logger = Logger.getLogger(this.getClass());

	public List<User> findOnline() {
		this.sql = " SELECT  o.uid,o.loginTime,u.nikeName,u.cid cid,u.rid,c.name cname,gd.name gdname,r.name rname "
				+ " FROM online_user o "
				+ " LEFT JOIN `user` u ON u.id=o.uid "
				+ " LEFT JOIN clazz c ON u.cid=c.id "
				+ " LEFT JOIN grade gd ON c.gid=gd.id "
				+ " LEFT JOIN role r ON r.id=u.rid";
		logger.info(this.sql);
		return super.query(new BeanListHandler<User>(User.class));
	}

	/**
	 * 角色列表
	 * 
	 * @return
	 */
	public List<Role> findRole() {
		this.sql = " SELECT id,`name` FROM role WHERE 1=1";
		logger.info(this.sql);
		return super.query(new BeanListHandler<Role>(Role.class));
	}

	/**
	 * 在线人数，根据角色id
	 * 
	 * @return
	 */
	public Integer findOnlineUser(Integer rid) {
		this.sql = "SELECT COUNT(*) FROM online_user o LEFT JOIN `user` u ON u.id=o.uid WHERE u.rid=?";
		logger.info(this.sql);
		return super.count(rid);
	}

	/**
	 * 根据uid，查询ip，表为online_user
	 * 
	 * @return
	 */
	public List<OnlineUser> findIp(Integer uid) {
		this.sql = " SELECT ip FROM online_user WHERE uid=?";
		logger.info(this.sql);
		return super.query(new BeanListHandler<OnlineUser>(OnlineUser.class),
				uid);
	}

	/**
	 * 在线人数，根据角色id
	 * 
	 * @return
	 */
	public List<OnlineUser> findOnlineList(Integer rid) {
		this.sql = "SELECT u.nikeName nikeName,o.ip ip FROM online_user o LEFT JOIN `user` u ON u.id=o.uid LEFT JOIN role r ON r.id=u.rid WHERE u.rid=?";
		logger.info(this.sql);
		return super.query(new BeanListHandler<OnlineUser>(OnlineUser.class),
				rid);
	}
}
