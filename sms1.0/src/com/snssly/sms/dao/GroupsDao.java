package com.snssly.sms.dao;

import java.util.List;

import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.log4j.Logger;

import com.snssly.sms.commons.DBHelper;
import com.snssly.sms.entity.Groups;

public class GroupsDao extends DBHelper {
	private Logger logger = Logger.getLogger(this.getClass());

	/**
	 * -- 查询所有分组
	 * 
	 * @return
	 */
	public List<Groups> findAll() {
		this.sql = "select * from groups order by id desc";
		logger.info(this.sql);
		return super.query(new BeanListHandler<Groups>(Groups.class));
	}

	/**
	 * -- 根据角色查询分组
	 * 
	 * @return
	 */
	public List<Groups> findGroupsByRole(Integer id) {
		this.sql = "select * from groups as g where g.rid=?";
		logger.info(this.sql);
		return super.query(new BeanListHandler<Groups>(Groups.class), id);
	}

	/**
	 * 根据角色编号和年级编号查询分组(查询教师时用)
	 * 
	 * @param rid
	 * @param gid
	 * @return
	 */
	public List<Groups> findGroupsByRidGid(Integer rid, Integer gid) {
		this.sql = "SELECT g.* FROM groups g RIGHT JOIN user u ON u.gid=g.id LEFT JOIN clazz c ON u.cid=c.id LEFT JOIN grade grade ON grade.id=c.gid WHERE u.rid=?  AND grade.id=? GROUP BY u.gid ";
		logger.info(this.sql);
		return super.query(new BeanListHandler<Groups>(Groups.class), rid, gid);
	}

	/**
	 * 根据角色编号和班级编号(查询学生时用)
	 * 
	 * @param rid
	 * @param cid
	 * @return
	 */
	public List<Groups> findGroupsByRidCid(Integer rid, Integer cid) {
		this.sql = "SELECT g.* FROM groups g RIGHT JOIN user u ON u.gid=g.id LEFT JOIN clazz c ON u.cid=c.id  WHERE u.rid=?  AND u.cid=? GROUP BY u.gid ";
		logger.info(this.sql);
		return super.query(new BeanListHandler<Groups>(Groups.class), rid, cid);
	}

	/**
	 * -- 添加分组
	 * 
	 * @return
	 * 
	 * 
	 */
	public void add(Groups groups) {
		this.sql = "insert into groups(tid,name,rid)values(?,?,?)";
		super.update(groups.getTid(), groups.getName(), groups.getRid());
	}

	/**
	 * -- 修改分组
	 * 
	 * @param g
	 */
	public void update(Groups g) {
		super.sql = "update groups set tid=?,name=?,rid=? where id=?";
		super.update(g.getTid(), g.getName(), g.getRid(), g.getId());
	}

	/**
	 * -- 删除分组
	 * 
	 * @param r
	 */
	public void delete(Integer g) {
		super.sql = "delete from groups where id=?";
		super.update(g);
	}

	/**
	 * 查询所有分组，并分页
	 * 
	 * @param page
	 * @param count
	 * @return
	 */
	public List<Groups> getGroupsList(int page, int count) {
		this.sql = "select * from groups order by id desc limit "
				+ (page * count) + "," + count;
		return super.query(new BeanListHandler<Groups>(Groups.class));
	}

	/**
	 * 显示数据的总条数
	 * 
	 * @return
	 */
	public Integer getAllCount() {
		this.sql = "select count(*) from groups";
		return super.count();
	}

	// 根据ID查询所在组
	public Groups findGroupsById(Integer id) {
		this.sql = "select * from groups where id=?";
		logger.info(this.sql);
		return (Groups) super.query(new BeanListHandler<Groups>(Groups.class),
				id);
	}

	// 根据RID查询所在组
	public Groups findGroupsByRid(Integer id) {
		this.sql = "select * from groups where rid=?";
		logger.info(this.sql);
		return (Groups) super.query(new BeanListHandler<Groups>(Groups.class),
				id);
	}

	// 根据RID查询所在组
	public Groups getGroups(Integer id) {
		this.sql = "select * from groups where rid=? ORDER BY id DESC LIMIT 1 ";
		logger.info(this.sql);
		return (Groups) super.query(new BeanHandler<Groups>(Groups.class), id);
	}
}
