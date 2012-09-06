package com.snssly.sms.dao;

import java.util.List;

import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.log4j.Logger;

import com.snssly.sms.commons.DBHelper;
import com.snssly.sms.entity.Groups;
import com.snssly.sms.entity.GroupsType;

public class GroupsTypeDao extends DBHelper {
	private Logger logger = Logger.getLogger(this.getClass());
	
	/**
	 * -- 查询所有分组类型
	 * 
	 * @return
	 */
	public List<GroupsType> findAll(){
		this.sql = "select * from groups_type order by id desc";
		logger.info(this.sql);
		return super.query(new BeanListHandler<GroupsType>(GroupsType.class));
	}
	
	/**
	 * -- 添加分组类型
	 * @return 
	 * 
	 * 
	 */
	public void add(GroupsType groupsType){
		this.sql = "insert into groups_type(name)values(?)";
		super.update(groupsType.getName());
	}
	/**
	 * -- 修改分组类型
	 * 
	 * @param g
	 */
	public void update(GroupsType g) {
		super.sql = "update groups_type set name=?where id=?";
		super.update(g.getName(), g.getId());
	}
	
	/**
	 * -- 删除分组类型
	 * 
	 * @param r
	 */
	public void delete(Integer g) {
		super.sql = "delete from groups_type where id=?";
		super.update(g);
	}
	/**
	 * -- 根据删除分组类型的ID查询分组ID
	 * 
	 * @return
	 */
	public List<Groups> findGidByTid(Integer id){
		this.sql = "select id from groups where tid=?";
		logger.info(this.sql);
		return super.query(new BeanListHandler<Groups>(Groups.class),id);
	}
	/**
	 * -- 删除分组类型时，同时删除分组
	 * 
	 * @param r
	 */
	public void deleteGroupsByTypeId(Integer tid) {
		super.sql = "delete from groups where tid=?";
		super.update(tid);
	}
	/**
	 * -- 删除分组类型时，将User中的分组ID设为空
	 * 
	 * @param gid
	 */
	public void updateUserByTypeId(List<Groups> gid) {
		super.sql = "update User set gid='' where gid=?";
		super.update(((Groups) gid).getId());
	}
	/**
	 * 查询所有分组类型，并分页
	 * @param page
	 * @param count
	 * @return
	 */
	public List<GroupsType> getGroupsTypeList(int page,int count){
		this.sql = "select * from groups_type order by id desc limit "+ (page * count) + "," + count;
		return super.query(new BeanListHandler<GroupsType>(GroupsType.class));
	}
	/**
	 * 显示数据的总条数
	 * @return
	 */
	public Integer getAllCount() {
		this.sql = "select count(*) from groups_type";
		return super.count();
	}

}
