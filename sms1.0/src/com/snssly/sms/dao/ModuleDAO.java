package com.snssly.sms.dao;

import java.util.List;

import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.log4j.Logger;

import com.snssly.sms.commons.DBHelper;
import com.snssly.sms.entity.Module;

/**
 * -- 模块类。。所有DAO操作
 * 
 * @author Administrator
 * 
 */
public class ModuleDAO extends DBHelper {
	private Logger logger = Logger.getLogger(this.getClass());

	/**
	 * -- 查询所有模块
	 * 
	 * @return
	 */
	public List<Module> findAll() {
		this.sql = "select * from module order by id;";
		logger.info(this.sql);
		return super.query(new BeanListHandler<Module>(Module.class));
	}

	/**
	 * 根据用户编号查询所有的权限
	 * 
	 * @param uid
	 * @return
	 */
	public List<Module> findByUid(Integer uid) {
		this.sql = "SELECT m.* FROM module m LEFT JOIN role_module rm ON m.id=rm.mid LEFT JOIN role r ON rm.rid=r.id LEFT JOIN user u ON r.id=u.rid	WHERE u.id=? ORDER BY m.id;";
		logger.info(this.sql);
		return super.query(new BeanListHandler<Module>(Module.class), uid);
	}

	/**
	 * -- 添加模块
	 * 
	 * @param mod
	 */
	public void add(Module mod) {
		this.sql = "insert into module(name,remarks,root,menu,url) values(?,?,?,?,?);";
		super.update(new Object[] { mod.getName(), mod.getRemarks(),
				new Integer(mod.getRoot()), mod.getMenu(), mod.getUrl() });
	}

	/**
	 * -- 修改模块
	 * 
	 * @param mod
	 */
	public void update(Module mod) {
		this.sql = "update module set name=?,remarks=?,root=?,url=?,menu=? where id=?";
		Object[] params = new Object[] { mod.getName(), mod.getRemarks(),
				new Integer(mod.getRoot()), mod.getUrl(), mod.getMenu(),
				new Integer(mod.getId()) };
		super.update(params);
	}

	/**
	 * -- 根据ID删除模块
	 * 
	 * @param mid
	 */
	public void delete(Integer mid) {
		this.sql = "delete from module where id=?";
		super.update(new Object[] { mid });
	}

	/**
	 * -- 删除子模块
	 * 
	 * @param root
	 */
	public void deleteByRoot(Integer root) {
		this.sql = "delete from module where root=?";
		super.update(new Object[] { root });
	}
}
