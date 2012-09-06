package com.snssly.sms.dao;

import java.util.Calendar;
import java.util.List;

import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.log4j.Logger;

import com.snssly.sms.commons.DBHelper;
import com.snssly.sms.commons.Env;
import com.snssly.sms.entity.OnlineUser;
import com.snssly.sms.entity.Role;
import com.snssly.sms.entity.User;

public class OnlineDao extends DBHelper {
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

	/**
	 * 更新在线用户
	 * 
	 * @param id
	 */
	public void updateOnline(Integer id, String ip) {
		OnlineUser ou = getUser(id);
		if (ou != null) {
			this.sql = "update online_user set lastTime=now(),ip=? where id=?";
			logger.info(this.sql);
			super.update(ip, ou.getId());
		} else {
			this.sql = "insert into online_user (loginTime,uid,lastTime,ip)"
					+ " values(now(),?,now(),?)";
			logger.info(this.sql);
			super.update(id, ip);
		}
	}

	/**
	 * -- 根据ID删除用户
	 * 
	 * @param mid
	 */
	public void deleteOnline(Integer id) {
		this.sql = "delete from online_user where uid=?";
		super.update(id);
	}

	/**
	 * 删除登录操作超时的用户(默认半小时)
	 */
	public void deleteOnline() {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MINUTE, -30);
		String lastTime = Env.format(calendar.getTime(), "yyyy-MM-dd HH:mm:ss");
		this.sql = "delete from online_user where lastTime < '" + lastTime
				+ "'";
		super.update();
	}

	/**
	 * 得到所有的在线人数
	 * 
	 * @return
	 */
	public Integer getCount() {
		this.sql = "SELECT COUNT(*) FROM online_user WHERE 1=1";
		return super.count();
	}

	/**
	 * 根据id查询用户是否已经登录
	 * 
	 * @return
	 */
	public OnlineUser getUser(Integer id) {
		this.sql = "select * from online_user where uid=?";
		logger.info(this.sql);
		return super.query(new BeanHandler<OnlineUser>(OnlineUser.class), id);
	}
}
