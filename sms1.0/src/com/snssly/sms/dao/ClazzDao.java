package com.snssly.sms.dao;

import java.util.List;

import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.log4j.Logger;

import com.snssly.sms.commons.DBHelper;
import com.snssly.sms.entity.Clazz;

public class ClazzDao extends DBHelper {
	private Logger logger = Logger.getLogger(this.getClass());
	
	
	public List<Clazz> findClazzByGrade(Integer id){
		this.sql =  "select * from clazz as c where gid=?";
		logger.info(this.sql);
		return super.query(new BeanListHandler<Clazz>(Clazz.class),id);
		
	}

	//查询全部班级
	public List<Clazz> findASG() {
		this.sql = "SELECT c.*,s.id sid,g.name gname FROM clazz c LEFT JOIN grade g ON g.id = c.gid LEFT JOIN school s ON s.id = g.schoolid";
		logger.info(this.sql);
		return super.query(new BeanListHandler<Clazz>(Clazz.class));
	}
	//查询所有班级，并分页
	public List<Clazz> getClazzList(int page,int count){
		this.sql = "SELECT c.*,s.id sid,g.name gname FROM clazz c LEFT JOIN grade g ON g.id = c.gid LEFT JOIN school s ON s.id = g.schoolid order by id desc limit "
					+ (page * count) + "," + count;
		logger.info(this.sql);
		return super.query(new BeanListHandler<Clazz>(Clazz.class));
	}
	//添加班级
	public void add(Clazz clazz) {
		this.sql = "INSERT INTO `clazz`(`name`,`gid`,`createTime`) VALUES ( ?,?,now())";
		super.update(new Object[]{clazz.getName(),clazz.getGid()});		
	}
	//修改班级信息
	public void update(Clazz clazz){
		this.sql = "UPDATE `sms_db`.`clazz` SET `name`=?,`gid`=? WHERE `id`=?";
		super.update(new Object[]{clazz.getName(),clazz.getGid(),clazz.getId()});
	}
	//删除班级 BY id
	public void delete(Integer id) {	
		this.sql = "DELETE FROM `clazz` WHERE `id`=?";
		super.update(new Object[]{id});
	}
	//查询数据库里clazz的总条数
	public Integer getAllCount() {
		this.sql = "select count(*) from clazz";
		return super.count();
	}
	//根据ID查询班级
	public Clazz findClazzById(Integer id){
		this.sql = "select * from clazz where id=?";
		logger.info(this.sql);
		return (Clazz)super.query(new BeanHandler<Clazz>(Clazz.class),id);
	}
	//查询班级，根据uid，可得到用户的gid
	public List<Clazz> findAllByUid(Integer id) {
		this.sql = " SELECT c.*,g.name gname " +
				   " FROM clazz c " +
				   " LEFT JOIN grade g ON g.id = c.gid " +
				   " LEFT JOIN user u on u.cid=c.id " +
				   " where u.id=?";
		logger.info(this.sql);
		return super.query(new BeanListHandler<Clazz>(Clazz.class),id);
	}
	/**
	 * -- 根据用户ID查询所在班级和年级
	 * 
	 * @return
	 */
	public Clazz findClazzByUid(Integer id) {
		this.sql = "SELECT g.id gid,g.name gname,c.id id,c.name name " +
				   " FROM clazz c " +
				   " LEFT JOIN grade g ON c.gid=g.id " +
				   " WHERE c.id=?";
		logger.info(this.sql);
		return (Clazz) super.query(new BeanHandler<Clazz>(Clazz.class), id);
	}
	/**
	 * 根据年级id gid，得到该年级的所有班级的id和name
	 * @param gid
	 * @return
	 */
	public List<Clazz> findClazzByGid(Integer gid) {
		this.sql = "SELECT id,`name` FROM clazz WHERE gid=?";
		logger.info(this.sql);
		return super.query(new BeanListHandler<Clazz>(Clazz.class),gid);
	}
	/**
	 * 根据用户ID查询所在班级
	 * @param uid
	 * @return
	 */
	public Clazz findClazzByUidOnly(Integer uid) {
		this.sql = "SELECT c.* FROM clazz c LEFT JOIN `user` u ON u.cid = c.id WHERE u.id = ?" ;
		logger.info(this.sql);
		return (Clazz) super.query(new BeanHandler<Clazz>(Clazz.class), uid);
	}
}
