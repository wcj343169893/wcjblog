package com.snssly.sms.dao;


import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.log4j.Logger;

import com.snssly.sms.commons.DBHelper;
import com.snssly.sms.entity.Adminuser;
import com.snssly.sms.entity.User;

public class AdminuserDAO extends DBHelper {
	private Logger logger = Logger.getLogger(this.getClass());

	/**
	 * 根据用户名，查询用户
	 * 
	 * @param adm
	 * @return
	 */
	public User findByUser(Adminuser adm) {
		this.sql = "SELECT u.*,r.name rname,r.level level FROM user u left join role r on r.id = u.rid where u.name=? and u.password=? and u.isVisible=0";
		logger.info(this.sql);
		return super.query(new BeanHandler<User>(User.class),adm.getName(),adm.getPassword());
	}

	/**
	 * 更新最后登录时间
	 * 
	 * @param id
	 */
	public void update(Integer id) {
		this.sql="update user set lastLoginTime = now() where id=?";
		logger.info(this.sql);
		super.update(id);
	}	
}
