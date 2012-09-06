package com.snssly.sms.dao;

import java.util.List;

import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.snssly.sms.commons.DBHelper;
import com.snssly.sms.entity.Role;
import com.snssly.sms.entity.RoleModule;

/**
 * -- 角色DAO管理
 * 
 * @author Administrator
 * 
 */
public class RoleDAO extends DBHelper {

	/**
	 * -- 添加角色
	 * 
	 * @param role
	 */
	public void add(Role role) {
		super.sql = "insert into role(name,remarks) values(?,?)";
		super.update(role.getName(), role.getRemarks());
	}

	/**
	 * -- 查询所有角色
	 * 
	 * @return
	 */
	public List<Role> findAll() {
		super.sql = "select * from role order by id desc;";
		return super.query(new BeanListHandler<Role>(Role.class));
	}
	/**
	 * -- 根据Rid，查询角色信息
	 * 
	 * @return
	 */
	public Role findRoleByRid(Integer id) {
		super.sql = "select * from role where id=?;";
		return  (Role) super.query(new BeanListHandler<Role>(Role.class), id);
	}

	/**
	 * -- 修改角色
	 * 
	 * @param r
	 */
	public void update(Role r) {
		super.sql = "update role set name=? where id=?";
		super.update(r.getName(), r.getId());
	}

	/**
	 * -- 删除角色
	 * 
	 * @param r
	 */
	public void delete(Integer r) {
		super.sql = "delete from role where id=?";
		super.update(r);
	}

	/**
	 * -- 删除角色与模块的关联
	 * 
	 * @param id
	 */
	public void deleteRM(Integer id) {
		super.sql = "delete from role_module where id=?";
		super.update(id);
	}

	/**
	 * -- 根据角色删除角色与模块的关联
	 * 
	 * @param rid
	 */
	public void deleteRMByRole(Integer rid) {
		super.sql = "delete from role_module where rid=?";
		super.update(rid);
	}

	/**
	 * -- 根据模块删除角色与模块的关联
	 * 
	 * @param mid
	 */
	public void deleteRMByModule(Integer mid) {
		super.sql = "delete from role_module where mid=?";
		super.update(mid);
	}

	/**
	 * -- 添加角色与模块的关联
	 * 
	 * @param rid
	 * @param mid
	 */
	public void addRM(Integer rid, Integer mid) {
		super.sql = "insert into role_module(rid,mid) values(?,?);";
		super.update(rid, mid);
	}

	/**
	 * -- 查询所有角色与模块的关联
	 * 
	 * @return
	 */
	public List<RoleModule> findAllRM() {
		super.sql = "select * from role_module order by id;";
		return super.query(new BeanListHandler<RoleModule>(RoleModule.class));
	}

}
